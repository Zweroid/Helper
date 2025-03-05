package com.example.application.views.adminka;

import com.example.application.Settings;
import com.example.application.databaseService.CrudPagePhotos;
import com.example.application.databaseService.CrudPageUserInfo;
import com.example.application.databaseService.DbPhotos;
import com.example.application.security.Roles;
import com.example.application.views.HomeView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.Collections;

@PageTitle("HelpMax")
@Route(value = "serviceWelcomePhoto", layout = HomeView.class)
@RolesAllowed(Roles.ADMIN)
public class ServiceAdminWelcomePageAndroidPhoto extends VerticalLayout {
    private boolean isFormEditOpen = false;
    Settings settings = new Settings();
    HorizontalLayout buttonLayout = new HorizontalLayout();
    Button buttonCreateButtonLayout = new Button("Создать запись", e -> buttonCreateForms());
    Button buttonEditButtonLayout = new Button("Редактировать запись", e -> buttonEditForms());
    Button buttonDeleteButtonLayout = new Button("Удалить запись", e-> buttonDeleteForms());
    private final Grid<DbPhotos> grid = new Grid<>();
    CrudPagePhotos photos = new CrudPagePhotos();

    //=============================== Создание записи=====================================
    //Todo форма с полями для создания
    FormLayout formLayoutCreate = new FormLayout();
    //Todo слой где кнопка создать и закрыть
    HorizontalLayout buttonCreateCancel = new HorizontalLayout();

    //=============================== Редактирование =====================================
    //Todo форма с полями для создания
    FormLayout formLayoutEdit = new FormLayout();
    //Todo слой где кнопка создать и закрыть
    HorizontalLayout buttonEditCancel = new HorizontalLayout();

    //=============================== Удаление записи =====================================

    //Todo форма с полями для удаления
    FormLayout formLayoutDelete = new FormLayout();
    //Todo слой где кнопка создать и закрыть в редактировании
    HorizontalLayout buttonDeleteCancel = new HorizontalLayout();


    public ServiceAdminWelcomePageAndroidPhoto() {

        buttonLayout.add(buttonCreateButtonLayout,buttonEditButtonLayout,buttonDeleteButtonLayout);

        add(createGridPhotos(),buttonLayout);


    }

    private Grid<DbPhotos> createGridPhotos() {

        grid.setItems(photos.printPhotos());
        grid.addColumn(DbPhotos::getId).setHeader("ID");
        grid.addColumn(DbPhotos::getScene_number).setHeader("Номер сцены");
        grid.addColumn(DbPhotos::getPhoto_path).setHeader("Пути к файлам");


        grid.addSelectionListener(selection -> {
            selection.getFirstSelectedItem().ifPresent(selectedItem -> {
               // Получаем ID выбранной записи

                int numberScene = selectedItem.getScene_number();
                String pathFile = selectedItem.getPhoto_path();

                // Проверяем состояние флага и выполняем соответствующее действие
                if (isFormEditOpen) {
                   int id = selectedItem.getId();
                    // Если открыта форма редактирования
                    formEdit(id,numberScene, pathFile);
                } else {
                  int  id = selectedItem.getId();
                    // Если открыта форма удаления
                    formDelete(id);
                }
            });
        });


        return grid;
    }

//TODO ============================Создание============================================================================
    private void buttonCreateForms() {

        removeAll();
        grid.removeAllColumns();
        buttonLayout.add(buttonCreateButtonLayout, buttonEditButtonLayout, buttonDeleteButtonLayout);
        add(createGridPhotos(), buttonLayout);
        exitLoyalCreate();
        exitLoyalEdit();
        exitLoyalDelete();
        formCreate();


    }


