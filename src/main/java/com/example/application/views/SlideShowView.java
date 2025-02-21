package com.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;


public class SlideShowView extends VerticalLayout {

    private final String[] imageUrls = {
            "C:/1/1366342864.jpg",
            "https://via.placeholder.com/300x200?text=Image+2",
            "https://via.placeholder.com/300x200?text=Image+3",
            "https://via.placeholder.com/300x200?text=Image+4",
            "https://via.placeholder.com/300x200?text=Image+5"
    };

    private int currentIndex = 0;
    private Image currentImage;

    public SlideShowView() {

        StreamResource imageResource = new StreamResource("1366342864.jpg",
                () -> getClass().getResourceAsStream("C:/1/1366342864.jpg"));
        Image image = new Image(imageResource, "My Streamed Image");
        // Создаем компонент для отображения изображения
        currentImage = new Image(imageUrls[currentIndex], "Slide Show Image");
        currentImage.setWidth("300px");

        // Кнопки для управления слайдером
        Button prevButton = new Button("Previous", event -> showPreviousImage());
        Button nextButton = new Button("Next", event -> showNextImage());

        // Добавляем кнопки в горизонтальный макет
        HorizontalLayout controls = new HorizontalLayout(prevButton, nextButton);

        // Добавляем изображение и кнопки в основной макет
        add(image, controls);

        // Автоматическое переключение через интервал времени
        getUI().ifPresent(ui -> ui.getPage().executeJs(
                "window.setInterval(() => $0.showNextImage(), 3000);", this));
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % imageUrls.length;
        currentImage.setSrc(imageUrls[currentIndex]);
    }

    private void showPreviousImage() {
        currentIndex = (currentIndex - 1 + imageUrls.length) % imageUrls.length;
        currentImage.setSrc(imageUrls[currentIndex]);
    }
}