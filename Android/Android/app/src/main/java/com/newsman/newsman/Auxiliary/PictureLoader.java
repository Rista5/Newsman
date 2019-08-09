package com.newsman.newsman.Auxiliary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.newsman.newsman.ServerEntities.Picture;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

// verovatno nece trebati, posto mogu slike u bazu da se cuvaju
public class PictureLoader {

    private static String currentPicturePath;

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

    public static void savePictureData(Context context, Picture picture){
        File imageFile = null;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imageFile = new File(storageDir.getPath(), picture.getName());

        if(imageFile.exists()){
            imageFile.delete();
        }
        try{
            imageFile.createNewFile();
            //FileOutputStream fos = context.openFileOutput(imageFile.getPath(), Context.MODE_PRIVATE);
            FileOutputStream fos = new FileOutputStream(imageFile);
            ObjectOutputStream oos = null;
            // moze i samo da se sacuva kao byte array preko fos, ali ovako je sigurnije valjda
            oos = new ObjectOutputStream(fos);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(picture.getPictureData());
            Bitmap bmp = BitmapFactory.decodeStream(byteArrayInputStream);

            Bitmap.CompressFormat format = getPictureFormat(picture);

            bmp.compress(format, 100, oos);
            oos.close();
//            oos.notifyAll();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPictureData(Context context, Picture picture) {
        File imageFile = null;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imageFile = new File(storageDir.getPath(), picture.getName());

        if(!imageFile.exists()){
            return;
        }
        try{
            FileInputStream fis = new FileInputStream(imageFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Bitmap bmp = BitmapFactory.decodeStream(ois);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bmp.compress(getPictureFormat(picture), 100, byteArrayOutputStream);
            picture.setPictureData(byteArrayOutputStream.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
}
