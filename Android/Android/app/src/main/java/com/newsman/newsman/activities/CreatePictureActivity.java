package com.newsman.newsman.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Auxiliary.PictureConverter;
import com.newsman.newsman.Auxiliary.PictureLoader;
import com.newsman.newsman.R;
import com.newsman.newsman.REST.PutPictureToRest;
import com.newsman.newsman.ServerEntities.Picture;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CreatePictureActivity extends AppCompatActivity {

    private EditText nameEditText, descriptionEditText;
    private ImageView pictureView;
    private Button postButton, cancelButton, captureButton, pickGalleryButton;
    private Bitmap pictureBitmap;
    private int newsId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_picture);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            newsId = extras.getInt(Constant.NEWS_EXTRA_ID_KEY);
        }

        setViews();
        setAllButtonListeners();
    }

    private void setViews() {
        nameEditText = findViewById(R.id.create_picture_name);
        descriptionEditText = findViewById(R.id.create_picture_description);
        pictureView = findViewById(R.id.create_picture_image_view);
        postButton = findViewById(R.id.create_picture_post_picture);
        cancelButton = findViewById(R.id.create_picture_cancel);
        captureButton = findViewById(R.id.create_picture_capture_button);
        pickGalleryButton = findViewById(R.id.create_picture_pick_from_gallery);
    }

    private void setAllButtonListeners() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!testInput()){
                    Toast toast = new Toast(getApplicationContext());
                    toast.setText(R.string.create_picture_validation_toast);
                    toast.show();
                } else {
                    new PutPictureToRest(createNewPicture()).Post(getApplicationContext());
                    finish();
                }
            }
        });
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captcurePhoto();
            }
        });
        pickGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhotoFromGallery();
            }
        });
    }

    private void captcurePhoto() {
        PictureLoader.capturePhoto(this);
    }
    //mozda pravi problem sa rezolucijama slike, pogledaj na netu
    private void pickPhotoFromGallery() {
        PictureLoader.loadPictureFromGallery(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        pictureBitmap = PictureLoader.getResultingBitmap(requestCode, resultCode, data, this);
        pictureView.setImageBitmap(pictureBitmap);
    }

    private Picture createNewPicture() {
        int id = -1;
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        byte[] data = PictureConverter.getBitmapBytes(pictureBitmap);
        return new Picture(id, name, description, newsId, data);

    }

    private boolean testInput() {
        if(pictureBitmap!= null){
            return true;
        } else {
            return false;
        }
    }
}
