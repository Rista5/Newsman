package com.newsman.newsman.fragments.comment_fragment.delete_strategy;

import android.widget.ImageView;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Delete;
import com.newsman.newsman.rest_connection.RestConnector;

public class DeleteComment implements DeleteStrategy{
    @Override
    public void setDelete(int commentId, ImageView view) {
        view.setOnClickListener(v -> {
            new RestConnector(new Delete(commentId), Constant.COMMENT_ROUTE);
        });
    }
}
