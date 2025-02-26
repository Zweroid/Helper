package com.example.application.views.adminka;


import com.example.application.databaseService.BdUserinfo;
import com.example.application.databaseService.CrudWelcomeAndroid;
import com.example.application.security.Roles;
import com.example.application.views.HomeView;

import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("HelpMax")
@Route(value = "serviseAdminWelcomePageAndroid", layout = HomeView.class)
@RolesAllowed(Roles.ADMIN)
public class ServiseAdminWelcomePageAndroid extends VerticalLayout {
    CrudWelcomeAndroid welcomeAndroid = new CrudWelcomeAndroid();


    private final Grid<BdUserinfo> grid = new Grid<>();

    public ServiseAdminWelcomePageAndroid() {




        add(createGridUserInfo());

    }

//TODO================================Left VerticalLayout=================================================//TODO

    private Grid<BdUserinfo> createGridUserInfo () {
        grid.setItems(welcomeAndroid.printUserInfo());
        grid.addColumn(BdUserinfo::getScene_number).setHeader("Номер сцены");
        grid.addColumn(BdUserinfo::getScene_name).setHeader("Тип сцены");
        grid.addColumn(BdUserinfo::getTitle).setHeader("Заголовок");
        grid.addColumn(BdUserinfo::getContent).setHeader("Содержание");

        grid.addSelectionListener(selection -> {
            selection.getFirstSelectedItem().ifPresent(selectedItem -> {
                int id = selectedItem.getId(); // Получаем ID выбранной записи
                System.out.println("Выбранный ID: " + id); // Выводим ID в консоль
            });
        });



        return grid;
    }
     //sceneNumber, String sceneName, String title, String content













}
