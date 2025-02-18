package com.example.application.views;

import com.example.application.components.Components;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("HelpMax")
@Route("")
public class HomeView extends HorizontalLayout {

    Components components = new Components();





    public HomeView() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setWidthFull(); // Занимаем всю доступную ширину
        setHeightFull();

        add(components.widthLayouts());

    }

}
