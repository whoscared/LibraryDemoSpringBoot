package whoscared.springbootlibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import whoscared.springbootlibrary.models.User;
import whoscared.springbootlibrary.repositories.UserRepository;
import whoscared.springbootlibrary.security.LibraryUserDetails;

import java.util.Optional;

// service for spring security
// this service loads user by login
@Service
public class LibraryUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public LibraryUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username);

        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return new LibraryUserDetails(user.get());
    }
}
