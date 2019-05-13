package com.newsman.newsman.Providers;

import com.newsman.newsman.REST.GetFromRest;
import com.newsman.newsman.REST.GetNewsFromRest;

public class NewsProvider implements IProvider {
    @Override
    public GetFromRest GetRestCommunicationObject() {
        return new GetNewsFromRest(Constant.getIpAddress(), Constant.NEWS_BUNDLE_KEY);
    }
}
