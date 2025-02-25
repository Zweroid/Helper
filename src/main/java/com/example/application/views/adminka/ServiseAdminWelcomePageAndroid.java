package com.example.application.views.adminka;

import com.example.application.security.Roles;
import com.example.application.views.HomeView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("HelpMax")
@Route(value = "serviseAdminWelcomePageAndroid", layout = HomeView.class)
@RolesAllowed(Roles.ADMIN)
public class ServiseAdminWelcomePageAndroid extends VerticalLayout {

    public ServiseAdminWelcomePageAndroid() {
        H2 text = new H2("Данный раздел находится в разработке");
        add(text);


    }
}
