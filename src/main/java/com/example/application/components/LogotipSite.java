package com.example.application.components;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;

public class LogotipSite {

    //Метод который помещает на HomeView логотип на страницу
    public Image logotipSite() {
        StreamResource imageResource = new StreamResource("Emblems.png",
                () -> getClass().getResourceAsStream("/images/Emblems.png"));
        Image image = new Image(imageResource, "My Streamed Image");
        image.setHeight("51px");
        image.setWidth("95px");
        return image;
    }





}
