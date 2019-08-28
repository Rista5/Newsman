package com.newsman.newsman.auxiliary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;
import com.newsman.newsman.server_entities.SimpleNews;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PictureLoader extends AsyncTaskLoader<List<SimpleNews>> {

    private static String currentPicturePath;
    private static List<News> newsList;

    public PictureLoader(@NonNull Context context, List<News> news) {
        super(context);
        newsList = news;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public List<SimpleNews> loadInBackground() {
        List<SimpleNews> simpleNewsList = new ArrayList<>(newsList.size());
        for(News n: newsList) {
            simpleNewsList.add(SimpleNews.getSimpleNews(n, getContext()));
        }
        return simpleNewsList;
    }

    private static File createPictureFile(Context context, Picture picture) throws IOException {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        int extensionIndex = picture.getName().lastIndexOf('.');
        String name = picture.getName().substring(0, extensionIndex);
        String extension = picture.getName().substring(extensionIndex, picture.getName().length());
        File image = File.createTempFile(
                name,
                extension,
                storageDir
                );
        currentPicturePath = image.getAbsolutePath();
        return image;
    }

    public static void savePictureData(Context context, int pictureId, Bitmap bitmap){
        File imageFile = null;
        String extension = ".png";
        String pictureName = pictureId + extension;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imageFile = new File(storageDir.getPath(), pictureName);

        if(imageFile.exists()){
            imageFile.delete();
        }
        try{
            imageFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imageFile);
            ObjectOutputStream oos = null;
            // moze i samo da se sacuva kao byte array preko fos, ali ovako je sigurnije valjda
            oos = new ObjectOutputStream(fos);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oos);
            oos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadPictureData(Context context, int pictureId) {
        File imageFile = null;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String extension = ".png";
        String pictureName = pictureId + extension;
        imageFile = new File(storageDir.getPath(), pictureName);
        Bitmap result = null;

        if(!imageFile.exists()){
            return null;
        }
        try{
            FileInputStream fis = new FileInputStream(imageFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = BitmapFactory.decodeStream(ois);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Picture> loadPictureListData(Context context, List<Picture> pictures){
        for(Picture p: pictures) {
            Bitmap bmp = PictureLoader.loadPictureData(context, p.getId());
            if(bmp != null)
                p.setPictureData(bmp);
        }
        return pictures;
    }

    public static void deletePictureData(Context context, int pictureId) {
        File imageFile = null;
        String extension = ".png";
        String pictureName = pictureId + extension;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imageFile = new File(storageDir.getPath(), pictureName);

        if(imageFile.exists()){
            imageFile.delete();
        }
    }

    private static String extractPictureExtension(Picture picture) {
        int extensionIndex = picture.getName().lastIndexOf('.');
        String name = picture.getName().substring(0, extensionIndex);
        return picture.getName().substring(extensionIndex, picture.getName().length());
    }

    public static Bitmap.CompressFormat getPictureFormat(Picture picture) {
        String extension = extractPictureExtension(picture);
        if(extension.toLowerCase().equals(".jpg")){
            return Bitmap.CompressFormat.JPEG;
        } else if(extension.toLowerCase().equals(".png")) {
            return Bitmap.CompressFormat.PNG;
        } else {
            return Bitmap.CompressFormat.WEBP;
        }
    }

    public static void loadPictureFromGallery(Context context){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(galleryIntent.resolveActivity(context.getPackageManager())!=null) {
            ((Activity) context).startActivityForResult(galleryIntent, Constant.REQUEST_LOAD_IMAGE);
        }
    }

    public static void capturePhoto(Context context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(context.getPackageManager())!=null) {
            ((Activity) context).startActivityForResult(takePictureIntent, Constant.REQUEST_IMAGE_CAPTURE);
        }
    }

    public static Bitmap getResultingBitmap(int requestCode, int resultCode, @Nullable Intent data, Context context) {
        if (requestCode == Constant.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            return (Bitmap) extras.get("data");
        } else if(requestCode == Constant.REQUEST_LOAD_IMAGE && resultCode == RESULT_OK){
            Uri pictureUri = data.getData();
            try{
                return MediaStore.Images.Media.getBitmap(context.getContentResolver(), pictureUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
