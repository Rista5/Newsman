package com.newsman.newsman.model.dtos;

import com.newsman.newsman.model.db_entities.Audio;

public class AudioDTO {
    private int id;
    private String name;
    private String description;
    private int belongsToNewsId;
    private String audioData;

    public AudioDTO(int id, String name, String description, int belongsToNewsId, String audioData) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.belongsToNewsId = belongsToNewsId;
        this.audioData = audioData;
    }

    public AudioDTO(Audio audio){
        this.id = audio.getId();
        this.name = audio.getName();
        this.description = audio.getDescription();
        this.belongsToNewsId = getBelongsToNewsId();
    }

    public static Audio getAudio(AudioDTO audioDTO) {
        return new Audio(
                audioDTO.id,
                audioDTO.name,
                audioDTO.description,
                audioDTO.belongsToNewsId,
                null
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBelongsToNewsId() {
        return belongsToNewsId;
    }

    public void setBelongsToNewsId(int belongsToNewsId) {
        this.belongsToNewsId = belongsToNewsId;
    }

    public String getAudioData() {
        return audioData;
    }

    public void setAudioData(String audioData) {
        this.audioData = audioData;
    }
}
