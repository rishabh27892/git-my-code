package com.rishabh.chauhan.camera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    final int CAM_REQUEST=1;
    Button button;
    ImageView imageView;
    File x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        button =(Button) findViewById(R.id.CameraButton);
        imageView =(ImageView) findViewById(R.id.image);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private File getFile(){
        File folder =new File("sdcard/app");
        if(!folder.exists()){
            folder.mkdir();
        }
        File image_file = new File(folder,"img.jpg");
        return image_file;
    }


    public void ClickPic(View View){
        Intent Cam=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file =getFile();
        x=file;
        Cam.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        if (Cam.resolveActivity(getPackageManager()) != null){
            startActivityForResult(Cam,CAM_REQUEST);
        }

    }
    public void check_image(View View){

        File imgFile = new  File("/sdcard/app/img.jpg");

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            imageView.setImageBitmap(myBitmap);

        }
    }


}
