package com.example.application.views.adminka;

import com.example.application.databaseService.BdUserinfo;
import com.example.application.databaseService.CrudWelcomeAndroid;
import com.example.application.security.Roles;
import com.example.application.views.HomeView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
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
@Route(value = "serviceWelcomeUserInfo", layout = HomeView.class)
@RolesAllowed(Roles.ADMIN)
public class ServiceAdminWelcomePageAndroidUserInfo extends VerticalLayout {
    private boolean isFormEditOpen = false;
    CrudWelcomeAndroid welcomeAndroid = new CrudWelcomeAndroid();
    private final Grid<BdUserinfo> grid = new Grid<>();

    //Todo слой в котором кнопки создать, редактировать, удалить
    HorizontalLayout buttonLayout = new HorizontalLayout();
    Button buttonCreateButtonLayout = new Button("Создать запись", e -> buttonCreateForms());

    Button buttonEditButtonLayout = new Button("Редактировать запись", e -> buttonEditForms());
    Button buttonDeleteButtonLayout = new Button("Удалить запись", e-> buttonDeleteForms());

    //=============================== Создание записи=====================================
    //Todo форма с полями для создания
    FormLayout formLayoutCreate = new FormLayout();
    //Todo слой где кнопка создать и закрыть
    HorizontalLayout buttonCreateCancel = new HorizontalLayout();

    //=============================== Редактирование записи =====================================
    //Todo форма с полями для редактирования
    FormLayout formLayoutEdit = new FormLayout();
    //Todo слой где кнопка создать и закрыть в редактировании
    HorizontalLayout buttonEditCancel = new HorizontalLayout();
    //=============================== Удаление записи =====================================

    //Todo форма с полями для удаления
    FormLayout formLayoutDelete= new FormLayout();
    //Todo слой где кнопка создать и закрыть в редактировании
    HorizontalLayout buttonDeleteCancel = new HorizontalLayout();

    public ServiceAdminWelcomePageAndroidUserInfo() {
        buttonLayout.add(buttonCreateButtonLayout, buttonEditButtonLayout, buttonDeleteButtonLayout);
        add(createGridUserInfo(), buttonLayout);

    }

    private Grid<BdUserinfo> createGridUserInfo() {
        grid.setItems(welcomeAndroid.printUserInfo());
        grid.addColumn(BdUserinfo::getScene_number).setHeader("Номер сцены");
        grid.addColumn(BdUserinfo::getScene_name).setHeader("Тип сцены");
        grid.addColumn(BdUserinfo::getTitle).setHeader("Заголовок");
        grid.addColumn(BdUserinfo::getContent).setHeader("Содержание");

        grid.addSelectionListener(selection -> {
            selection.getFirstSelectedItem().ifPresent(selectedItem -> {
                int id = selectedItem.getId(); // Получаем ID выбранной записи

                int numberScene = selectedItem.getScene_number();
                String typeScene =selectedItem.getScene_name();
                String titleScene =selectedItem.getTitle();
                String content = selectedItem.getContent();
                // Проверяем состояние флага и выполняем соответствующее действие
                if (isFormEditOpen) {
                    // Если открыта форма редактирования
                    formEdit(numberScene, typeScene, titleScene, content);
                } else {
                    // Если открыта форма удаления
                    formDelete(numberScene);
                }

             //   System.out.println("Выбранный ID: " + id); // Выводим ID в консоль
            });
        });


        return grid;
    }

    //todo ======================Создание=================================================


    private void buttonCreateForms() {
        formCreate();

    }


