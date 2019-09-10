package com.newsman.newsman.auxiliary.manu_helpers;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.newsman.newsman.R;
import com.newsman.newsman.activities.LoginActivity;
import com.newsman.newsman.activities.SettingsActivity;
import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.LoginState;

public class LoginMenuInflater {
    public static void inflateLogin(MenuInflater inflater, Menu menu){
        inflater.inflate(R.menu.login_menu,menu);
        if(LoginState.getInstance().getUserId() == Constant.INVALID_USER_ID){
            menu.findItem(R.id.action_user_account).setVisible(false);
            menu.findItem(R.id.action_logout).setVisible(false);
        }
        else{
            menu.findItem(R.id.action_login).setVisible(false);
            menu.findItem(R.id.action_user_account)
                    .setTitle(LoginState.getInstance().getUser().getUsername());
        }
    }

    public static boolean handleOnMenuItemClick(MenuItem item, Context context){
        if(item.getItemId() == R.id.action_login){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else if(item.getItemId() == R.id.action_settings){
            Intent intent = new Intent(context, SettingsActivity.class);
            context.startActivity(intent);
            return true;
        } else if(item.getItemId() == R.id.action_logout){
            LoginState.logout();
            Intent intent = new Intent(context, SettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }
}
