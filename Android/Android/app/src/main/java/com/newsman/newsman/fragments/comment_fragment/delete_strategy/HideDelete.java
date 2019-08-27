package com.newsman.newsman.fragments.comment_fragment.delete_strategy;

import android.view.View;
import android.widget.ImageView;

public class HideDelete implements DeleteStrategy {
    @Override
    public void setDelete(int commentId, ImageView view) {
        view.setVisibility(View.INVISIBLE);
    }
}