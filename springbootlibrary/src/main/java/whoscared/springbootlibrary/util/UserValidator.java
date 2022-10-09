package whoscared.springbootlibrary.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import whoscared.springbootlibrary.models.User;
import whoscared.springbootlibrary.services.UserService;

import java.util.Optional;

//use this validator in authController when create a new user
@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        //for objects of which class a validator is needed
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target; //down-casting
        //use service if person not found -> exception
        // !!it is undesirable to rely on exceptions
        Optional<User> findUser = userService.findByLogin(user.getLogin());
        if (findUser.isPresent()){
            errors.rejectValue("login", "",
                    "user with this login already exist");
        }

    }
}
