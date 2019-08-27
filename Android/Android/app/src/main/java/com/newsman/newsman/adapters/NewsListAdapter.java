package com.newsman.newsman.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.DateAux;
import com.newsman.newsman.auxiliary.PopUpMenuController;
import com.newsman.newsman.R;
import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.picture_management.BitmapObserver;
import com.newsman.newsman.server_entities.SimpleNews;
import com.newsman.newsman.activities.DisplayNewsActivity;

import java.util.List;
import java.util.Observable;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsItemViewHolder> {

    private Context mContext;
    private List<SimpleNews> newsList;

    public NewsListAdapter(Context context, List<SimpleNews> newsList) {
        this.mContext = context;
        this.newsList = newsList;
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
        String USER_MODIFIER = "USER_MODIFIER_NOT_FOUND";
        SimpleNews news = newsList.get(position);
        final int newsId = news.getId();
        newsItemViewHolder.title.setText(news.getTitle());
        newsItemViewHolder.dateModified.setText(DateAux.formatDate(news.getLastModified()));
        newsItemViewHolder.userModifier.setText(news.getModifierUsername());
        newsItemViewHolder.content.setText(news.getContent());
        if(news.getBackgroundId() != Constant.INVALID_PICTURE_ID) {
            BitmapObserver observer = new BitmapObserver(newsItemViewHolder.background);
            Observable observable = BitmapCache.getInstance().getBitmap(mContext, news.getBackgroundId(), news.getId());
            observable.addObserver(observer);
        }
//            newsItemViewHolder.background.setImageBitmap(news.getBackgroundPicture());

        newsItemViewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpMenuController.showMenu(mContext, view, newsId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setNewsList(List<SimpleNews> news) {
        newsList.clear();
        newsList.addAll(news);
        notifyDataSetChanged();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title, dateModified, userModifier, content;
        private ImageView background, overflow;


        NewsItemViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            title = itemView.findViewById(R.id.news_item_title);
            dateModified = itemView.findViewById(R.id.news_item_post_date_value);
            userModifier = itemView.findViewById(R.id.news_item_post_last_user_update_value);
            content = itemView.findViewById(R.id.news_item_text_content);
            background = itemView.findViewById(R.id.news_item_img);
            overflow = itemView.findViewById(R.id.news_item_overflow_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int positionInList = getAdapterPosition();
                    if(positionInList == -1) return;
                    Intent intent = new Intent(context, DisplayNewsActivity.class);
                    intent.putExtra(Constant.NEWS_BUNDLE_KEY, newsList.get(positionInList).getId());
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

        public ImageView getBackground() {
            return background;
        }

        public void setBackground(ImageView background) {
            this.background = background;
        }


    }
}
