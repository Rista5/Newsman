package com.newsman.newsman.new_rest.dtos;

public class SimpleNewsDTO {
    private int Id;
    private String Title;
    private String Content;
    private String LasModified;
    private UserDTO LastModifiedUser;
    private PictureDTO BackgroundPicture;

    public SimpleNewsDTO(int id, String title, String content, String lasModified, UserDTO lastModifiedUser, PictureDTO backgroundPicture) {
        Id = id;
        Title = title;
        Content = content;
        LasModified = lasModified;
        LastModifiedUser = lastModifiedUser;
        BackgroundPicture = backgroundPicture;
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
}
