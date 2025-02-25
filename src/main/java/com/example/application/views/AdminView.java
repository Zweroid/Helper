package com.example.application.views;

import com.example.application.security.Roles;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("HelpMax")
@Route(value = "generalAdmin", layout = HomeView.class)
@RolesAllowed(Roles.ADMIN)
public class AdminView extends VerticalLayout {

    public AdminView () {
        H2 text = new H2("Данный раздел находится в разработке");
        add(text);


    }
}
