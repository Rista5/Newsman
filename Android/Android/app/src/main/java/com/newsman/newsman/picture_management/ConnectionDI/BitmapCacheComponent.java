package com.newsman.newsman.picture_management.ConnectionDI;

import com.newsman.newsman.picture_management.BitmapCache;

import dagger.Component;

@Component(modules = BitmapRestConnectionModule.class)
public interface BitmapCacheComponent {
    BitmapCache createBitmapCache();
}
