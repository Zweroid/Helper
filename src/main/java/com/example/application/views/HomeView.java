package com.example.application.views;

import com.example.application.components.Components;
import com.example.application.security.Roles;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;

@Route("")
@PageTitle("HelpMax")
@PermitAll
public class HomeView extends AppLayout {
    private final SecurityService securityService;
    Components components = new Components();


    public HomeView(SecurityService securityService) {
        this.securityService = securityService;
        Button logout = new Button("Выход", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        header.add(components.logotipSite(),logout,components.tabsRoute());

        addToNavbar(header);


    }




}