    private void formCreate () {


        TextField numberScene = new TextField("Номер сцены");
        TextField pathFile = new TextField("Путь к файлам");
        pathFile.setPlaceholder(settings.getPathPhotoWelcomeAndroid());

        Button create = new Button("Создать",  e -> {

            if (numberScene.isEmpty() || pathFile.isEmpty()) {

                Notification.show("Заполните все поля").setPosition(Notification.Position.TOP_CENTER);
            } else {
                buttonCreate(Integer.parseInt(numberScene.getValue()), pathFile.getValue());
            }
        });

        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Закрыть", event -> exitLoyalCreate());
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        buttonCreateCancel.add(cancel, create);

        formLayoutCreate.add(numberScene, pathFile);
        formLayoutCreate.setColspan(numberScene, 1); // Занимает все 2 колонки
        formLayoutCreate.setColspan(pathFile,4);   // Занимает 1 колонку (50%)

        formLayoutCreate.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),   // При ширине <500px: 1 колонка
                new FormLayout.ResponsiveStep("500px", 8) // При ширине >=500px: 2 колонки
        );
        add(formLayoutCreate, buttonCreateCancel);





    }

    private void buttonCreate(int numberScene, String pathFile) {

        if (numberScene < 0 || pathFile.isEmpty()) {
            Notification.show("Заполните все поля").setPosition(Notification.Position.TOP_CENTER);
            return;
        } else {
           CrudPagePhotos.addPhotos(numberScene,pathFile);

            exitLoyalCreate();
            updateGrid();

        }
    }

    //TODO ============================Редактирование===================================================================


    private void buttonEditForms() {

        removeAll();
        grid.removeAllColumns();
        buttonLayout.add(buttonCreateButtonLayout, buttonEditButtonLayout, buttonDeleteButtonLayout);
        add(createGridPhotos(), buttonLayout);
        exitLoyalCreate();
        exitLoyalEdit();
        exitLoyalDelete();
        formEdit();


    }

    private void formEdit () {
        isFormEditOpen = true;
        TextField id = new TextField("ID");
        TextField numberScene = new TextField("Номер сцены");
        TextField pathFile = new TextField("Путь к файлу");
        Button edit = new Button("Редактировать");
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Закрыть", e -> exitLoyalEdit());
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonEditCancel.add(cancel, edit);
        formLayoutEdit.add(id,numberScene, pathFile);
        formLayoutEdit.setColspan(id,1);
        formLayoutEdit.setColspan(numberScene, 1); // Занимает все 2 колонки
        formLayoutEdit.setColspan(pathFile,4);   // Занимает 1 колонку (50%)


        // Настройка адаптивных шагов
        formLayoutEdit.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),   // При ширине <500px: 1 колонка
                new FormLayout.ResponsiveStep("500px", 8) // При ширине >=500px: 2 колонки
        );
        add(formLayoutEdit, buttonEditCancel);

    }

    private void formEdit (int idValue,int numberSceneValue, String pathFileValue) {
        isFormEditOpen = true;
        exitLoyalCreate();
        exitLoyalEdit();
        exitLoyalDelete();
        TextField id = new TextField("ID");
        id.setValue(String.valueOf(idValue));
        TextField numberScene = new TextField("Номер сцены");
        numberScene.setValue(String.valueOf(numberSceneValue));
        TextField pathFile = new TextField("Путь к файлам");
        pathFile.setValue(pathFileValue);
        Button edit = new Button("Редактировать", event -> buttonEdit(Integer.parseInt(id.getValue()), Integer.parseInt(numberScene.getValue()), pathFile.getValue()));
        edit.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Закрыть", e -> exitLoyalEdit());
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonEditCancel.add(cancel, edit);
        formLayoutEdit.add(id,numberScene, pathFile);
        formLayoutEdit.setColspan(id,1);
        formLayoutEdit.setColspan(numberScene, 1); // Занимает все 2 колонки
        formLayoutEdit.setColspan(pathFile,4);   // Занимает 1 колонку (50%)


        // Настройка адаптивных шагов
        formLayoutEdit.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),   // При ширине <500px: 1 колонка
                new FormLayout.ResponsiveStep("500px", 8) // При ширине >=500px: 2 колонки
        );
        add(formLayoutEdit, buttonEditCancel);

    }

    private void buttonEdit(int id,int numberSceneValue, String pathFileValue) {


        CrudPagePhotos.editPgotos(id,numberSceneValue,pathFileValue);

        exitLoyalEdit();
        updateGrid();


    }


    //TODO ============================Удаление========================================================================
    private void buttonDeleteForms() {
        removeAll();
        grid.removeAllColumns();
        buttonLayout.add(buttonCreateButtonLayout, buttonEditButtonLayout, buttonDeleteButtonLayout);
        add(createGridPhotos(), buttonLayout);
        exitLoyalCreate();
        exitLoyalEdit();
        exitLoyalDelete();

        formDelete();


    }

    private void formDelete () {
        isFormEditOpen = false;
        TextField id = new TextField("ID Фото");
        Button delete = new Button("Удалить");
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Закрыть", event -> exitLoyalDelete());
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonDeleteCancel.add(cancel, delete);
        formLayoutDelete.add(id);
        formLayoutDelete.setColspan(id, 1); //

        // Настройка адаптивных шагов
        formLayoutDelete.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),   // При ширине <500px: 1 колонка
                new FormLayout.ResponsiveStep("500px", 8) // При ширине >=500px: 2 колонки
        );
        add(formLayoutDelete, buttonDeleteCancel);

    }


    private void formDelete (int numberSceneValue ) {
        exitLoyalCreate();
        exitLoyalEdit();
        exitLoyalDelete();
        isFormEditOpen = false;
        TextField id = new TextField("ID Фото");
        id.setValue(String.valueOf(numberSceneValue));
        Button delete = new Button("Удалить",e -> buttonDelete(Integer.parseInt(id.getValue())));
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button cancel = new Button("Закрыть", event -> exitLoyalDelete());
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        buttonDeleteCancel.add(cancel, delete);
        formLayoutDelete.add(id);
        formLayoutDelete.setColspan(id, 1); //

        // Настройка адаптивных шагов
        formLayoutDelete.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),   // При ширине <500px: 1 колонка
                new FormLayout.ResponsiveStep("500px", 8) // При ширине >=500px: 2 колонки
        );
        add(formLayoutDelete, buttonDeleteCancel);

    }

    private void buttonDelete(int parseInt) {
        CrudPagePhotos.deletePhotos(parseInt);

        exitLoyalDelete();
        updateGrid();


    }






    private void exitLoyalCreate() {
        formLayoutCreate.removeAll();
        buttonCreateCancel.removeAll();

    }

    private void exitLoyalEdit() {
        formLayoutEdit.removeAll();
        buttonEditCancel.removeAll();

    }

    private void exitLoyalDelete(){
        formLayoutDelete.removeAll();
        buttonDeleteCancel.removeAll();

    }





    private void updateGrid() {
        // Получаем обновленные данные из источника
        ListDataProvider<DbPhotos> dataProvider = photos.printPhotos();

        if (dataProvider != null) {
            // Устанавливаем новые данные в Grid
            grid.setItems(dataProvider.getItems());
        } else {
            // Если данные отсутствуют, очищаем Grid
            grid.setItems(Collections.emptyList());
        }
    }

}


