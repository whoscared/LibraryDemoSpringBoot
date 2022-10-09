package whoscared.springbootlibrary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import whoscared.springbootlibrary.services.LibraryUserDetailsService;

@EnableWebSecurity
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
        http.csrf().disable()//disable protection against cross-site request forgery
                .authorizeRequests()
                //all user have access for this pages
                .antMatchers("/auth/login","/auth/register", "/error").permitAll()
                //for all another pages have access only auth users
                .anyRequest().authenticated()
                //for go to settings for page with login
                .and()
                //for page with authentication
                .formLogin().loginPage("/auth/login")
                //send data to this url
                .loginProcessingUrl("/process_login")
                //after success auth
                .defaultSuccessUrl("/hello", true)
                //if auth not success
                .failureUrl("/auth/login?error");

    }

    //settings of authentication
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
