package com.newsman.newsman.new_rest.dtos;

public class AudioDTO {
    private int Id;
    private String Name;
    private String Description;
    private int BelongsToNewsId;
    private String AudioData;

    public AudioDTO(int id, String name, String description, int belongsToNewsId, String audioData) {
        Id = id;
        Name = name;
        Description = description;
        BelongsToNewsId = belongsToNewsId;
        AudioData = audioData;
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

    public String getAudioData() {
        return AudioData;
    }

    public void setAudioData(String audioData) {
        AudioData = audioData;
    }
}
