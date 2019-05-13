package com.newsman.newsman.REST;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;

import com.newsman.newsman.ServerEntities.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Me on 1/9/2019.
 */

public class GetUserClass {

    private String ipAddress;

    public GetUserClass(String ip)
    {
        ipAddress = ip + ":52752/api/User/";
    }

    public void Get(final int id, final Handler hanlder) {
        Thread getThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://" +  ipAddress /*+ id*/);
                            HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
                            myConnection.setRequestProperty("Accept", "Application/json");

                            String text = "";
                            ArrayList<User> userList = null;
                            if (myConnection.getResponseCode() == 200) {
                                InputStream responseBody = myConnection.getInputStream();
                                InputStreamReader isr = new InputStreamReader(responseBody, "UTF-8");
                                JsonReader jsonReader = new JsonReader(isr);


//                                jsonReader.beginObject();
//                                while (jsonReader.hasNext()) {
//                                    text += jsonReader.nextName() + ":" + jsonReader.nextString();
//                                    text += "\n\n\n";
//                                }
//                                jsonReader.close();
                                userList = new ArrayList<>();
                                jsonReader.beginArray();
                                while(jsonReader.hasNext()){
                                    userList.add( readUser(jsonReader));
                                }
                                jsonReader.close();

                            }
                            myConnection.disconnect();
                            Message msg = hanlder.obtainMessage();
                            Bundle b = new Bundle();

                            Bundle bundle = new Bundle();
                            //bundle.putString("msg", text);
                            bundle.putSerializable("users", userList);
                            msg.setData(bundle);
                            hanlder.sendMessage(msg);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        getThread.start();
    }

    private User readUser(JsonReader reader) throws IOException {
        String username = null;
        int id =0;
        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("Id")){
                id = reader.nextInt();
            } else if (name.equals("Username")){
                username = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new User(id, username);
    }
}

