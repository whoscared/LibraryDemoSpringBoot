package whoscared.springbootlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import whoscared.springbootlibrary.models.User;
import whoscared.springbootlibrary.services.RegistrationService;
import whoscared.springbootlibrary.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;


    @Autowired
    public AuthController(UserValidator userValidator, RegistrationService registrationService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String checkData(@ModelAttribute("user") @Valid User libraryUser,
                            BindingResult bindingResult) {
        userValidator.validate(libraryUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/register";
        }
        registrationService.register(libraryUser);
        return "redirect:/auth/login";
    }
}
