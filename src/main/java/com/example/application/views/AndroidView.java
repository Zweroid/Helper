package com.example.application.views;

import com.example.application.components.UserInfoService;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;


import java.util.Map;

@Route(value = "android", layout = HomeView.class)
@PageTitle("Admin")
@PermitAll
public class AndroidView extends VerticalLayout {
    HomeView homeView;
    UserInfoService userInfoService = new UserInfoService();
    private final SecurityService securityService;

    SlideShowView slideShowView = new SlideShowView();


    public AndroidView(SecurityService securityService) {
        this.securityService = securityService;

        add(userInfoService.userInfo());



    }




}
