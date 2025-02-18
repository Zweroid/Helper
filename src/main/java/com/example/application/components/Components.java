package com.example.application.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Components {



    public Div widthLayouts() {
        Div divNew = new Div();
        divNew.setText("Привет всем это HeplMax сегожня мы будем устанавлиавть прилодения на ваш телевизор, сразу хочу сказать что вы будете делать это сами без посторонней помощи");
        divNew.setClassName("my-component");


        return divNew;
    }
}
