package com.example.application.views.adminka;

import com.example.application.security.Roles;
import com.example.application.views.HomeView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("HelpMax")
@Route(value = "serviceWelcomePhoto", layout = HomeView.class)
@RolesAllowed(Roles.ADMIN)
public class ServiceAdminWelcomePageAndroidPhoto extends VerticalLayout {
}
