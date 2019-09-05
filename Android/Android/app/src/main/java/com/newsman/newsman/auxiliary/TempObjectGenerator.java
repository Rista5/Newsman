package com.newsman.newsman.auxiliary;

import com.newsman.newsman.picture_management.BitmapCache;
import com.newsman.newsman.model.db_entities.SimpleNews;

import java.util.Date;

public class TempObjectGenerator {
    public static SimpleNews genInvalidSimpleNews() {
        return new SimpleNews(Constant.INVALID_NEWS_ID, "", "", new Date(),
                null, BitmapCache.getNonValidIdentifier(), Constant.USER_ID,
                Constant.getThisUser().getUsername());
    }
}
