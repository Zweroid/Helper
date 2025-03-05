package com.example.application.databaseService;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrudPagePhotos {

    public static boolean addPhotos(int scene_number,String photo_path) {
        String sql = "INSERT INTO photos(scene_number, photo_path) VALUES(?, ?)";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, scene_number);
            pstmt.setString(2, photo_path);

            pstmt.executeUpdate();
            Notification.show("Вы успешно дабавили запись в базу данных").setPosition(Notification.Position.TOP_CENTER);
            return true;

        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении записи в user_info: " + e.getMessage());
            return false;
        }
    }

    public static boolean editPgotos(int id,int scene_number,String photo_path) {
        String sql = "UPDATE photos SET scene_number = ?, photo_path = ? where id = ?" ;

        try (Connection conn = DatabaseHelper.connect();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, scene_number);
            pstmt.setString(2, photo_path);
            pstmt.setInt(3,id);

            pstmt.executeUpdate();

            Notification.show("Вы успешно отредактировали запись в базе данных").setPosition(Notification.Position.TOP_CENTER);
            return true;
        } catch (SQLException e) {
            System.out.println("Ошибка при редактировании записи в user_info: " + e.getMessage());
            return false;
        }
    }

    public static boolean deletePhotos(int id) {
        String sql = "DELETE FROM photos WHERE id = ?";
        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            Notification.show("Вы успешно удалили запись в базе данных").setPosition(Notification.Position.TOP_CENTER);
            return true;
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении записи из user_info: " + e.getMessage());
            return false;
        }
    }


    public ListDataProvider<DbPhotos> printPhotos() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;


        try {
            // Получаем соединение с базой данных
            connection = DatabaseHelper.connect();
            // SQL-запрос для выборки всех записей из таблицы
            String query = "SELECT id, scene_number,photo_path FROM photos";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            // Используем createTransactionList для преобразования ResultSet в список BdUserinfo
            List<DbPhotos> dbPhotos = createTransactionList(resultSet);

            // Возвращаем ListDataProvider с данными
            return new ListDataProvider<>(dbPhotos);

        } catch (SQLException e) {
            System.out.println("Ошибка при выполнении запроса: " + e.getMessage());
            return null; // или выбросить исключение, если нужно
        } finally {
            // Закрываем все ресурсы
            DatabaseHelper.close(connection, statement, resultSet);
        }


    }


    private List<DbPhotos> createTransactionList(ResultSet resultSet) throws SQLException {
        List<DbPhotos> transactionsList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("ID");;
            int  sceneNumber = resultSet.getInt("scene_number");;
            String  photo_path = resultSet.getString("photo_path");;


            DbPhotos photos= new DbPhotos(id,sceneNumber,photo_path);
            transactionsList.add(photos);
        }
        return transactionsList;
    }

}
