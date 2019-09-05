package com.newsman.newsman.auxiliary.manu_helpers;

import android.content.Context;
import android.view.MenuInflater;
import android.view.View;

import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.LoginState;

public class PopUpMenuController {
    public static void showMenu(Context context, View view, int newsId) {
        android.widget.PopupMenu popup = new android.widget.PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.news_list_item_menu, popup.getMenu());
        if(LoginState.getInstance().getUserId() == Constant.INVALID_USER_ID) {
            popup.getMenu().findItem(R.id.action_update_news).setVisible(false);
            popup.getMenu().findItem(R.id.action_delete_news).setVisible(false);
        }
        popup.setOnMenuItemClickListener(new MenuItemClickListener(context, newsId));
        popup.show();
    }
}
