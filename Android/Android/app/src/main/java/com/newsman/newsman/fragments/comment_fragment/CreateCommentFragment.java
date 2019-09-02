package com.newsman.newsman.fragments.comment_fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.LoginState;
import com.newsman.newsman.new_rest.CommentConnector;
import com.newsman.newsman.server_entities.Comment;
import com.newsman.newsman.thread_management.AppExecutors;

import java.util.Date;

public class CreateCommentFragment extends Fragment {

    private GetCommentResult callback;
    private EditText contentEditText;
    private int newsId;

    public static CreateCommentFragment newInstance(int newsId, GetCommentResult getResult) {
        CreateCommentFragment ccf = new CreateCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.NEWS_BUNDLE_KEY, newsId);
        ccf.setArguments(bundle);
        ccf.callback = getResult;
        return ccf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_comment, container, false);
        ImageView postButton = rootView.findViewById(R.id.create_comment_post);
        contentEditText = rootView.findViewById(R.id.create_comment_content);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(commentValid()) {
                    sendComment();
                } else {
                    commentInvalidToast();
                }
            }
        });
        return rootView;
    }

    public interface GetCommentResult{
        void addComment(Comment comment);
    }

    private Comment createComment() {
        Comment comment = new Comment();
        comment.setId(Constant.INVALID_COMMENT_ID);
        comment.setContent(contentEditText.getText().toString());
        comment.setBelongsToNewsId(getArguments().getInt(Constant.NEWS_BUNDLE_KEY));
        comment.setCreatedById(LoginState.getInstance().getUser().getId());
        comment.setUsername(LoginState.getInstance().getUser().getUsername());
        comment.setPostDate(new Date());
        return comment;
    }

    private void sendComment() {
        Comment comment = createComment();
        clearControls();
        if(callback != null){
            callback.addComment(comment);
        }
//        if(callback == null) {
////                new RestConnector(new Put(new WriteComment(comment)), Constant.COMMENT_ROUTE).execute();
//            clearControls();
//            AppExecutors.getInstance().getNetworkIO()
//                    .execute(CommentConnector.saveComment(getContext(), comment));
//        } else {
//            callback.addComment(comment);
//        }
    }

    private void commentInvalidToast() {
        Toast.makeText(getContext(), R.string.create_comment_invalid_comment_toast, Toast.LENGTH_LONG).show();
    }

    private boolean commentValid() {
        String text = contentEditText.getText().toString();
        return !text.equals("");
    }

    private void clearControls() {
        contentEditText.setText("");
    }
}
