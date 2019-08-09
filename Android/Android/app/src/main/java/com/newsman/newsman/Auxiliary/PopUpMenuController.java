package com.newsman.newsman.Auxiliary;

import android.content.Context;
import android.view.MenuInflater;
import android.view.View;

import com.newsman.newsman.R;

public class PopUpMenuController {
    public static void showMenu(Context context, View view) {
        // menu inflation
        android.widget.PopupMenu popup = new android.widget.PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.news_list_item_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuItemClickListener(context));
        popup.show();
    }
}
