package com.newsman.newsman.activities;

import android.graphics.Bitmap;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.PictureConverter;
import com.newsman.newsman.auxiliary.ZoomableImageView;
import com.newsman.newsman.R;

public class ImageDisplayActivity extends AppCompatActivity {

    private ZoomableImageView zoomableImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        zoomableImage = findViewById(R.id.image_display_image_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            byte[] data = extras.getByteArray(Constant.IMAGE_DISPLAY_KEY);
            try {
                Bitmap bmp = PictureConverter.getBitmap(data);
                zoomableImage.setImageBitmap(bmp);
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }

            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
