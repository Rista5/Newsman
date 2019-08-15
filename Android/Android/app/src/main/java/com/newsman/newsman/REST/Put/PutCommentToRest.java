package com.newsman.newsman.REST.Put;

import android.util.JsonWriter;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.ServerEntities.Comment;

import java.io.IOException;

public class PutCommentToRest extends PutToRest {

    private Comment comment;
    public PutCommentToRest(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String getRoute() {
        return Constant.COMMENT_ROUTE;
    }

    @Override
    public void writeJsonObject(JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("Content").value(comment.getContent());
        jsonWriter.name("BelongsToNewsId").value(comment.getBelongsToNewsId());
        jsonWriter.name("CreatedBy").value(comment.getCreatedById());
        jsonWriter.endObject();
    }
}
