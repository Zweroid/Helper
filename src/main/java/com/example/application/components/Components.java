package com.example.application.components;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

public class Components {

     private Span status;

    public Div widthLayouts() {
        Div divNew = new Div();
        divNew.setText("проверка шрифтов");
        divNew.setClassName("my-component");


        return divNew;
    }
    //Метод который помещает на HomeView логотип на страницу
    public Image logotipSite () {
        StreamResource imageResource = new StreamResource("Emblems.png",
                () -> getClass().getResourceAsStream("/images/Emblems.png"));
        Image image = new Image(imageResource, "My Streamed Image");
        image.setHeight("89px");
        image.setWidth("165px");
        return image;
    }

    // Метод который помещает на HomeView предупреждение для пользователя
    public HorizontalLayout agreement () {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        status = new Span();
        status.setVisible(false);
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setClassName("confirm-dialog");
        dialog.setHeader("Важное предупреждение");
        Html content = new Html("""
            <div>
                <p>Используя наш сайт, вы подтверждаете:</p>
                <ul>
                    <li>Согласие с условиями использования</li>
                    <li>Готовность нести ответственность за установку ПО</li>
                    <li>Ознакомление с инструкциями и предупреждениями</li>
                </ul>
                <p>Мы предоставляем:</p>
                <ul>
                    <li>Бесплатные APK-файлы из открытых источников</li>
                    <li>Подробные инструкции со скриншотами</li>
                    <li>Видео-гайды для каждого этапа установки</li>
                </ul>
                <p>Риски при установке программного обеспечения несет ПОЛЬЗОВАТЕЛЬ. Мы не несем ответственности за возможные проблемы с устройством.</p>
                <p>Следуя нашим инструкциям внимательно и выполняя все шаги правильно, вы сможете успешно установить приложения, и они будут работать корректно.</p>
                <p>Нажимая "Принять", вы принимаете все вышеуказанные условия.</p>
            </div>
            """);
        dialog.setText(content);
        dialog.setCancelable(true);
        dialog.setCancelText("Отказаться");
        dialog.addCancelListener(event -> setStatus("Canceled"));

        dialog.setConfirmText("Принять");
        dialog.addConfirmListener(event -> setStatus("Принято"));


        dialog.open();
        status.setVisible(true);

        return layout;
    }

    private void setStatus(String value) {
        status.setText("Status: " + value);
        status.setVisible(true);
    }

}
