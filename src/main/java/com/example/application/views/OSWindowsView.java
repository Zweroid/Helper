package com.example.application.views;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle("HelpMax")
@Route(value = "osWindows", layout = HomeView.class)
@PermitAll
public class OSWindowsView extends VerticalLayout {



    public OSWindowsView () {
        H2 text = new H2("Данный раздел находится в разработке");
        add(text);


    }


}
