package com.example.application.views;

import com.example.application.components.Components;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

@Route("")
@PageTitle("HelpMax")
@PermitAll

public class HomeView extends AppLayout {
    private final SecurityService securityService;
    Components components = new Components();
    HorizontalLayout header = new HorizontalLayout();
    H6 agreementText = new H6("Соглашение принято");
    HorizontalLayout present = new HorizontalLayout();



    public HomeView(SecurityService securityService) {

        agreementText.setVisible(false);
        this.securityService = securityService;
        Button logout = new Button("Выход", e -> securityService.logout());
        logout.addThemeVariants(ButtonVariant.LUMO_SMALL);
        DrawerToggle toggle = new DrawerToggle();
        SideNav nav = getSideNav();
        Scroller scroller = new Scroller(nav);
        scroller.setClassName(LumoUtility.Padding.SMALL);
        header.setWidth("100%");
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.add(toggle, components.logotipSite(), logout,agreementText);
        addToNavbar(header);
        addToDrawer(scroller);
        setContent(agreement());

        checkAgreementStatus();


    }



    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Android TV", "/android"));
        return sideNav;
    }

    private void checkAgreementStatus() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session != null && session.getAttribute("agreementAccepted") != null && (boolean) session.getAttribute("agreementAccepted")) {
            agreementText.addClassNames("text-success", "m-t-s");
            agreementText.setVisible(true);

        }
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
        checkAgreementStatus();
        agreementText.addClassNames("text-success", "m-t-s");
        agreementText.setVisible(true);

    }



}





