package com.newsman.newsman.picture_management;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.newsman.newsman.auxiliary.PictureLoader;

public class LoadPictureTask extends AsyncTask<InParam, Void, LoadPictureTask.OutParam> {

    @Override
    protected void onPostExecute(OutParam outParam) {
        BitmapCache.getInstance().setBitmap(outParam.pictureId,outParam.newsId,outParam.bitmap);
    }

    @Override
    protected OutParam doInBackground(InParam... inParams) {
        Bitmap bmp = PictureLoader.loadPictureData(inParams[0].getContext(),inParams[0].getPictureId());
        return new OutParam(inParams[0].getPictureId(),inParams[0].getNewsId(),bmp);
    }

    public class OutParam{
        private int pictureId;
        private int newsId;
        private Bitmap bitmap;

        public OutParam(int pictureId, int newsId, Bitmap bitmap) {
            this.pictureId = pictureId;
            this.newsId = newsId;
            this.bitmap = bitmap;
        }

        public int getPictureId() {
            return pictureId;
        }

        public int getNewsId() {
            return newsId;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }
    }
}
