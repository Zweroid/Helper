package com.example.application.databaseService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrudPageUserInfo {

    public static boolean addUserInfo(int sceneNumber, String sceneName, String title, String content) {
        String sql = "INSERT INTO user_info(scene_number, scene_name, title, content) VALUES(?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sceneNumber);
            pstmt.setString(2, sceneName);
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.executeUpdate();
            Notification.show("Вы успешно дабавили запись в базу данных").setPosition(Notification.Position.TOP_CENTER);
            return true;

        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении записи в user_info: " + e.getMessage());
            return false;
        }
    }

    public static boolean editUserInfo(int sceneNumber, String sceneName, String title, String content) {
        String sql = "UPDATE user_info SET scene_name = ?, title = ?, content = ? WHERE scene_number = ?";
        //UPDATE user_info SET scene_name = "text", title = "22222222", content = "33333333" WHERE scene_number = 1
        try (Connection conn = DatabaseHelper.connect();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sceneName);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            pstmt.setInt(4, sceneNumber);
            pstmt.executeUpdate();

            Notification.show("Вы успешно отредактировали запись в базе данных").setPosition(Notification.Position.TOP_CENTER);
            return true;
        } catch (SQLException e) {
            System.out.println("Ошибка при редактировании записи в user_info: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteUserInfo(int sceneNumber) {
        String sql = "DELETE FROM user_info WHERE scene_number = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sceneNumber);
            pstmt.executeUpdate();
            Notification.show("Вы успешно удалили запись в базе данных").setPosition(Notification.Position.TOP_CENTER);
            return true;
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении записи из user_info: " + e.getMessage());
            return false;
        }
    }

    public ListDataProvider<DbUserInfo> printUserInfo() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;


        try {
            // Получаем соединение с базой данных
            connection = DatabaseHelper.connect();
            // SQL-запрос для выборки всех записей из таблицы
            String query = "SELECT id, scene_number, scene_name, title, content FROM user_info";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            // Используем createTransactionList для преобразования ResultSet в список BdUserinfo
            List<DbUserInfo> userInfoList = createTransactionList(resultSet);

            // Возвращаем ListDataProvider с данными
            return new ListDataProvider<>(userInfoList);

        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении запроса: " + e.getMessage());
            return null; // или выбросить исключение, если нужно
        } finally {
            // Закрываем все ресурсы
            DatabaseHelper.close(connection, statement, resultSet);
        }


    }



    private List<DbUserInfo> createTransactionList(ResultSet resultSet) throws SQLException {
    List<DbUserInfo> transactionsList = new ArrayList<>();
        while (resultSet.next()) {
              int id = resultSet.getInt("ID");;
              int  sceneNumber = resultSet.getInt("scene_number");;
          String  sceneName = resultSet.getString("scene_name");;
          String  title = resultSet.getString("title");;
          String  content = resultSet.getString("content");;

            DbUserInfo userinfo= new DbUserInfo(id,sceneNumber,sceneName,title,content);
            transactionsList.add(userinfo);
        }
        return transactionsList;
    }


}
