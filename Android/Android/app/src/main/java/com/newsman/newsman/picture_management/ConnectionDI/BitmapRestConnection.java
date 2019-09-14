package com.newsman.newsman.picture_management.ConnectionDI;

import android.graphics.Bitmap;

public interface BitmapRestConnection {
    void getBitmap(int pictureId, int newsId);
    void putBitmap(int pictureId, int newsId, Bitmap bitmap);
}