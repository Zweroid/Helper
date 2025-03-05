package com.example.application;

public class Settings {
    private String pathBDSqlite = "jdbc:sqlite:C:/helper/helper.db";
    private String pathPhotoWelcomeAndroid = "C:\\helper\\photoAndroidWelcome\\";

    public String getPathBDSqlite() {
        return pathBDSqlite;
    }

    public String getPathPhotoWelcomeAndroid() {
        return pathPhotoWelcomeAndroid;
    }
}
