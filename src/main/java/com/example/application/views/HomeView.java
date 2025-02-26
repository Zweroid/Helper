package com.example.application.views;

import com.example.application.components.LogotipSite;
import com.example.application.security.Roles;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Route("")
@PageTitle("HelpMax")
@PermitAll

public class HomeView extends AppLayout {
    private final SecurityService securityService;
    LogotipSite components = new LogotipSite();
    HorizontalLayout header = new HorizontalLayout();
    H6 agreementText = new H6("Соглашение принято");
    HorizontalLayout present = new HorizontalLayout();
    Button checkDeviceButton = new Button("Проверить устройство", event -> chekAndroid());


    public HomeView(SecurityService securityService,AuthenticationContext authenticationContext) {




        checkDeviceButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        agreementText.setVisible(false);
        this.securityService = securityService;
        Button logout = new Button("Выход", e -> securityService.logout());
        logout.addThemeVariants(ButtonVariant.LUMO_SMALL);
        DrawerToggle toggle = new DrawerToggle();
        List<SideNav> sideNavs = getSideNav(authenticationContext);
        Div navContainer = new Div();

        for (SideNav nav : sideNavs) {
            Scroller scroller = new Scroller(nav);
            scroller.setClassName(LumoUtility.Padding.SMALL);
            navContainer.add(scroller);
        }


        header.setWidth("100%");
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.add(toggle, components.logotipSite(), logout, agreementText,checkDeviceButton);
        addToNavbar(header);
        addToDrawer(navContainer);
        setContent(agreement());




        checkAgreementStatus();


    }




    private List<SideNav> getSideNav(AuthenticationContext authenticationContext) {
        SideNav user = new SideNav();
        SideNav admin = new SideNav();

        if (authenticationContext.hasRole(Roles.ADMIN)) {

            user.addItem(
                    new SideNavItem("Android TV", "/android"),
                    new SideNavItem("Установка Windows","/osWindows"));

            admin.setLabel("Админ");
            admin.setCollapsible(true);
            admin.addItem(new SideNavItem("Страница Welcome",
                    "/serviseAdminWelcomePageAndroid"));

            return Arrays.asList(user,admin);


        }else {

            user.addItem(
                    new SideNavItem("Android TV", "/android"),
                    new SideNavItem("Установка Windows","/osWindows"));


        }

        return Collections.singletonList(user);
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

    private void chekAndroid() {
        String userAgent = getAndroidVersionFromUserAgent();
        if (userAgent != null && !userAgent.isEmpty()) {

            Notification.show("Это Android устройство. Версия: " + userAgent).setPosition(Notification.Position.BOTTOM_CENTER);

        } else {

            Notification.show("Это не Android устройство").setPosition(Notification.Position.BOTTOM_CENTER);;
        }
    }

    private String getAndroidVersionFromUserAgent() {
        VaadinRequest request = VaadinRequest.getCurrent();
        if (request != null) {
            String userAgent = request.getHeader("User-Agent");

            System.out.println(userAgent);
            if (userAgent != null && userAgent.contains("Android")) {
                return userAgent.split("Android ")[1].split(" ")[0]; // Возвращает версию Android
            }
        }
        return "";
    }

}





