package com.newsman.newsman.new_rest.dtos;

import com.newsman.newsman.server_entities.Audio;

import java.util.List;

public class NewsDTO {
    private int Id;
    private String Title;
    private String Content;
    private String LasModified;
    private UserDTO LastModifiedUser;
    private PictureDTO BackgroundPicture;
    private List<CommentDTO> Coments;
    private List<PictureDTO> Pictures;
    private List<AudioDTO> AudioRecordings;


    public NewsDTO(int id, String title, String content, String lasModified, UserDTO lastModifiedUser,
                   PictureDTO backgroundPicture) {
        Id = id;
        Title = title;
        Content = content;
        LasModified = lasModified;
        LastModifiedUser = lastModifiedUser;
        BackgroundPicture = backgroundPicture;
    }

    public NewsDTO(int id, String title, String content, String lasModified, UserDTO lastModifiedUser,
                   PictureDTO backgroundPicture, List<CommentDTO> coments, List<PictureDTO> pictures,
                   List<AudioDTO> audioRecordings) {
        Id = id;
        Title = title;
        Content = content;
        LasModified = lasModified;
        LastModifiedUser = lastModifiedUser;
        BackgroundPicture = backgroundPicture;
        Coments = coments;
        Pictures = pictures;
        AudioRecordings = audioRecordings;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getLasModified() {
        return LasModified;
    }

    public void setLasModified(String lasModified) {
        LasModified = lasModified;
    }

    public UserDTO getLastModifiedUser() {
        return LastModifiedUser;
    }

    public void setLastModifiedUser(UserDTO lastModifiedUser) {
        LastModifiedUser = lastModifiedUser;
    }

    public PictureDTO getBackgroundPicture() {
        return BackgroundPicture;
    }

    public void setBackgroundPicture(PictureDTO backgroundPicture) {
        BackgroundPicture = backgroundPicture;
    }

    public List<CommentDTO> getComents() {
        return Coments;
    }

    public void setComents(List<CommentDTO> coments) {
        Coments = coments;
    }

    public List<PictureDTO> getPictures() {
        return Pictures;
    }

    public void setPictures(List<PictureDTO> pictures) {
        Pictures = pictures;
    }

    public List<AudioDTO> getAudioRecordings() {
        return AudioRecordings;
    }

    public void setAudioRecordings(List<AudioDTO> audioRecordings) {
        AudioRecordings = audioRecordings;
    }
}
