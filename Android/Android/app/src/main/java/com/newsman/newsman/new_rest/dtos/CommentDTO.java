package com.newsman.newsman.new_rest.dtos;

import com.newsman.newsman.server_entities.Comment;

public class CommentDTO {

    private int id;
    private String content;
    private int belongsToNewsId;
    private UserDTO createdBy;
    private String postDate;

    public CommentDTO(int id, String content, int belongsToNewsId, UserDTO createdBy, String postDate) {
        this.id = id;
        this.content = content;
        this.belongsToNewsId = belongsToNewsId;
        this.createdBy = createdBy;
        this.postDate = postDate;
    }

    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.belongsToNewsId = comment.getBelongsToNewsId();
        this.createdBy = new UserDTO(comment.getCreatedById(),comment.getUsername());
        this.postDate = comment.getPostDate().toString();
    }

    public static Comment getComment(CommentDTO commentDTO){
        return new Comment(
                commentDTO.id,
                commentDTO.content,
                commentDTO.createdBy.getId(),
                commentDTO.belongsToNewsId,
                commentDTO.postDate,
                commentDTO.createdBy.getUsername());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBelongsToNewsId() {
        return belongsToNewsId;
    }

    public void setBelongsToNewsId(int belongsToNewsId) {
        this.belongsToNewsId = belongsToNewsId;
    }

    public UserDTO getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserDTO createdBy) {
        this.createdBy = createdBy;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
}
