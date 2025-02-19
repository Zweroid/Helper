package com.example.application.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

public class Components {



    public Div widthLayouts() {
        Div divNew = new Div();
        divNew.setText("проверка шрифтов");
        divNew.setClassName("my-component");


        return divNew;
    }

    public Image logotipSite () {
        StreamResource imageResource = new StreamResource("logo.png",
                () -> getClass().getResourceAsStream("/images/logo.png"));
        Image image = new Image(imageResource, "My Streamed Image");
        image.setHeight("50px");
        image.setWidth("50px");




        return image;
    }



}
