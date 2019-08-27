package com.newsman.newsman.auxiliary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BackArrowHelper {

    public static void displayBackArrow(AppCompatActivity activity){
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static boolean backArrowClicked(AppCompatActivity activity){
        activity.finish();
        return true;
    }
}
