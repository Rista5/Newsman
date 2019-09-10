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
import com.newsman.newsman.auxiliary.date_helpers.DateAux;
import com.newsman.newsman.auxiliary.LoginState;
import com.newsman.newsman.fragments.comment_fragment.delete_strategy.DeleteStrategy;
import com.newsman.newsman.rest_connection.rest_connectors.CommentConnector;
import com.newsman.newsman.model.db_entities.Comment;
import com.newsman.newsman.thread_management.AppExecutors;

import java.util.List;

public class CommentsFragment extends Fragment implements CreateCommentFragment.GetCommentResult, DeleteStrategy.RemoveAtPosition {

    private List<Comment> commentList;
    private LinearLayout llComments;
    private DeleteStrategy deleteStrategy;
    private boolean sendToRest;

    public static CommentsFragment newInstance(int newsId, List<Comment> comments,
                                               DeleteStrategy deleteStrategy, boolean sendToRest) {
        CommentsFragment cf = new CommentsFragment();
        cf.commentList = comments;
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.NEWS_BUNDLE_KEY, newsId);
        cf.setArguments(bundle);
        cf.deleteStrategy = deleteStrategy;
        cf.sendToRest = sendToRest;
        return cf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comments, container, false);
        llComments = rootView.findViewById(R.id.news_item_comments_list);
        displayComments();
        if (getArguments() != null && LoginState.getInstance().getUserId() != Constant.INVALID_USER_ID) {
            inflateCreateCommentFragment(getArguments().getInt(Constant.NEWS_BUNDLE_KEY));
        }
        return rootView;
    }

    @Override
    public void addComment(Comment comment) {
        if(sendToRest) {
            AppExecutors.getInstance().getNetworkIO()
                    .execute(CommentConnector.saveComment(getContext(), comment));
        } else {
            commentList.add(comment);
            refreshList();
        }
    }

    @Override
    public void removeAt(int position) {
        if(sendToRest) {
            Comment c =commentList.get(position);
            CommentConnector.deleteComment(getContext(), c.getId());
        } else {
            commentList.remove(position);
            refreshList();
        }
    }

    private void inflateCreateCommentFragment(int newsId) {
        CreateCommentFragment ccf = CreateCommentFragment.newInstance(newsId, this);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.news_item_create_comment_fragment, ccf)
                .commit();
    }

    private void refreshList() {
        llComments.removeAllViews();
        displayComments();
    }

    public void setCommentList(List<Comment> comments) {
        this.commentList = comments;
        refreshList();
    }

    private void displayComments() {
        for(Comment c: commentList) {
            View view = getLayoutInflater().inflate(R.layout.comment_item, null);
            final TextView username = view.findViewById(R.id.comment_item_username);
            TextView postDate = view.findViewById(R.id.comment_item_post_date);
            TextView content = view.findViewById(R.id.comment_item_content);
            ImageView delete = view.findViewById(R.id.comment_item_delete);
            setDeleteButton(commentList.indexOf(c), delete);
            postDate.setText(DateAux.formatDate(c.getPostDate()));
            content.setText(c.getContent());
            username.setText(c.getUsername());
            llComments.addView(view);
        }
    }

    private void setDeleteButton(int position, ImageView view){
        deleteStrategy.setDelete(position, view, this);
    }
}
