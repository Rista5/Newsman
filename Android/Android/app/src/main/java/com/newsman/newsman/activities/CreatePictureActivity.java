package com.newsman.newsman.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Auxiliary.PictureConverter;
import com.newsman.newsman.Auxiliary.PictureLoader;
import com.newsman.newsman.R;
import com.newsman.newsman.ServerEntities.Picture;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CreatePictureActivity extends AppCompatActivity {

    private EditText nameEditText, descriptionEditText;
    private ImageView pictureView;
    private Button postButton, cancelButton, captureButton, pickGalleryButton;
    private Bitmap pictureBitmap;
    private int newsId = Constant.INVALID_NEWS_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_picture);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            newsId = extras.getInt(Constant.NEWS_BUNDLE_KEY);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setViews();
        setAllButtonListeners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                    Intent data = new Intent();

//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable(Constant.PICTURE_BUNDLE_KEY, createNewPicture());
//                    data.putExtras(bundle);

//                    data.putExtra(Constant.PICTURE_BUNDLE_KEY, createNewPicture());

                    Bundle bundle = getPictureBundle(createNewPicture());
                    data.putExtras(bundle);

                    setResult(RESULT_OK, data);
                    CreatePictureActivity.super.onBackPressed();
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

    private Bundle getPictureBundle(Picture picture) {
        Bundle bundle = new Bundle();
        bundle.putInt("Id", picture.getId());
        bundle.putString("Name", picture.getName());
        bundle.putString("Description", picture.getDescription());
        bundle.putInt("BelongsToNewsId", picture.getBelongsToNewsId());
        bundle.putString("FileName", Constant.PICTURE_TRASPORT);
        FileOutputStream stream = null;
        try {
            stream = openFileOutput(Constant.PICTURE_TRASPORT, Context.MODE_PRIVATE);
            picture.getPictureData().compress(Bitmap.CompressFormat.PNG, 100, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bundle;
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
        int id = Constant.INVALID_PICTURE_ID;
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        return new Picture(id, name, description, newsId, pictureBitmap);
    }

    private boolean testInput() {
        if(pictureBitmap!= null){
            return true;
        } else {
            return false;
        }
    }
}
