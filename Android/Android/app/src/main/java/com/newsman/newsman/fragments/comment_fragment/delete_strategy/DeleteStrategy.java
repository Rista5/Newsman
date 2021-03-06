package com.newsman.newsman.fragments.comment_fragment.delete_strategy;

import android.content.Context;
import android.widget.ImageView;

public interface DeleteStrategy {
    void setDelete(int position, ImageView view, RemoveAtPosition remover);
    interface RemoveAtPosition{
        void removeAt(int position);
    }
}
