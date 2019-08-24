package com.newsman.newsman.REST.ReadJson;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadComposite extends ReadJson {

    private ReadJson clone;
    private List<ReadJson> composite;

    public ReadComposite(ReadJson readJson) {
        clone = readJson;
        composite = new ArrayList<>();
    }

    @Override
    public void readJson(JsonReader jsonReader) throws IOException {
        jsonReader.beginArray();
        while(jsonReader.hasNext()) {
            ReadJson rj = clone.clone();
            rj.readJson(jsonReader);
            composite.add(rj);
        }
        jsonReader.endArray();
    }

    @Override
    public void updateDB(Context context) {
        for(ReadJson rj: composite) {
            rj.updateDB(context);
        }
    }

    @Override
    public ReadJson clone() {
        return new ReadComposite(clone);
    }
}
