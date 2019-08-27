package com.newsman.newsman.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.newsman.newsman.R;
import com.newsman.newsman.auxiliary.BackArrowHelper;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.thread_management.SubscriptionService;

public class SettingsActivity extends AppCompatActivity {

    private EditText etIpAddresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        BackArrowHelper.displayBackArrow(this);
        setUpViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return BackArrowHelper.backArrowClicked(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpViews() {
        etIpAddresse = findViewById(R.id.settings_ip_adr_et);
        etIpAddresse.setText(Constant.getIpAddress());
    }

    private void setBtnClickListener() {
        findViewById(R.id.settings_set_ip_adr_btn).setOnClickListener(v -> {
            int validDots = 3;
            int minLen = 7;
            int maxLen = 15;
            String ip = etIpAddresse.getText().toString();
            String[] arr = ip.split("\\.");
            if(arr.length == validDots && ip.length()>=minLen && ip.length()<=maxLen) {
                Constant.setIpAddress(ip);
                Intent intent = new Intent(this, SubscriptionService.class);
                intent.setAction(SubscriptionService.START);
                startService(intent);
            }
        });
    }
}
