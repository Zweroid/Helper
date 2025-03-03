package com.example.application.databaseService;

public class DbPhotos {
    private int id;
    private int scene_number;
    private String photo_path;


    public DbPhotos(int id, int scene_number, String photo_path) {
        this.id = id;
        this.scene_number = scene_number;
        this.photo_path = photo_path;
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

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
}
