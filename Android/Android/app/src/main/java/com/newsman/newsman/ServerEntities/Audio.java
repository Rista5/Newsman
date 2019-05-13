package com.newsman.newsman.ServerEntities;

import android.graphics.Bitmap;

/**
 * Created by Me on 1/10/2019.
 */

public class Audio {
    private int Id;
    private String Name;
    private String Description;
    private int BelongsToNewsId;
    private byte[] AudioData;

    public Audio(int id, String name, String description, int belongsToNewsId, byte[] audioData) {
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.BelongsToNewsId = belongsToNewsId;
        this.AudioData = audioData;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getBelongsToNewsId() {
        return BelongsToNewsId;
    }

    public void setBelongsToNewsId(int belongsToNewsId) {
        BelongsToNewsId = belongsToNewsId;
    }

    public byte[] getAudioData() {
        return AudioData;
    }

    public void setAudioData(byte[] audioData) {
        AudioData = audioData;
    }
}
