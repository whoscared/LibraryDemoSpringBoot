package whoscared.springbootlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import whoscared.springbootlibrary.models.User;
import whoscared.springbootlibrary.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String allPerson(Model model) {
        model.addAttribute("users", userService.findAll());
        return ("user/list");
    }


    @GetMapping("/{id}")
    public String onePerson(@PathVariable("id") int id, Model model) {
        User user = userService.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("books", userService.getBookByPersonId(id).isEmpty() ? null : userService.getBookByPersonId(id));
        return "user/id";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "user/new";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("user") @Valid User user,
                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/new";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findOne(id));
        return ("user/edit");
    }

    @PostMapping("/{id}")
    public String change(@PathVariable("id") int id,
                         @ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ("user/edit");
        }
        userService.update(id, user);
        return ("user/id");
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return ("redirect:/users");
    }

}
