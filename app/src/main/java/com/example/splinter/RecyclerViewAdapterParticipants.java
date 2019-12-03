/*
 * Author: Vamsi Gamidi
 * Contributors:
 * Date: 2019
 */

package com.example.splinter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewAdapterParticipants extends RecyclerView.Adapter<RecyclerViewAdapterParticipants.ViewHolder> {
  private ArrayList<String> firstNameList = new ArrayList<>();
  private ArrayList<String> lastNameList = new ArrayList<>();
  private ArrayList<String> emailList = new ArrayList<>();
  private ArrayList<String> checkedEmailList = new ArrayList<>();

  private Context rvContext;
  public Dialog dialogEditParticipant;
  public Button buttonSave, buttonCancel;
  public ViewHolder holder;
  public EditText editTextFirstName, editTextLastName;

  public RecyclerViewAdapterParticipants(Context context, ArrayList<String> fnameList, ArrayList<String> lnameList, ArrayList<String> mailList) {
    firstNameList = fnameList;
    lastNameList = lnameList;
    emailList = mailList;
    rvContext = context;
  }


  //initializing the view holder
  @NonNull
  @Override
  public RecyclerViewAdapterParticipants.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    //dialog to edit the bill
    dialogEditParticipant = new Dialog(rvContext);
    dialogEditParticipant.setContentView(R.layout.dialog_edit_participant);
    editTextFirstName = dialogEditParticipant.findViewById(R.id.edit_text_first_name);
    editTextLastName = dialogEditParticipant.findViewById(R.id.edit_text_last_name);
    buttonSave = dialogEditParticipant.findViewById(R.id.button_save);
    buttonCancel = dialogEditParticipant.findViewById(R.id.button_cancel);

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_participants_recyclerview, parent, false);
    holder = new ViewHolder(view);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerViewAdapterParticipants.ViewHolder holder, int position) {
    holder.firstName.setText(firstNameList.get(position));
    holder.lastName.setText(lastNameList.get(position));

  }

  @Override
  public int getItemCount() {
    return firstNameList.size();
  }


  //creating the view holder
  public class ViewHolder extends RecyclerView.ViewHolder {
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    TextView firstName, lastName;
    RelativeLayout parent_layout;
    ImageButton editButton, deleteButton;
    AppCompatCheckBox checkBox;


    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      firstName = itemView.findViewById(R.id.tv_first_name);
      lastName = itemView.findViewById(R.id.tv_last_name);
      parent_layout = itemView.findViewById(R.id.recycler_view_participants);
//            checkBox = itemView.findViewById(R.id.checkbox_participants);

      editButton = itemView.findViewById(R.id.edit_participant_button);
      editButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          editTextFirstName.setText(firstNameList.get(getAdapterPosition()));
          editTextLastName.setText(lastNameList.get(getAdapterPosition()));

          dialogEditParticipant.show();
//button click events for save and cancel buttons
          buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              firstNameList.set(getAdapterPosition(), editTextFirstName.getText().toString());
              lastNameList.set(getAdapterPosition(), editTextLastName.getText().toString());
              notifyItemChanged(getAdapterPosition());

              dialogEditParticipant.cancel();
            }
          });
          buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dialogEditParticipant.cancel();
            }
          });
        }
      });

      deleteButton = itemView.findViewById(R.id.delete_participant_button);
      deleteButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          int position = getAdapterPosition();
          if (position != RecyclerView.NO_POSITION) {
            firstNameList.remove(position);
            lastNameList.remove(position);
            emailList.remove(position);

            notifyItemRemoved(position);
          }

        }
      });


    }
  }
}
