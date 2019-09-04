package com.newsman.newsman.auxiliary.manu_inflater;

import android.view.Menu;
import android.view.MenuInflater;

import com.newsman.newsman.R;

public class SettingsMenuInflater {
    public static void inflateSettings(MenuInflater inflater, Menu menu) {
        inflater.inflate(R.menu.settings_menu, menu);

    }
}
