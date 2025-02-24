package com.example.application.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;


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
