package com.newsman.newsman.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.R;
import com.newsman.newsman.ServerEntities.CommentWithUsername;

import java.util.List;

public class CommentsFragment extends Fragment {

    private List<CommentWithUsername> commentList;
    private LinearLayout llComments;

    public static CommentsFragment newInstance(int newsId, List<CommentWithUsername> comments) {
        CommentsFragment cf = new CommentsFragment();
        cf.commentList = comments;
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.NEWS_BUNDLE_KEY, newsId);
        cf.setArguments(bundle);
        return cf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);
        llComments = rootView.findViewById(R.id.news_item_comments_list);
        displayCommentItems();
        inflateCreateCommentFragment(getArguments().getInt(Constant.NEWS_BUNDLE_KEY));
        return rootView;
    }

    private void inflateCreateCommentFragment(int newsId) {
        CreateCommentFragment ccf = CreateCommentFragment.newInstance(newsId);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.news_item_create_comment_fragment, ccf)
                .commit();
    }

    public void setCommentList(List<CommentWithUsername> comments) {
        this.commentList = comments;
        llComments.removeAllViews();
        displayCommentItems();
    }

    private void displayCommentItems() {
        for(CommentWithUsername c: commentList) {
            View view = getLayoutInflater().inflate(R.layout.comment_item, null);
            final TextView username = view.findViewById(R.id.comment_item_username);
            TextView postDate = view.findViewById(R.id.comment_item_post_date);
            TextView content = view.findViewById(R.id.comment_item_content);
            postDate.setText(c.getPostDate().toString());
            content.setText(c.getContent());
            username.setText(c.getUsername());
            llComments.addView(view);
        }
    }
}
