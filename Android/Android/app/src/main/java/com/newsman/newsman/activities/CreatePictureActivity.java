package com.newsman.newsman.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.newsman.newsman.auxiliary.BackArrowHelper;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.picture_helpers.PictureConverter;
import com.newsman.newsman.auxiliary.picture_helpers.PictureLoader;
import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.manu_helpers.LoginMenuInflater;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.model.db_entities.Picture;

public class CreatePictureActivity extends AppCompatActivity {

    private EditText nameEditText, descriptionEditText;
    private ImageView pictureView;
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

        BackArrowHelper.displayBackArrow(this);

        setViews();
        setAllButtonListeners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return BackArrowHelper.backArrowClicked(this);
            case R.id.action_save_news:
                createPicture();
                return true;
            default:
                if(LoginMenuInflater.handleOnMenuItemClick(item, getApplicationContext())) {
                    return true;
                }
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        LoginMenuInflater.inflateLogin(inflater,menu);
        inflater.inflate(R.menu.save_news_menu, menu);
        return true;
    }

    private void setViews() {
        nameEditText = findViewById(R.id.create_picture_name);
        descriptionEditText = findViewById(R.id.create_picture_description);
        pictureView = findViewById(R.id.create_picture_image_view);
    }

    private void setAllButtonListeners() {
        findViewById(R.id.create_picture_pick_gallery).setOnClickListener((view) -> {
            pickPhotoFromGallery();
        });
        findViewById(R.id.create_picture_capture_photo).setOnClickListener((view) -> {
            captcurePhoto();
        });
    }

    private void createPicture() {
        if(!testInput()){
            Toast toast = new Toast(getApplicationContext());
            toast.setText(R.string.create_picture_validation_toast);
            toast.show();
        } else {
            Intent data = new Intent();

            Bundle bundle = getPictureBundle(createNewPicture());
            data.putExtras(bundle);

            setResult(RESULT_OK, data);
            CreatePictureActivity.super.onBackPressed();
        }
    }

    private Bundle getPictureBundle(Picture picture) {
        Bundle bundle = new Bundle();
        bundle.putInt("Id", picture.getId());
        bundle.putString("Name", picture.getName());
        bundle.putString("Description", picture.getDescription());
        bundle.putInt("BelongsToNewsId", picture.getBelongsToNewsId());

        //TODO REVIEW if this is correct
        BitmapCache.getInstance().setBitmap(picture.getId(),picture.getBelongsToNewsId(),
                PictureConverter.getImageViewBitmap(pictureView));
        return bundle;
    }

    private void captcurePhoto() {
        PictureLoader.capturePhoto(this);
    }

    private void pickPhotoFromGallery() {
        PictureLoader.loadPictureFromGallery(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        pictureBitmap = PictureLoader.getResultingBitmap(requestCode, resultCode, data, this);
        pictureView.setImageBitmap(pictureBitmap);
    }

    private Picture createNewPicture() {
        int id = BitmapCache.getNonValidIdentifier();
        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        //TODO ovde bitmap ne treba da se koristi
        // mozda moze da se upotrebi
//        BitmapCache.getInstance().setBitmap(id, newsId, pictureBitmap);
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
