package com.newsman.newsman.model.dtos;

import android.graphics.Bitmap;

import com.newsman.newsman.auxiliary.Constant;
import com.newsman.newsman.auxiliary.LoginState;
import com.newsman.newsman.auxiliary.date_helpers.DateAux;
import com.newsman.newsman.model.db_entities.News;
import com.newsman.newsman.model.db_entities.SimpleNews;

import java.text.ParseException;
import java.util.Date;

public class SimpleNewsDTO {
    private int id;
    private String title;
    private String content;
    private String lastModified;
    private UserDTO lastModifiedUser;
    private PictureDTO backgroundPicture;

    public SimpleNewsDTO(int id, String title, String content, String lasModified, UserDTO lastModifiedUser, PictureDTO backgroundPicture) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.lastModified = lasModified;
        this.lastModifiedUser = lastModifiedUser;
        this.backgroundPicture = backgroundPicture;
    }

    public SimpleNewsDTO(News news){
        this.id = news.getId();
        this.title = news.getTitle();
        this.content = news.getContent();
        this.lastModified = news.getLastModified().toString();
        this.lastModifiedUser = new UserDTO(news.getModifierId(),news.getModifierUsername());
        this.backgroundPicture = new PictureDTO(news.getBackgroundId(),"","",news.getId());
    }

    public SimpleNewsDTO(SimpleNews news){
        this.id = news.getId();
        this.title = news.getTitle();
        this.content = news.getContent();
        this.lastModified = news.getLastModified().toString();
        this.lastModifiedUser = new UserDTO(news.getModifierId(),news.getModifierUsername());
        this.backgroundPicture = new PictureDTO(news.getBackgroundId(),"","",news.getId());
    }

    public static SimpleNews getSimpleNews(SimpleNewsDTO dto) {
        Bitmap background = null;
        int backgroundId = Constant.INVALID_PICTURE_ID;
        if(dto.backgroundPicture != null)
            backgroundId = dto.backgroundPicture.getId();
        Date date = null;
        try {
            date = DateAux.getDate(dto.getLasModified());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int userId = LoginState.getInstance().getUserId();
        String username = "";
        if(dto.lastModifiedUser != null) {
            userId = dto.lastModifiedUser.getId();
            username = dto.lastModifiedUser.getUsername();
        }
        return new SimpleNews(
                dto.id,
                dto.title,
                dto.content,
                date,
                null,
                dto.backgroundPicture.getId(),
                userId,
                username
        );
    }

    public SimpleNews getSimpleNews(){
        SimpleNews ret = null;
        try {
            ret = new SimpleNews(
                    id,title,content, DateAux.getDate(lastModified),null,backgroundPicture.getId(),
                    lastModifiedUser.getId(),lastModifiedUser.getUsername()
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLasModified() {
        return lastModified;
    }

    public void setLasModified(String lasModified) {
        this.lastModified = lasModified;
    }

    public UserDTO getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(UserDTO lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser;
    }

    public PictureDTO getBackgroundPicture() {
        return backgroundPicture;
    }

    public void setBackgroundPicture(PictureDTO backgroundPicture) {
        this.backgroundPicture = backgroundPicture;
    }
}
