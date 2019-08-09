package com.newsman.newsman.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Auxiliary.ZoomableImageView;
import com.newsman.newsman.R;

import java.io.ByteArrayInputStream;

public class ImageDisplayActivity extends AppCompatActivity {

    private ZoomableImageView zoomableImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        zoomableImage = findViewById(R.id.image_display_image_view);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.);
//        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            byte[] data = extras.getByteArray(Constant.IMAGE_DISPLAY_KEY);
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            Bitmap bmp = BitmapFactory.decodeStream(bis);
            zoomableImage.setImageBitmap(bmp);

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
