package com.newsman.newsman.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.R;
import com.newsman.newsman.REST.Put.PutCommentToRest;
import com.newsman.newsman.ServerEntities.Comment;

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
        Button postButton = rootView.findViewById(R.id.create_comment_post_button);
        contentEditText = rootView.findViewById(R.id.create_comment_content);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO get user and send new comment to rest
                Comment comment = createComment();
                new PutCommentToRest(comment).put();
            }
        });
        return rootView;
    }

    private Comment createComment() {
        Comment comment = new Comment();
        comment.setId(-1);
        comment.setContent(contentEditText.getText().toString());
        comment.setBelongsToNewsId(getArguments().getInt(Constant.NEWS_BUNDLE_KEY));
        comment.setCreatedById(Constant.USER_ID);
        return comment;
    }
}
