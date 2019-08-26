package com.newsman.newsman.auxiliary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.newsman.newsman.R;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Delete;
import com.newsman.newsman.rest_connection.RestConnector;
import com.newsman.newsman.thread_management.SubscriptionService;
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
                return startServiceWithAction(SubscriptionService.SUBSCRIBE);
            case R.id.action_unsubscribe_from_news:
                return startServiceWithAction(SubscriptionService.UNSUBSCRIBE);
            case R.id.action_update_news:
                Intent intent = new Intent(mContext, UpdateNewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.NEWS_BUNDLE_KEY, newsId);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                return true;
            case R.id.action_delete_news:
                new RestConnector(new Delete(newsId), Constant.NEWS_ROUTE).execute();
                return true;
            default:
        }
        return false;
    }

    private boolean startServiceWithAction(String action) {
        Intent intent = new Intent(mContext, SubscriptionService.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.NEWS_BUNDLE_KEY, newsId);
        intent.setAction(action);
        intent.putExtras(bundle);
        mContext.startService(intent);
        return true;
    }
}