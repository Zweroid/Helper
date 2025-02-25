package com.example.application.security;

import com.example.application.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        super.configure(http);
        setLoginView(http, LoginView.class);
    }


    @Bean
    public UserDetailsService users() {
        var max = User.builder()
                .username("Max")
                // password = password with this hash, don't tell anybody :-)
                .password("{bcrypt}$2a$12$JbP.7NR5SRAMkXMXP3RV0eU8s/g.xqUVOnToUZ3414qQge.8.iW0W")//123456
                .roles(Roles.USER)
                .build();
        var bob = User.builder()
                .username("bob")
                // password = password with this hash, don't tell anybody :-)
                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
                .roles(Roles.USER)
                .build();
        var admin = User.builder()
                .username("Admin")
                // password = password with this hash, don't tell anybody :-)
                .password("{bcrypt}$2a$12$6PMeoaTtc4cOJjsII3h1r.yvdlV.fwTFqnlyd4lZYyLoo6l5NeWAu")//Admin
                .roles(Roles.ADMIN, Roles.USER)
                .build();
        return new InMemoryUserDetailsManager(max,admin);
    }
}
