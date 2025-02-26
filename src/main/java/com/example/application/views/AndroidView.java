package com.example.application.views;

import com.example.application.components.SceneInfoView;
import com.example.application.databaseService.PresentInfoToJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.PermitAll;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;


@Route(value = "android", layout = HomeView.class)
@PageTitle("HelpMax")
@PermitAll
public class AndroidView extends VerticalLayout {

    private PresentInfoToJson infoToJson = new PresentInfoToJson();


    private String json = infoToJson.getDatabaseAsJson();
    // Создаем ObjectMapper для парсинга JSON
    private ObjectMapper objectMapper = new ObjectMapper();


    public AndroidView() {
        System.out.println(json);

        try {
            // Парсим JSON в список объектов SceneInfoView.Scene
            List<SceneInfoView> scenes = objectMapper.readValue(json, new TypeReference<List<SceneInfoView>>() {
            });

            // Создаем основной контейнер для компонентов
            VerticalLayout layout = new VerticalLayout();

            // Обрабатываем каждую сцену
            for (SceneInfoView scene : scenes) {
                if ("text".equals(scene.getSceneName())) {
                    layout.add(createTextArea(scene));
                } else if ("photo".equals(scene.getSceneName())) {
                    layout.add(createPhotoGallery(scene));
                }
            }

            // Добавляем все компоненты в основной макет
            add(layout);

        } catch (JsonProcessingException e) {
            // Обработка ошибки парсинга JSON
            System.err.println("Ошибка при парсинге JSON: " + e.getMessage());
            throw new RuntimeException("Не удалось загрузить данные из JSON", e);
        }



    }

    private VerticalLayout createTextArea(SceneInfoView sceneInfoView) {
        VerticalLayout verticalLayout = new VerticalLayout();
        Div general = new Div();
        general.addClassName("general-present");
        Div title = new Div();
        title.setWidthFull(); // Уста
        title.addClassName("title-present");
        Div component = new Div();
        component.addClassName("component-present");
        component.setWidthFull(); // Уста

        H5 titleFont = new H5(sceneInfoView.getTitle());
        H5 componentFont = new H5(sceneInfoView.getContent());

        title.add(titleFont);
        component.add(componentFont);

        general.add(title,component);
        verticalLayout.add(general);

        return verticalLayout;
    }

    /**
     * Метод для создания галереи изображений
     */
    private VerticalLayout createPhotoGallery(SceneInfoView sceneInfoView) {
        VerticalLayout verticalLayout = new VerticalLayout();

        Div general = new Div();
        general.addClassName("general-present");
        Div title = new Div();
        title.setWidthFull(); // Уста
        title.addClassName("title-present");
        Div component = new Div();
        component.addClassName("component-present");
        component.setWidthFull(); // Уста


        H5 titleFont = new H5(sceneInfoView.getTitle());

        HorizontalLayout gallery = new HorizontalLayout();

        Scroller scroller = new Scroller();
        scroller.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);
        scroller.setWidth("100%");


        for (String photoPath : sceneInfoView.getPhotos()) {
            try {
                // Создаем изображение с помощью метода createImageFromPath
                Image image = createImageFromPath(photoPath);
                image.setWidth("300px"); // Установите желаемую ширину
                gallery.add(image);

            } catch (RuntimeException e) {
                // Если файл не найден, добавляем сообщение об ошибке
                System.err.println("Ошибка при загрузке изображения: " + e.getMessage());
            }
        }

        scroller.setContent(gallery);
        title.add(titleFont);
        component.add(scroller);
        general.add(title,component);
        verticalLayout.add(general);

        return verticalLayout;
    }

    private Image createImageFromPath(String filePath) {
        File file = new File(filePath);

        // Проверяем, существует ли файл
        if (!file.exists()) {
            throw new RuntimeException("Файл не найден: " + filePath);
        }

        // Создаем StreamResource с ленивой загрузкой InputStream
        StreamResource resource = new StreamResource(file.getName(), () -> {
            try {
                return new FileInputStream(file); // Создаем новый поток каждый раз
            } catch (Exception e) {
                e.printStackTrace();
                return null; // Возвращаем null в случае ошибки
            }
        });

        // Возвращаем объект Image с указанием альтернативного текста
        return new Image(resource, "Изображение: " + file.getName());
    }



}
