package com.newsman.newsman.REST.WriteJson;

import android.util.JsonWriter;

import com.newsman.newsman.ServerEntities.Comment;

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
