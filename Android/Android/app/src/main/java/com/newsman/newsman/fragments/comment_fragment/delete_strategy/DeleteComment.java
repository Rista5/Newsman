package com.newsman.newsman.fragments.comment_fragment.delete_strategy;

import android.content.Context;
import android.widget.ImageView;

public class DeleteComment implements DeleteStrategy{
    @Override
    public void setDelete(int position, ImageView view, RemoveAtPosition remover) {
        view.setOnClickListener(v -> {
//            AppExecutors.getInstance().getNetworkIO().execute(CommentConnector.deleteComment(view.getContext(), position));
            remover.removeAt(position);
        });
    }
}
