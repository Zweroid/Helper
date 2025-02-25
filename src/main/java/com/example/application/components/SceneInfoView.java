package com.example.application.components;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SceneInfoView {

    @JsonProperty("scene_number") // Сопоставление с "scene_number" из JSON
    private int sceneNumber;

    @JsonProperty("id") // Сопоставление с "id" из JSON
    private int id;

    @JsonProperty("title") // Сопоставление с "title" из JSON
    private String title;

    @JsonProperty("photos") // Сопоставление с "photos" из JSON
    private List<String> photos;

    @JsonProperty("scene_name") // Сопоставление с "scene_name" из JSON
    private String sceneName;

    @JsonProperty("content") // Сопоставление с "content" из JSON
    private String content;

    // Конструктор без параметров
    public SceneInfoView() {
    }

    // Геттеры и сеттеры

    public int getSceneNumber() {
        return sceneNumber;
    }

    public void setSceneNumber(int sceneNumber) {
        this.sceneNumber = sceneNumber;
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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
