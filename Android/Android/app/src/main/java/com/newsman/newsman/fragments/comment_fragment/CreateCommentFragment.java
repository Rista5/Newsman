package com.newsman.newsman.fragments.comment_fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.R;
import com.newsman.newsman.rest_connection.ConnectionStrategy.Put;
import com.newsman.newsman.rest_connection.RestConnector;
import com.newsman.newsman.rest_connection.WriteJson.WriteComment;
import com.newsman.newsman.server_entities.Comment;

import java.util.Date;

public class CreateCommentFragment extends Fragment {

    private EditText contentEditText;
    private int newsId;

    public static CreateCommentFragment newInstance(int newsId) {
        CreateCommentFragment ccf = new CreateCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.NEWS_BUNDLE_KEY, newsId);
        ccf.setArguments(bundle);
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
                Comment comment = createComment();
                new RestConnector(new Put(new WriteComment(comment)), Constant.COMMENT_ROUTE).execute();
            }
        });
        return rootView;
    }

    private Comment createComment() {
        Comment comment = new Comment();
        comment.setId(Constant.INVALID_COMMENT_ID);
        comment.setContent(contentEditText.getText().toString());
        comment.setBelongsToNewsId(getArguments().getInt(Constant.NEWS_BUNDLE_KEY));
        comment.setCreatedById(Constant.USER_ID);
        comment.setPostDate(new Date());
        return comment;
    }
}
