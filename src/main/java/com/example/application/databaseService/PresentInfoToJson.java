package com.example.application.databaseService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class PresentInfoToJson {
    private String url = "jdbc:sqlite:C:/helper/helper.db";

    public PresentInfoToJson() {

    }

    public String getDatabaseAsJson() {
        JSONArray resultJsonArray = new JSONArray();

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                // SQL-запрос для выборки всех записей из таблицы user_info
                String sqlUserInfo = "SELECT id, scene_number, scene_name, title, content FROM user_info";
                PreparedStatement pstmtUserInfo = conn.prepareStatement(sqlUserInfo);
                ResultSet rsUserInfo = pstmtUserInfo.executeQuery();

                while (rsUserInfo.next()) {
                    int id = rsUserInfo.getInt("id");
                    int sceneNumber = rsUserInfo.getInt("scene_number");
                    String sceneName = rsUserInfo.getString("scene_name");
                    String title = rsUserInfo.getString("title");
                    String content = rsUserInfo.getString("content");

                    // Создаем JSON-объект для текущей записи из user_info
                    JSONObject userInfoObject = new JSONObject();
                    userInfoObject.put("id", id);
                    userInfoObject.put("scene_number", sceneNumber);
                    userInfoObject.put("scene_name", sceneName);
                    userInfoObject.put("title", title);
                    userInfoObject.put("content", content);

                    // Запрос для получения фотографий, связанных с этой сценой
                    String sqlPhotos = "SELECT photo_path FROM photos WHERE scene_number = ?";
                    PreparedStatement pstmtPhotos = conn.prepareStatement(sqlPhotos);
                    pstmtPhotos.setInt(1, sceneNumber);
                    ResultSet rsPhotos = pstmtPhotos.executeQuery();

                    // Создаем массив JSON для хранения путей к фотографиям
                    JSONArray photosArray = new JSONArray();
                    while (rsPhotos.next()) {
                        // Убираем лишние обратные слеши
                        String photoPath = rsPhotos.getString("photo_path").replace("\\", "/");
                        photosArray.put(photoPath);
                    }

                    // Добавляем массив фотографий в объект сцены
                    userInfoObject.put("photos", photosArray);

                    // Добавляем объект сцены в общий массив JSON
                    resultJsonArray.put(userInfoObject);
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при работе с базой данных: " + e.getMessage());
            e.printStackTrace();
        }

        // Возвращаем JSON-результат как строку
        return resultJsonArray.toString(1); // Форматируем JSON с отступами для удобства чтения
    }
}
