package com.example.application.components;

import com.example.application.views.AndroidView;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;

public class Components {
    private Tabs tabs = new Tabs();
    private Span status;

    public Div widthLayouts() {
        Div divNew = new Div();
        divNew.setText("проверка шрифтов");
        divNew.setClassName("my-component");


        return divNew;
    }

    //Метод который помещает на HomeView логотип на страницу
    public Image logotipSite() {
        StreamResource imageResource = new StreamResource("Emblems.png",
                () -> getClass().getResourceAsStream("/images/Emblems.png"));
        Image image = new Image(imageResource, "My Streamed Image");
        image.setHeight("89px");
        image.setWidth("165px");
        return image;
    }


    public Tabs tabsRoute() {

        RouterLink routeLink = new RouterLink("Android TV", AndroidView.class);
        ;
        tabs.setClassName("tabs-element");
        Tab tv = new Tab();
        Tab comp = new Tab("Comp");
        tv.add(routeLink);

        tabs.getStyle().set("margin", "auto");
        tabs.setAutoselect(false);
        tabs.add(tv);
        return tabs;


    }


}
