package com.example.application.databaseService;

public class DbUserInfo {

    private int id;
    private int scene_number;
    private String scene_name;
    private String title;
    private String content;

    public DbUserInfo(int id, int sceneNumber, String sceneName, String title, String content) {
        this.id = id;
        this.scene_number = sceneNumber;
        this.scene_name = sceneName;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScene_number() {
        return scene_number;
    }

    public void setScene_number(int scene_number) {
        this.scene_number = scene_number;
    }

    public String getScene_name() {
        return scene_name;
    }

    public void setScene_name(String scene_name) {
        this.scene_name = scene_name;
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

    @Override
    public String toString() {
        return "BdUserinfo{" +
                "id=" + id +
                ", sceneNumber=" + scene_number +
                ", sceneName='" + scene_name + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
