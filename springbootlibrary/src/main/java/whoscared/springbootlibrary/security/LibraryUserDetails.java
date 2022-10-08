package whoscared.springbootlibrary.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import whoscared.springbootlibrary.models.User;

import java.util.Collection;

public class LibraryUserDetails implements UserDetails {
    private final User libraryUser;

    public LibraryUserDetails(User libraryUser) {
        this.libraryUser = libraryUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.libraryUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.libraryUser.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //needed to get data authentication user
    public User getUser(){
        return this.libraryUser;
    }
}
