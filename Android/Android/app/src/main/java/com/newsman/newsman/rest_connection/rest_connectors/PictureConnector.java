package com.newsman.newsman.rest_connection.rest_connectors;

import android.content.Context;
import android.graphics.Bitmap;

import com.newsman.newsman.database.AppDatabase;
import com.newsman.newsman.model.dtos.PictureDTO;
import com.newsman.newsman.rest_connection.retrofit_services.PictureService;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.model.db_entities.Picture;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PictureConnector {
    public static Runnable savePicture(final Context context, final Picture picture, final Bitmap bitmap) {
        return () -> {
            PictureDTO requestDTO = new PictureDTO(picture);
            Retrofit retrofit = RetrofitFactory.createInstance();
            PictureService service = retrofit.create(PictureService.class);
            Call<PictureDTO> request  = service.createPicture(requestDTO);
            try {
                Response<PictureDTO> response = request.execute();
                PictureDTO responseDTO = response.body();
                if(responseDTO == null) return;
                AppDatabase.getInstance(context).pictureDao().insertPicture(PictureDTO.getPicture(responseDTO));
                BitmapConnector.saveBitmap(responseDTO.getBelongsToNewsId(), responseDTO.getId(), bitmap).run();
                BitmapCache.getInstance().deleteBitmap(picture.getId());
                BitmapCache.getInstance().setBitmap(responseDTO.getId(), responseDTO.getBelongsToNewsId(), bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static Runnable loadPicture(final Context context, final int pictureId) {
        return () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();
            PictureService service = retrofit.create(PictureService.class);
            Call<PictureDTO> request  = service.getPicture(pictureId);
            try {
                Response<PictureDTO> response = request.execute();
                PictureDTO dto = response.body();
                AppDatabase.getInstance(context).pictureDao().insertPicture(PictureDTO.getPicture(dto));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static Runnable loadPicturesForNews(final Context context, final int newsId) {
        return () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();
            PictureService service = retrofit.create(PictureService.class);
            Call<List<PictureDTO>> request  = service.getPicturesForNews(newsId);
            try {
                Response<List<PictureDTO>> response = request.execute();
                List<PictureDTO> dtoList = response.body();
                if(dtoList == null) return;
                for(PictureDTO dto: dtoList) {
                    AppDatabase.getInstance(context).pictureDao().insertPicture(PictureDTO.getPicture(dto));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static Runnable deletePicture(final Context context, final int pictureId) {
        return () -> {
            Retrofit retrofit = RetrofitFactory.createInstance();
            PictureService service = retrofit.create(PictureService.class);
            Call<Boolean> request  = service.deletePicture(pictureId);
            try {
                Response<Boolean> response = request.execute();
                Boolean deleted = response.body();
                if(deleted != null && deleted) {
                    AppDatabase.getInstance(context).pictureDao().deletePictureById(pictureId);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
