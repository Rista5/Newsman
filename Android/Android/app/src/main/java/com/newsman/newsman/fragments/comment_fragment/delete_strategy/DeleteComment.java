package com.newsman.newsman.fragments.comment_fragment.delete_strategy;

import android.content.Context;
import android.widget.ImageView;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.new_rest.CommentConnector;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Delete;
import com.newsman.newsman.rest_connection.RestConnector;
import com.newsman.newsman.thread_management.AppExecutors;

public class DeleteComment implements DeleteStrategy{
    @Override
    public void setDelete(int position, ImageView view, RemoveAtPosition remover) {
        view.setOnClickListener(v -> {
//            AppExecutors.getInstance().getNetworkIO().execute(CommentConnector.deleteComment(view.getContext(), position));
            remover.removeAt(position);
        });
    }
}
