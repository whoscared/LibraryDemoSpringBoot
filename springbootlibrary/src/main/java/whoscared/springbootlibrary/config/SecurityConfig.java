package whoscared.springbootlibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import whoscared.springbootlibrary.services.LibraryUserDetailsService;

@EnableWebSecurity
// authorization at the level methods
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LibraryUserDetailsService detailsService;

    @Autowired
    public SecurityConfig(LibraryUserDetailsService detailsService) {
        this.detailsService = detailsService;
    }

    //configure for spring security
    //configure for authorization
    //check which user is getting http from (auth on not)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //all requests are authorized
        //read from top to bottom
        //more specific rules come before more general ones
        http.authorizeRequests()
                .antMatchers("/users", "/book/add", "/book/new").hasRole("ADMIN")
                //all user (guests too) have access for this pages
                .antMatchers("/auth/login", "/auth/register", "/error").permitAll()
                //for all any request have access only these users:
                .anyRequest().hasAnyRole("USER", "ADMIN", "LIBRARIAN")
                //for go to settings for page with login
                .and()
                //for page with authentication
                .formLogin().loginPage("/auth/login")
                //send data to this url
                .loginProcessingUrl("/process_login")
                //after success auth
                .defaultSuccessUrl("/hello", true)
                //if auth not success
                .failureUrl("/auth/login?error")
                .and()
                //delete user from session, user cookies is deleted (in browser)
                .logout().logoutUrl("/logout")
                //success logout -> go to this url
                .logoutSuccessUrl("/auth/login");

    }

    //settings of authentication
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService)
                // encoder password and compare with existing
                .passwordEncoder(getPasswordEncoder());
    }

    //responsible for password encryption
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
