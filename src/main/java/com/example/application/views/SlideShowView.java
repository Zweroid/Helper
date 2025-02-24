package com.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import java.io.File;
import java.io.FileInputStream;



public class SlideShowView extends VerticalLayout {

    // Массив с абсолютными путями к изображениям в файловой системе
    private final String[] imagePaths = {
            "C:/1/1366342864.jpg", // Путь к первому изображению
            "C:/1/2222222.jpeg",     // Путь ко второму изображению
            "C:/1/image3.jpg",     // Путь к третьему изображению
            "C:/1/image4.jpg",     // И так далее...
            "C:/1/image5.jpg"
    };

    private int currentIndex = 0;
    private Image currentImage;

    public SlideShowView() {
        // Создаем изображение из первого пути
        currentImage = createImageFromPath(imagePaths[currentIndex]);
        currentImage.setWidth("400px"); // Устанавливаем фиксированный размер

        // Кнопки для управления слайдером
        Button prevButton = new Button("Previous", event -> showPreviousImage());
        Button nextButton = new Button("Next", event -> showNextImage());

        // Добавляем кнопки в горизонтальный макет
        HorizontalLayout controls = new HorizontalLayout(prevButton, nextButton);

        // Добавляем изображение и кнопки в основной макет
        add(currentImage, controls);

        // Автоматическое переключение через интервал времени
        getUI().ifPresent(ui -> ui.getPage().executeJs(
                "window.setInterval(() => $0.showNextImage(), 3000);", this));
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % imagePaths.length;
        updateImage();
    }

    private void showPreviousImage() {
        currentIndex = (currentIndex - 1 + imagePaths.length) % imagePaths.length;
        updateImage();
    }

    private void updateImage() {
        // Удаляем старое изображение
        remove(currentImage);
        // Создаем новое изображение
        currentImage = createImageFromPath(imagePaths[currentIndex]);
        currentImage.setWidth("400px");
        // Добавляем новое изображение в макет
        add(currentImage);
    }

    private Image createImageFromPath(String filePath) {
        File file = new File(filePath);
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

        return new Image(resource, "Local Image");
    }
}