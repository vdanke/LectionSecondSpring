package org.step.lection.second.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.step.lection.second.spring.model.User;
import org.step.lection.second.spring.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/api/v1/main")
public class MainController {

    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getMainPageWithModel(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @SessionAttribute(name = "user", required = false) User user,
                                       Model model) {
        List<User> allUsers = userService.findAll();

        model.addAttribute("users", allUsers);

        return "index";
    }

    @GetMapping("/view")
    public ModelAndView getMainPageWithModelAndView() {
        ModelAndView index = new ModelAndView("index");

        List<User> allUsers = userService.findAll();

        index.addObject("users", allUsers);

        return index;
    }

    @PostMapping("/user/save")
    public String saveUser(@RequestParam(name = "username") String username,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "age") int age,
                           Model model) {
        User user = new User(username, password, age);

        boolean isSaved = userService.save(user);

        model.addAttribute("isSaved", isSaved);
        model.addAttribute("username", user.getUsername());

        return "index";
    }

    @GetMapping("/users/{id}")
    public String updateUser(@PathVariable(name = "id") String id, Model model) {
        User userById = userService.findById(id);

        userById.getMessageList()
                .forEach(message -> System.out.println(message.getDescription()));

        model.addAttribute("userById", userById);

        return "index";
    }
}
