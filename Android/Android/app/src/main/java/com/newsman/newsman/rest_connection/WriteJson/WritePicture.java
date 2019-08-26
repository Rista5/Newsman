package com.newsman.newsman.rest_connection.WriteJson;

import android.util.JsonWriter;

import com.newsman.newsman.server_entities.Picture;

import java.io.IOException;

public class WritePicture extends WriteJson {

    private Picture picture;

    public WritePicture(Picture picture){
        this.picture = picture;
    }

    @Override
    public void writeJson(JsonWriter jsonWriter) throws IOException {
        JsonSerializer.writePicture(jsonWriter, picture);
    }
}
