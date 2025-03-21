package com.example.application.views;

import com.example.application.components.LogotipSite;
import com.example.application.security.Roles;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import java.util.Arrays;
import java.util.List;


@Route("")
@PageTitle("HelpMax")
@PermitAll

public class HomeView extends AppLayout {


    private final SecurityService securityService;
    LogotipSite components = new LogotipSite();

    HorizontalLayout headerToggleAndLogotip = new HorizontalLayout();
    HorizontalLayout headerLogout = new HorizontalLayout();
    H6 agreementText = new H6("Соглашение принято");
    HorizontalLayout present = new HorizontalLayout();
    Button checkDeviceButton = new Button("Проверить устройство", event -> chekAndroid());


    public HomeView(SecurityService securityService, AuthenticationContext authenticationContext) {




        Button sizeScreen = new Button("Получить размер", event -> buttonSize());

        checkDeviceButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
        agreementText.setVisible(false);
        this.securityService = securityService;
        Button logout = new Button("Exit",new Icon(VaadinIcon.EXIT_O), e -> securityService.logout());
        logout.getStyle().set("margin-right","20px");
        logout.addThemeVariants(ButtonVariant.LUMO_SMALL);
        DrawerToggle toggle = new DrawerToggle();
        List<SideNav> sideNavs = getSideNav(authenticationContext);
        Div navContainer = new Div();

        for (SideNav nav : sideNavs) {
            Scroller scroller = new Scroller(nav);
            scroller.setClassName(LumoUtility.Padding.SMALL);
            navContainer.add(scroller);
        }


        headerToggleAndLogotip.setWidth("100%");
        headerToggleAndLogotip.setAlignItems(FlexComponent.Alignment.CENTER);
        headerToggleAndLogotip.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerToggleAndLogotip.add(toggle, components.logotipSite());

        headerLogout.setWidth("100%");
        headerLogout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        headerLogout.add(logout);

        addToNavbar(headerToggleAndLogotip,headerLogout);
        addToDrawer(navContainer);
        setContent(agreement());


        checkAgreementStatus();


    }

    private void buttonSize() {

        UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> {
            // This is your own method that you may do something with the screen width.
            // Note that this method runs asynchronously
            Notification.show("Ширина " + details.getScreenWidth() + " Высота " + details.getScreenHeight()).setPosition(Notification.Position.TOP_CENTER);
        });
    }





    private List<SideNav> getSideNav(AuthenticationContext authenticationContext) {
        SideNav userAndroid = new SideNav();
        SideNav userPC = new SideNav();
        SideNav admin = new SideNav();

        if (authenticationContext.hasRole(Roles.ADMIN)) {

            userAndroid.setLabel("Android TV");
            userAndroid.setCollapsible(true);
            userAndroid.addItem(new SideNavItem("Приветствие", "/androidWelcomePage"));

            userPC.setLabel("Computer");
            userPC.setCollapsible(true);
            userPC.addItem(new SideNavItem("Установка Windows", "/osWindows"));


            admin.setLabel("Админ");
            admin.setCollapsible(true);
            admin.addItem(new SideNavItem("Page SendUser", "/serviceWelcomeUserInfo"), new SideNavItem("Page SendPhoto", "/serviceWelcomePhoto"));

            return Arrays.asList(userAndroid, userPC, admin);


        } else {
            userAndroid.setLabel("Android TV");
            userAndroid.setCollapsible(true);
            userAndroid.addItem(new SideNavItem("Приветствие", "/androidWelcomePage"));

            userPC.setLabel("Computer");
            userPC.setCollapsible(true);
            userPC.addItem(new SideNavItem("Установка Windows", "/osWindows"));


        }

        return Arrays.asList(userAndroid, userPC);
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

            Notification.show("Это не Android устройство").setPosition(Notification.Position.BOTTOM_CENTER);
            ;
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





