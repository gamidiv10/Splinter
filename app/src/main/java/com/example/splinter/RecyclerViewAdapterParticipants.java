/*
 * Created By: Vamsi Gamidi
 */
package com.example.splinter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerViewAdapterParticipants extends  RecyclerView.Adapter<RecyclerViewAdapterParticipants.ViewHolder>
{
    private ArrayList<String> firstNameList = new ArrayList<>();
    private ArrayList<String> lastNameList = new ArrayList<>();
    private ArrayList<String> emailList = new ArrayList<>();
    private ArrayList<String> checkedEmailList = new ArrayList<>();

    private Context rvContext;
    public Dialog dialogEditParticipant;
    public Button buttonSave, buttonCancel;
    public ViewHolder holder;
    public EditText editTextFirstName, editTextLastName;
    private DatabaseReference mDatabase;

    public RecyclerViewAdapterParticipants(Context context, ArrayList<String> fnameList, ArrayList<String> lnameList, ArrayList<String> mailList)
    {
        firstNameList = fnameList;
        lastNameList = lnameList;
        emailList = mailList;
        rvContext = context;
    }


    @NonNull
    @Override
    public RecyclerViewAdapterParticipants.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
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
    public void onBindViewHolder(@NonNull RecyclerViewAdapterParticipants.ViewHolder holder, int position)
    {
        holder.firstName.setText(firstNameList.get(position));
        holder.lastName.setText(lastNameList.get(position));

    }

    @Override
    public int getItemCount() {
        return firstNameList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        TextView firstName, lastName;
        RelativeLayout parent_layout;
        ImageButton editButton, deleteButton;
        AppCompatCheckBox checkBox;


        public ViewHolder(@NonNull View itemView)
        {
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
                    if(position != RecyclerView.NO_POSITION)
                    {
                        firstNameList.remove(position);
                        lastNameList.remove(position);
                        emailList.remove(position);

                        notifyItemRemoved(position);
                    }

                }
            });

//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
//                {
//
//                    if(isChecked == true)
//                    {
//
//                        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//
//                        checkedEmailList.add(emailList.get(getAdapterPosition()));
//                        Toast.makeText(rvContext, "Finally", Toast.LENGTH_SHORT).show();
//                        DatabaseReference refEmailList = database.getReference("checkedEmailList");
//                        refEmailList.setValue(checkedEmailList);
//
//                        refEmailList.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                // This method is called once with the initial value and again
//                                // whenever data at this location is updated.
//                                ArrayList<String> value = (ArrayList<String>) dataSnapshot.getValue();
//
//                            }
//                            @Override
//                            public void onCancelled(DatabaseError error) {
//                                // Failed to read value
//                            }
//                        });
//
//
//                    }
//                    if(isChecked == false)
//                    {
//                        checkedEmailList.remove(emailList.get(getAdapterPosition()));
//                    }
//
//                }
//            });

        }
    }
}
