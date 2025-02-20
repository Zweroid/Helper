package com.example.application.views;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "android", layout = HomeView.class)
@PageTitle("Admin")
@PermitAll
public class AndroidView extends VerticalLayout {
    HorizontalLayout present = new HorizontalLayout();


    public AndroidView() {

     add(agreement());


    }







    public HorizontalLayout agreement () {
        present.setAlignItems(FlexComponent.Alignment.CENTER);
        present.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
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
        dialog.addCancelListener(event -> buttonNo("Canceled"));

        dialog.setConfirmText("Принять");
        dialog.addConfirmListener(event -> buttonOk());

        dialog.open();

        return present;
    }

    private void buttonNo(String canceled) {
    }

    private void buttonOk() {
    present.setVisible(false);
    present = null;
    }

}
