package com.example.application.views;

import com.example.application.components.Components;
import com.example.application.security.Roles;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.WebBrowser;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.annotation.Secured;

import java.util.Date;

@Route("")
@PageTitle("HelpMax")
@PermitAll
public class HomeView extends AppLayout {
    private final SecurityService securityService;
    Components components = new Components();
    HorizontalLayout header = new HorizontalLayout();



    public HomeView(SecurityService securityService) {
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
        header.add(toggle, components.logotipSite(), logout);
        addToNavbar(header);
        addToDrawer(scroller);

        checkAgreementStatus();




    }


    private void checkAgreementStatus() {
        // Получаем текущую сессию
        VaadinSession session = VaadinSession.getCurrent();

        // Проверяем, было ли соглашение принято
        if (session != null && session.getAttribute("agreementAccepted") != null && (boolean) session.getAttribute("agreementAccepted")) {
            // Если соглашение принято, показываем текст
            H6 agreementText = new H6("Соглашение принято");
            agreementText.addClassNames("text-success", "m-t-s"); // Можно добавить стили
            header.add(agreementText); // Добавляем текст в верхнюю часть макета
        }
    }


    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Android TV", "/android"));
        return sideNav;
    }


}





