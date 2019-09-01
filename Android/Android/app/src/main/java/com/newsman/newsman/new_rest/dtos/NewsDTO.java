package com.newsman.newsman.new_rest.dtos;

import com.newsman.newsman.auxiliary.DateAux;
import com.newsman.newsman.server_entities.Audio;
import com.newsman.newsman.server_entities.Comment;
import com.newsman.newsman.server_entities.News;
import com.newsman.newsman.server_entities.Picture;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NewsDTO {
    private int id;
    private String title;
    private String content;
    private String lastModified;
    private UserDTO lastModifiedUser;
    private PictureDTO backgroundPicture;
    private List<CommentDTO> comments;
    private List<PictureDTO> pictures;
    private List<AudioDTO> audioRecordings;


    public NewsDTO(int id, String title, String content, String lasModified, UserDTO lastModifiedUser,
                   PictureDTO backgroundPicture) {
        this.id = id;
        this.title = title;
        this.content = content;
        lastModified = lasModified;
        this.lastModifiedUser = lastModifiedUser;
        this.backgroundPicture = backgroundPicture;
    }

    public NewsDTO(int id, String title, String content, String lasModified, UserDTO lastModifiedUser,
                   PictureDTO backgroundPicture, List<CommentDTO> comments, List<PictureDTO> pictures,
                   List<AudioDTO> audioRecordings) {
        this.id = id;
        this.title = title;
        this.content = content;
        lastModified = lasModified;
        this.lastModifiedUser = lastModifiedUser;
        this.backgroundPicture = backgroundPicture;
        this.comments = comments;
        this.pictures = pictures;
        this.audioRecordings = audioRecordings;
    }

    public NewsDTO(News news){
        id = news.getId();
        title = news.getTitle();
        content = news.getContent();
        lastModified = news.getLastModified().toString();
        lastModifiedUser = new UserDTO(news.getModifierId(),news.getModifierUsername());
        backgroundPicture = new PictureDTO(news.getBackgroundId(),"","",news.getId());
        comments = new ArrayList<>();
        for(Comment comment: news.getComments()){
            comments.add(new CommentDTO(comment));
        }
        pictures = new ArrayList<>();
        for(Picture picture: news.getPictures()){
            pictures.add(new PictureDTO(picture));
        }
        audioRecordings = new ArrayList<>();
        for(Audio audio:news.getAudioRecordings()){
            audioRecordings.add(new AudioDTO(audio));
        }
    }

    public static News getNews(NewsDTO newsDTO){
        News retVal = new News();
        retVal.setId(newsDTO.id);
        retVal.setTitle(newsDTO.title);
        retVal.setContent(newsDTO.content);
        try {
            retVal.setLastModified(DateAux.getDate(newsDTO.lastModified));
        }catch (ParseException e) {
            e.printStackTrace();
        }
        retVal.setModifierUsername(newsDTO.lastModifiedUser.getUsername());
        retVal.setModifierId(newsDTO.lastModifiedUser.getId());
        retVal.setBackgroundId(newsDTO.backgroundPicture.getId());

        List<Comment> commentList = new ArrayList<>();
        for(CommentDTO comment: newsDTO.comments){
            commentList.add(CommentDTO.getComment(comment));
        }
        retVal.setComments(commentList);

        List<Picture> pictureList = new ArrayList<>();
        for(PictureDTO pictureDTO: newsDTO.pictures){
            pictureList.add(PictureDTO.getPicture(pictureDTO));
        }
        retVal.setPictures(pictureList);

        List<Audio> audioList = new ArrayList<>();
        for(AudioDTO audioDTO: newsDTO.audioRecordings){
            audioList.add(AudioDTO.getAudio(audioDTO));
        }
        retVal.setAudioRecordings(audioList);

        return retVal;
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
        lastModified = lasModified;
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

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<PictureDTO> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureDTO> pictures) {
        this.pictures = pictures;
    }

    public List<AudioDTO> getAudioRecordings() {
        return audioRecordings;
    }

    public void setAudioRecordings(List<AudioDTO> audioRecordings) {
        this.audioRecordings = audioRecordings;
    }
}