    private void formCreate() {

        exitLoyalCreate();
        exitLoyalEdit();
        exitLoyalDelete();

        TextField numberScene = new TextField("Номер сцены");
        TextField typeScene = new TextField("Тип сцены");
        typeScene.setPlaceholder("text/photo");
        TextField titleScene = new TextField("Заголовок");
        TextArea content = new TextArea("Содержание");
        Button create = new Button("Создать", e -> {

            if (numberScene.isEmpty() || titleScene.isEmpty() || titleScene.isEmpty() || content.isEmpty()) {

                Notification.show("Заполните все поля").setPosition(Notification.Position.TOP_CENTER);
            } else {
                buttonCreate(Integer.parseInt(numberScene.getValue()), typeScene.getValue(), titleScene.getValue(), content.getValue());
            }
        });
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Закрыть", event -> exitLoyalCreate());
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonCreateCancel.add(cancel, create);
        formLayoutCreate.add(numberScene, typeScene, titleScene, content);
        formLayoutCreate.setColspan(numberScene, 1); // Занимает все 2 колонки
        formLayoutCreate.setColspan(typeScene, 1);   // Занимает 1 колонку (50%)
        formLayoutCreate.setColspan(titleScene, 3);  // Занимает 1 колонку (50%)
        formLayoutCreate.setColspan(content, 8);     // Занимает все 2 колонки

        // Настройка адаптивных шагов
        formLayoutCreate.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),   // При ширине <500px: 1 колонка
                new FormLayout.ResponsiveStep("500px", 8) // При ширине >=500px: 2 колонки
        );
        add(formLayoutCreate, buttonCreateCancel);

    }

    private void buttonCreate(int scene_number, String scene_name, String title, String content) {

        if (scene_number < 0 || scene_name.isEmpty() || title.isEmpty() || content.isEmpty()) {
            Notification.show("Заполните все поля").setPosition(Notification.Position.TOP_CENTER);
            return;
        } else {
            CrudWelcomeAndroid.addUserInfo(scene_number, scene_name, title, content);

            exitLoyalCreate();
            updateGrid();

        }

    }



    //todo ======================Редактирование =================================================

    private void buttonEditForms() {
        formEdit();

    }

    private void formEdit() {
        exitLoyalCreate();
        exitLoyalEdit();
        exitLoyalDelete();
        isFormEditOpen = true;
        TextField numberScene = new TextField("Номер сцены");
        TextField typeScene = new TextField("Тип сцены");
        TextField titleScene = new TextField("Заголовок");
        TextArea content = new TextArea("Содержание");
        Button create = new Button("Редактировать");
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Закрыть", e -> exitLoyalEdit());
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonEditCancel.add(cancel, create);
        formLayoutEdit.add(numberScene, typeScene, titleScene, content);
        formLayoutEdit.setColspan(numberScene, 1); // Занимает все 2 колонки
        formLayoutEdit.setColspan(typeScene, 1);   // Занимает 1 колонку (50%)
        formLayoutEdit.setColspan(titleScene, 3);  // Занимает 1 колонку (50%)
        formLayoutEdit.setColspan(content, 8);     // Занимает все 2 колонки

        // Настройка адаптивных шагов
        formLayoutEdit.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),   // При ширине <500px: 1 колонка
                new FormLayout.ResponsiveStep("500px", 8) // При ширине >=500px: 2 колонки
        );
        add(formLayoutEdit, buttonEditCancel);

    }




    private void formEdit(int numberSceneValue,String typeSceneValue,String titleSceneValue,String contentValue) {
        exitLoyalCreate();
        exitLoyalEdit();
        exitLoyalDelete();
        isFormEditOpen = true;
        TextField numberScene = new TextField("Номер сцены");
                  numberScene.setValue(String.valueOf(numberSceneValue));
        TextField typeScene = new TextField("Тип сцены");
        typeScene.setValue(typeSceneValue);
        TextField titleScene = new TextField("Заголовок");
        titleScene.setValue(titleSceneValue);
        TextArea content = new TextArea("Содержание");
        content.setValue(contentValue);
        Button create = new Button("Редактировать", event -> buttonEdit(Integer.parseInt(numberScene.getValue()),typeScene.getValue(),titleScene.getValue(),content.getValue()));
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Закрыть", e -> exitLoyalEdit());
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonEditCancel.add(cancel, create);
        formLayoutEdit.add(numberScene, typeScene, titleScene, content);
        formLayoutEdit.setColspan(numberScene, 1); // Занимает все 2 колонки
        formLayoutEdit.setColspan(typeScene, 1);   // Занимает 1 колонку (50%)
        formLayoutEdit.setColspan(titleScene, 3);  // Занимает 1 колонку (50%)
        formLayoutEdit.setColspan(content, 8);     // Занимает все 2 колонки

        // Настройка адаптивных шагов
        formLayoutEdit.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),   // При ширине <500px: 1 колонка
                new FormLayout.ResponsiveStep("500px", 8) // При ширине >=500px: 2 колонки
        );
        add(formLayoutEdit, buttonEditCancel);

    }


    private void buttonEdit(int numberSceneValue,String typeSceneValue,String titleSceneValue,String contentValue) {

        CrudWelcomeAndroid.editUserInfo(numberSceneValue,typeSceneValue,titleSceneValue,contentValue);

         exitLoyalEdit();
         updateGrid();


    }



    //TODO=============================== Удаление записи ============================================================


    private void buttonDeleteForms() {
        formDelete();


    }

    private void formDelete () {

        exitLoyalCreate();
        exitLoyalEdit();
        exitLoyalDelete();
        isFormEditOpen = false;
        TextField numberScene = new TextField("Номер сцены");
        Button delete = new Button("Удалить");
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Закрыть", event -> exitLoyalDelete());
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonDeleteCancel.add(cancel, delete);
        formLayoutDelete.add(numberScene);
        formLayoutDelete.setColspan(numberScene, 1); //

        // Настройка адаптивных шагов
        formLayoutDelete.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),   // При ширине <500px: 1 колонка
                new FormLayout.ResponsiveStep("500px", 8) // При ширине >=500px: 2 колонки
        );
        add(formLayoutDelete, buttonDeleteCancel);




    }



    private void formDelete (int number) {

        exitLoyalCreate();
        exitLoyalEdit();
        exitLoyalDelete();
        isFormEditOpen = false;
        TextField numberScene = new TextField("Номер сцены");
        numberScene.setValue(String.valueOf(number));
        Button delete = new Button("Удалить",e-> buttonDelete(Integer.parseInt(numberScene.getValue())));
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Закрыть", event -> exitLoyalDelete());
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonDeleteCancel.add(cancel, delete);
        formLayoutDelete.add(numberScene);
        formLayoutDelete.setColspan(numberScene, 1); //

        // Настройка адаптивных шагов
        formLayoutDelete.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),   // При ширине <500px: 1 колонка
                new FormLayout.ResponsiveStep("500px", 8) // При ширине >=500px: 2 колонки
        );
        add(formLayoutDelete, buttonDeleteCancel);




    }


    private void buttonDelete(int number) {
        CrudWelcomeAndroid.deleteUserInfo(number);

        exitLoyalDelete();
        updateGrid();

    }



    private void exitLoyalEdit() {
        formLayoutEdit.removeAll();
        buttonEditCancel.removeAll();

    }
    private void exitLoyalCreate() {
        formLayoutCreate.removeAll();
        buttonCreateCancel.removeAll();
    }

    private void exitLoyalDelete () {
        formLayoutDelete.removeAll();
        buttonDeleteCancel.removeAll();



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





}
