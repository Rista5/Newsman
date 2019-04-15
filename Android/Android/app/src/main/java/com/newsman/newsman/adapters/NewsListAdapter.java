package com.newsman.newsman.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.newsman.newsman.Entities.NewsItem;
import com.newsman.newsman.R;
import com.newsman.newsman.activities.NewsDisplayActivity;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsItemViewHolder> {

    private Context mContext;
    private List<NewsItem> newsItemList;

    public NewsListAdapter(Context context, List<NewsItem> newsItemList) {
        this.mContext = context;
        this.newsItemList = newsItemList;
    }
    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_item, viewGroup,false);
        return new NewsItemViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsItemViewHolder newsItemViewHolder, int position) {
        NewsItem newsItem = newsItemList.get(position);
        newsItemViewHolder.title.setText(newsItem.getTitle());
        newsItemViewHolder.dateModified.setText(newsItem.getLastModified().toString());
        newsItemViewHolder.userModifier.setText(newsItem.getUserModifier());
        newsItemViewHolder.content.setText(newsItem.getContent());

        newsItemViewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    private void showPopupMenu(View view) {
        // menu inflation
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.news_list_item_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuItemClickListener());
        popup.show();
    }



    public class NewsItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title, dateModified, userModifier, content;
        private ImageView backgroud, overflow;


        public NewsItemViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.news_item_title);
            dateModified = (TextView) itemView.findViewById(R.id.news_item_post_date_value);
            userModifier = (TextView) itemView.findViewById(R.id.news_item_post_last_user_update_value);
            content = (TextView) itemView.findViewById(R.id.news_item_text_content);
            backgroud = (ImageView) itemView.findViewById(R.id.news_item_img);
            overflow =  (ImageView) itemView.findViewById(R.id.news_item_overflow_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsDisplayActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getDateModified() {
            return dateModified;
        }

        public void setDateModified(TextView dateModified) {
            this.dateModified = dateModified;
        }

        public TextView getUserModifier() {
            return userModifier;
        }

        public void setUserModifier(TextView userModifier) {
            this.userModifier = userModifier;
        }

        public TextView getContent() {
            return content;
        }

        public void setContent(TextView content) {
            this.content = content;
        }

        public ImageView getOverflow() {
            return overflow;
        }

        public void setOverflow(ImageView overflow) {
            this.overflow = overflow;
        }

        public ImageView getBackgroud() {
            return backgroud;
        }

        public void setBackgroud(ImageView backgroud) {
            this.backgroud = backgroud;
        }
    }

    class MenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MenuItemClickListener() {}

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
}
