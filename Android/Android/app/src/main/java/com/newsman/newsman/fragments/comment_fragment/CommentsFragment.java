package com.newsman.newsman.fragments.comment_fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.DateAux;
import com.newsman.newsman.fragments.comment_fragment.delete_strategy.DeleteStrategy;
import com.newsman.newsman.server_entities.Comment;

import java.util.List;

public class CommentsFragment extends Fragment {

    private List<Comment> commentList;
    private LinearLayout llComments;
    private DeleteStrategy deleteStrategy;

    public static CommentsFragment newInstance(int newsId, List<Comment> comments,
                                               DeleteStrategy deleteStrategy) {
        CommentsFragment cf = new CommentsFragment();
        cf.commentList = comments;
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.NEWS_BUNDLE_KEY, newsId);
        cf.setArguments(bundle);
        cf.deleteStrategy = deleteStrategy;
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

    public void setCommentList(List<Comment> comments) {
        this.commentList = comments;
        llComments.removeAllViews();
        displayCommentItems();
    }

    private void displayCommentItems() {
        //TODO Check null reference
        for(Comment c: commentList) {
            View view = getLayoutInflater().inflate(R.layout.comment_item, null);
            final TextView username = view.findViewById(R.id.comment_item_username);
            TextView postDate = view.findViewById(R.id.comment_item_post_date);
            TextView content = view.findViewById(R.id.comment_item_content);
            ImageView delete = view.findViewById(R.id.comment_item_delete);
            setDeleteButton(c.getId(), delete);
            postDate.setText(DateAux.formatDate(c.getPostDate()));
            content.setText(c.getContent());
            username.setText(c.getUsername());
            llComments.addView(view);
        }
    }

    //TODO proveri da li se cuva commentId u listeneru
    private void setDeleteButton(int commentId, ImageView view){
        deleteStrategy.setDelete(commentId, view);
    }
}
