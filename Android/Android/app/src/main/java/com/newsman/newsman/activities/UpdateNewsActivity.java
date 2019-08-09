package com.newsman.newsman.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newsman.newsman.Auxiliary.Constant;
import com.newsman.newsman.R;

public class UpdateNewsActivity extends AppCompatActivity {

    private int newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_news);
        // verovatno je najbolje da se posalje vest umesto id, ali za sad nej ide iz db
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            newsId = extras.getInt(Constant.NEWS_EXTRA_ID_KEY);
        }

    }
}
