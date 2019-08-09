package com.newsman.newsman.Auxiliary;

import android.content.Context;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.newsman.newsman.R;

public class MenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    private Context mContext;

    public MenuItemClickListener(Context context) {
        mContext = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_subscribe_to_news:
                Toast.makeText(mContext, "Successfuly subscribed to news!", Toast.LENGTH_SHORT).show();
                return true;
            default:
        }
        return false;
    }
}