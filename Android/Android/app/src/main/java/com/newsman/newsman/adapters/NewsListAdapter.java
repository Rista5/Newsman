package com.newsman.newsman.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.Auxiliary.PopUpMenuController;
import com.newsman.newsman.ServerEntities.News;
import com.newsman.newsman.R;
import com.newsman.newsman.activities.DisplayNewsActivity;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsItemViewHolder> {

    private Context mContext;
    private List<News> newsItemList;

    public NewsListAdapter(Context context, List<News> newsItemList) {
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
        String USER_MODIFIER = "USER_MODIFIER_NOT_FOUND";
        News news = newsItemList.get(position);
        newsItemViewHolder.title.setText(news.getTitle());
        newsItemViewHolder.dateModified.setText(news.getLastModified().toString());
        newsItemViewHolder.userModifier.setText(USER_MODIFIER);
        newsItemViewHolder.content.setText(news.getContent());
        newsItemViewHolder.setPositionInList(position);

        newsItemViewHolder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpMenuController.showMenu(mContext, view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    public class NewsItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title, dateModified, userModifier, content;
        private ImageView backgroud, overflow;
        private int positionInList = -1;


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
                    if(positionInList == -1) return;
                    Intent intent = new Intent(context, DisplayNewsActivity.class);
                    intent.putExtra(Constant.NEWS_EXTRA_ID_KEY, newsItemList.get(positionInList).getId());
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


        public int getPositionInList() {
            return positionInList;
        }

        public void setPositionInList(int positionInList) {
            this.positionInList = positionInList;
        }

    }
}
