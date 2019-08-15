package com.newsman.newsman.REST.Put.WriteJson;

import android.util.JsonWriter;

import com.newsman.newsman.ServerEntities.Comment;
import com.newsman.newsman.ServerEntities.JsonSerializer;

import java.io.IOException;

public class WriteComment extends WriteJson {

    private Comment comment;

    public WriteComment(Comment comment){
        this.comment = comment;
    }

    @Override
    public void writeJson(JsonWriter jsonWriter) throws IOException {
        JsonSerializer.writeComment(jsonWriter, comment);
    }
}
