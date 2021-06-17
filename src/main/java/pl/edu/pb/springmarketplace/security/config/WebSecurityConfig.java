package pl.edu.pb.springmarketplace.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.edu.pb.springmarketplace.appuser.AppUserServiceImpl;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserServiceImpl appUserServiceImpl;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/registration")
                .permitAll()
                .antMatchers("/auction/new")
                .hasAnyAuthority("USER", "MODERATOR", "ADMIN")
                .antMatchers("/auction/*/delete", "/auction/*/publish", "/auction/*/unpublish")
                .hasAnyAuthority("MODERATOR", "ADMIN")
                .antMatchers("/auction")
                .permitAll()
                .antMatchers("/administration")
                .hasAuthority("ADMIN")
                .anyRequest()
                .authenticated().and()
                .formLogin()
                .usernameParameter("email")
                .defaultSuccessUrl("/auction", true)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());

//        auth.inMemoryAuthentication()
//                .withUser("User")
//                .password(bCryptPasswordEncoder.encode("123123"))
//                .roles("USER")
//                .and()
//                .withUser("Moderator")
//                .password(bCryptPasswordEncoder.encode("123123"))
//                .roles("MODERATOR")
//                .and()
//                .withUser("Admin")
//                .password(bCryptPasswordEncoder.encode("123123"))
//                .roles("ADMIN");
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserServiceImpl);
        return provider;
    }

}
