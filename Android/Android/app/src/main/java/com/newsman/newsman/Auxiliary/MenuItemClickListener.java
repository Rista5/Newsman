package com.newsman.newsman.Auxiliary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.newsman.newsman.R;
import com.newsman.newsman.REST.ConnectionStrategy.Delete;
import com.newsman.newsman.REST.RestConnector;
import com.newsman.newsman.activities.UpdateNewsActivity;

public class MenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

    private Context mContext;
    private int newsId;

    public MenuItemClickListener(Context context, int newsId) {
        mContext = context;
        this.newsId = newsId;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_subscribe_to_news:
                Toast.makeText(mContext, "Successfuly subscribed to news!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_unsubscribe_from_news:
                Toast.makeText(mContext, "Successfuly unsubscribed from news!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_update_news:
                Intent intent = new Intent(mContext, UpdateNewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.NEWS_BUNDLE_KEY, newsId);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                return true;
            case R.id.action_delete_news:
                new RestConnector(new Delete(newsId), Constant.NEWS_ROUTE).execute();
                return true;
            default:
        }
        return false;
    }
}