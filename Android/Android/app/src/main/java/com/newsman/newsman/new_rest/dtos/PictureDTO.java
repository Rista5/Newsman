package com.newsman.newsman.new_rest.dtos;

import com.newsman.newsman.server_entities.Picture;

public class PictureDTO {
    private int id;
    private String name;
    private String description;
    private int belongsToNewsId;


    public PictureDTO(int id, String name, String description, int belongsToNewsId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.belongsToNewsId = belongsToNewsId;
    }

    public PictureDTO(Picture picture){
        this.id = picture.getId();
        this.name = picture.getName();
        this.description = picture.getDescription();
        this.belongsToNewsId = picture.getBelongsToNewsId();
    }

    public static Picture getPicture(PictureDTO pictureDTO) {
        return new Picture(
                pictureDTO.id,
                pictureDTO.name,
                pictureDTO.description,
                pictureDTO.belongsToNewsId,
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
}
