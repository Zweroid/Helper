package com.example.application.views;

import com.example.application.security.SecurityService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;


import java.util.Map;

@Route(value = "android", layout = HomeView.class)
@PageTitle("Admin")
@PermitAll
public class AndroidView extends VerticalLayout {
    private final SecurityService securityService;
    HorizontalLayout present = new HorizontalLayout();
    SlideShowView slideShowView = new SlideShowView();


    public AndroidView(SecurityService securityService) {
        this.securityService = securityService;

        add(agreement());


    }


    private boolean isAgreementAccepted() {
        VaadinSession session = VaadinSession.getCurrent();
        return session.getAttribute("agreementAccepted") != null && (boolean) session.getAttribute("agreementAccepted");
    }

    private void setAgreementAccepted(boolean accepted) {
        VaadinSession.getCurrent().setAttribute("agreementAccepted", accepted);
    }

    public HorizontalLayout agreement() {
        if (isAgreementAccepted()) {
            // Если пользователь уже принял соглашение, ничего не делаем
            return present;
        }

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
        dialog.addConfirmListener(event -> {
            buttonOk();
            setAgreementAccepted(true); // Устанавливаем флаг принятия соглашения
        });

        dialog.open();
        return present;
    }

    private void buttonNo(String canceled) {
        securityService.logout();
    }

    private void buttonOk() {
        present.setVisible(false);
        textAgreementSucsses();
        UI.getCurrent().getPage().reload();
        present = null;
    }


    private H5 textAgreementSucsses () {

        H5 text = new H5("Соглашение приянто") ;

        return text;
    }

}
