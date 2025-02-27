package com.example.application.views.adminka;

import com.example.application.databaseService.BdUserinfo;
import com.example.application.databaseService.CrudWelcomeAndroid;
import com.example.application.security.Roles;
import com.example.application.views.HomeView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.Collections;

@PageTitle("HelpMax")
@Route(value = "serviseAdminWelcomePageAndroid", layout = HomeView.class)
@RolesAllowed(Roles.ADMIN)
public class ServiseAdminWelcomePageAndroid extends VerticalLayout {
    CrudWelcomeAndroid welcomeAndroid = new CrudWelcomeAndroid();

    //=============================== Создание записи=====================================

    //Todo слой в котором кнопки создать, редактировать, удалить
    HorizontalLayout buttonLayout = new HorizontalLayout();

    Button buttonCreate = new Button("Создать запись", e -> buttonCreateForms());
    Button buttonEdit = new Button("Редактировать запись");
    Button buttonDelete = new Button("Удалить запись");
    //Todo форма с полями для создания
    FormLayout formLayoutCreate = new FormLayout();
    //Todo слой где кнопка создать и закрыть
    HorizontalLayout buttonCreateCancel = new HorizontalLayout();

    //=============================== Редактирование записи =====================================







    private final Grid<BdUserinfo> grid = new Grid<>();

    public ServiseAdminWelcomePageAndroid() {
        buttonLayout.add(buttonCreate,buttonEdit,buttonDelete);
        add(createGridUserInfo(),buttonLayout);

    }

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

    //todo ======================Создание=================================================


    private void buttonCreateForms() {
     formCreate();

    }


    private void formCreate () {
       formLayoutCreate.removeAll();
       buttonCreateCancel.removeAll();


        TextField numberScene = new TextField("Номер сцены");

        TextField typeScene = new TextField("Тип сцены");
        TextField titleScene = new TextField("Заголовок");
        TextArea content = new TextArea("Содержание");

        Button create = new Button("Создать",e-> buttonCreate(Integer.parseInt(numberScene.getValue()),typeScene.getValue(),titleScene.getValue(),content.getValue()));
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Закрыть", event -> exitLoyalCreate());
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonCreateCancel.add(cancel,create);
        formLayoutCreate.add(numberScene,typeScene,titleScene,content);
        formLayoutCreate.setColspan(numberScene, 1); // Занимает все 2 колонки
        formLayoutCreate.setColspan(typeScene, 1);   // Занимает 1 колонку (50%)
        formLayoutCreate.setColspan(titleScene, 3);  // Занимает 1 колонку (50%)
        formLayoutCreate.setColspan(content, 8);     // Занимает все 2 колонки

        // Настройка адаптивных шагов
        formLayoutCreate.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),   // При ширине <500px: 1 колонка
                new FormLayout.ResponsiveStep("500px", 8) // При ширине >=500px: 2 колонки
        );
        add(formLayoutCreate,buttonCreateCancel);

    }

    private void buttonCreate(int scene_number , String scene_name, String title,String content) {

        CrudWelcomeAndroid.addUserInfo(scene_number,scene_name,title,content);


      exitLoyalCreate();
      updateGrid();



    }

    private void updateGrid() {
        // Получаем обновленные данные из источника
        ListDataProvider<BdUserinfo> dataProvider = welcomeAndroid.printUserInfo();

        if (dataProvider != null) {
            // Устанавливаем новые данные в Grid
            grid.setItems(dataProvider.getItems());
        } else {
            // Если данные отсутствуют, очищаем Grid
            grid.setItems(Collections.emptyList());
        }
    }


    private void exitLoyalCreate() {
        formLayoutCreate.removeAll();
        buttonCreateCancel.removeAll();
    }





    //todo ======================Редактирование =================================================


}
