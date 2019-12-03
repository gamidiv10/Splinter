
/**
 * Created by Jasper Jiao on 2019-10-09.
 * Contributors: Vamsi Gamidi, Menni Prem Kumar
 */
package com.example.splinter;
import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class CameraActivity extends AppCompatActivity {
    Button btnTakepic;
    ImageView imageView;
    String patchToFile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        btnTakepic = findViewById(R.id.btnTakePic);
        if(Build.VERSION.SDK_INT >=23){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
        }
        btnTakepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakingPic();
            }
        });
        imageView = findViewById(R.id.image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bitmap bitmap = BitmapFactory.decodeFile(patchToFile);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
// Function to capture picture
    private void TakingPic(){
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            photoFile = createPhoto();
            if (photoFile !=null){
                patchToFile = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(CameraActivity.this,"com.example.splinter.fileprovider",photoFile);
                takePic.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePic,1);
            }
        }
    }
// Renaming the picture
    private File createPhoto(){
        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image =null;
        try {
            image = File.createTempFile(name,".jpg", storageDir);
        } catch (IOException e) {
            Log.d("mylog","Excep : "+ e.toString());
        }
        return image;
    }
}
