package com.example.application.views;

import com.example.application.components.UserInfoService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "android", layout = HomeView.class)
@PageTitle("Admin")
@PermitAll
public class AndroidView extends VerticalLayout {
    UserInfoService userInfoService = new UserInfoService();
    SlideShowView slideShowView = new SlideShowView();


    public AndroidView() {


        add(userInfoService.userInfo());



    }




}
