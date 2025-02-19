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
        StreamResource imageResource = new StreamResource("Emblems.png",
                () -> getClass().getResourceAsStream("/images/Emblems.png"));
        Image image = new Image(imageResource, "My Streamed Image");
        image.setHeight("81px");
        image.setWidth("150px");
        return image;
    }



}
