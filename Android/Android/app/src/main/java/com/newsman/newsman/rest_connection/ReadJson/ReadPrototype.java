package com.newsman.newsman.rest_connection.ReadJson;

import com.newsman.newsman.rest_connection.WriteJson.WriteComment;
import com.newsman.newsman.rest_connection.WriteJson.WriteNews;
import com.newsman.newsman.rest_connection.WriteJson.WritePicture;
import com.newsman.newsman.rest_connection.WriteJson.WriteSimpleNews;
import com.newsman.newsman.rest_connection.WriteJson.WriteUser;

import java.util.HashMap;

public class ReadPrototype {
    private static ReadPrototype _instance = null;
    public static ReadPrototype getInstance(){
        if(_instance == null)
            _instance = new ReadPrototype();
        return _instance;
    }

    private HashMap<java.lang.Class,ReadJson> protoMap;
    public ReadPrototype() {
        protoMap = new HashMap<>();
        protoMap.put(WriteComment.class ,new ReadComment());
        ReadNewsWithPictureCash readNewsWithPictureCash = new ReadNewsWithPictureCash();
        readNewsWithPictureCash.setReadNews(new ReadNews());
        protoMap.put(WriteNews.class,readNewsWithPictureCash);
        protoMap.put(WritePicture.class, new ReadPicture());
        protoMap.put(WriteSimpleNews.class, new ReadSimpleNews());
    }

    public ReadJson GetReader(java.lang.Class writer){
        return protoMap.get(writer);
    }
}
