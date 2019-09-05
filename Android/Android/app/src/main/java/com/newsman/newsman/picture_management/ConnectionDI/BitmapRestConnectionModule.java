package com.newsman.newsman.picture_management.ConnectionDI;

import dagger.Module;
import dagger.Provides;

@Module
public class BitmapRestConnectionModule {
    @Provides
    static BitmapRestConnection provideRestConnection(){
        return new BitmapRetrofitRestConnection();
    }
}
