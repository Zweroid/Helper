package com.example.application.components;

import com.example.application.views.HomeView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;


public class UserInfoService extends VerticalLayout {



    public TextArea userInfo () {
        TextArea textArea = new TextArea();
        textArea.setWidthFull();
        textArea.setLabel("Знакомство с сервисом и ответы на частые возникающие вопросы");
        textArea.setValue("Это текст Это текст Это текст Это текст Это текст Это текст Это текст Это текст Это текст Это текст Это текст Это текст Это текст Это текст ");
        // Делаем текстовое поле только для чтения
        textArea.setReadOnly(true);



      return textArea;

    }



}
