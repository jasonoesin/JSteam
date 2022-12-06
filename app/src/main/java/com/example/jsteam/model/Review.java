package com.example.jsteam.model;

public class Review {

    private Integer id;
    private Integer user_id;
    private Integer game_id;
    private String comment;

    public Review(Integer id, Integer user_id, Integer game_id, String comment) {
        this.id = id;
        this.user_id = user_id;
        this.game_id = game_id;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
