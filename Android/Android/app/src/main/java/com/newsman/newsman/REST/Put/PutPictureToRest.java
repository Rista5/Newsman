package com.newsman.newsman.REST.Put;

import android.util.Base64;
import android.util.JsonWriter;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.ServerEntities.Picture;

import java.io.IOException;

public class PutPictureToRest extends PutToRest {

    private Picture picture;

    public PutPictureToRest(Picture picture) {
        this.picture = picture;
    }

    @Override
    public String getRoute() {
        return Constant.PICTURE_ROUTE;
    }

    @Override
    public void writeJsonObject(JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Id").value(picture.getId());
        jsonWriter.name("Name").value(picture.getName());
        jsonWriter.name("Description").value(picture.getDescription());
        jsonWriter.name("BelongsToNewsId").value(picture.getBelongsToNewsId());
        jsonWriter.name("PictureData")
                .value(Base64.encodeToString(picture.getPictureData(), Base64.DEFAULT));
        jsonWriter.endObject();
    }

}
