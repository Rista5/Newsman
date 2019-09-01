package com.newsman.newsman.new_rest.dtos;

import android.content.Context;

import androidx.versionedparcelable.VersionedParcel;

import com.newsman.newsman.auxiliary.DateAux;
import com.newsman.newsman.server_entities.Audio;
import com.newsman.newsman.server_entities.Comment;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NewsDTO {
    private int Id;
    private String Title;
    private String Content;
    private String LastModified;
    private UserDTO LastModifiedUser;
    private PictureDTO BackgroundPicture;
    private List<CommentDTO> Comments;
    private List<PictureDTO> Pictures;
    private List<AudioDTO> AudioRecordings;


    public NewsDTO(int id, String title, String content, String lasModified, UserDTO lastModifiedUser,
                   PictureDTO backgroundPicture) {
        Id = id;
        Title = title;
        Content = content;
        LastModified = lasModified;
        LastModifiedUser = lastModifiedUser;
        BackgroundPicture = backgroundPicture;
    }

    public NewsDTO(int id, String title, String content, String lasModified, UserDTO lastModifiedUser,
                   PictureDTO backgroundPicture, List<CommentDTO> comments, List<PictureDTO> pictures,
                   List<AudioDTO> audioRecordings) {
        Id = id;
        Title = title;
        Content = content;
        LastModified = lasModified;
        LastModifiedUser = lastModifiedUser;
        BackgroundPicture = backgroundPicture;
        Comments = comments;
        Pictures = pictures;
        AudioRecordings = audioRecordings;
    }

    public NewsDTO(News news){
        Id = news.getId();
        Title = news.getTitle();
        Content = news.getContent();
        LastModified = news.getLastModified().toString();
        LastModifiedUser = new UserDTO(news.getModifierId(),news.getModifierUsername());
        BackgroundPicture = new PictureDTO(news.getBackgroundId(),"","",news.getId());
        Comments = new ArrayList<>();
        for(Comment comment: news.getComments()){
            Comments.add(new CommentDTO(comment));
        }
        Pictures = new ArrayList<>();
        for(Picture picture: news.getPictures()){
            Pictures.add(new PictureDTO(picture));
        }
        AudioRecordings = new ArrayList<>();
        for(Audio audio:news.getAudioRecordings()){
            AudioRecordings.add(new AudioDTO(audio));
        }
    }

    public News getNews(){
        News retVal = new News();
        retVal.setId(this.Id);
        retVal.setTitle(Title);
        retVal.setContent(Content);
        try {
            retVal.setLastModified(DateAux.getDate(LastModified));
        }catch (ParseException e) {
            e.printStackTrace();
        }
        retVal.setModifierUsername(LastModifiedUser.getUsername());
        retVal.setModifierId(LastModifiedUser.getId());
        retVal.setBackgroundId(BackgroundPicture.getId());

        List<Comment> commentList = new ArrayList<>();
        for(CommentDTO comment: Comments){
            commentList.add(comment.getComment());
        }
        retVal.setComments(commentList);

        List<Picture> pictureList = new ArrayList<>();
        for(PictureDTO pictureDTO: Pictures){
            pictureList.add(PictureDTO.getPicture(pictureDTO));
        }
        retVal.setPictures(pictureList);

        List<Audio> audioList = new ArrayList<>();
        for(AudioDTO audioDTO: AudioRecordings){
            audioList.add(AudioDTO.getAudio(audioDTO));
        }
        retVal.setAudioRecordings(audioList);

        return retVal;
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
        return LastModified;
    }

    public void setLasModified(String lasModified) {
        LastModified = lasModified;
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

    public List<CommentDTO> getComments() {
        return Comments;
    }

    public void setComments(List<CommentDTO> comments) {
        Comments = comments;
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
