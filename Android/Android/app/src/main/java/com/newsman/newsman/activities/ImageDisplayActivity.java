package com.newsman.newsman.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;

import com.newsman.newsman.auxiliary.BackArrowHelper;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.ZoomableImageView;
import com.newsman.newsman.R;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.picture_management.BitmapObserver;

public class ImageDisplayActivity extends AppCompatActivity {

    private ZoomableImageView zoomableImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        zoomableImage = findViewById(R.id.image_display_image_view);

        BackArrowHelper.displayBackArrow(this);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            int pictureId = extras.getInt(Constant.IMAGE_DISPLAY_KEY);
            int newsId = extras.getInt(Constant.NEWS_BUNDLE_KEY);
            BitmapCache.getInstance().getBitmapObservable(this, pictureId, newsId)
                    .addObserver(new BitmapObserver(zoomableImage));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return BackArrowHelper.backArrowClicked(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
