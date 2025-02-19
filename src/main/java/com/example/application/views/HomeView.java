package com.example.application.views;

import com.example.application.components.Components;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("HelpMax")
@Route("presentUser")
public class HomeView extends AppLayout {
    Components components = new Components();


    public HomeView() {
        addToNavbar(components.logotipSite());
        setClassName("navbar");

        // Настройка основного содержимого
        setContent(new Div(components.agreement()));

//        setJustifyContentMode(JustifyContentMode.CENTER);
//        setAlignItems(Alignment.CENTER);
//        setWidthFull(); // Занимаем всю доступную ширину
//        setHeightFull();
//
//        add(components.widthLayouts());
    }

}
