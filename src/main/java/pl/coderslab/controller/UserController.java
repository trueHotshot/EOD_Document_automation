package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.Department;
import pl.coderslab.model.Folder;
import pl.coderslab.model.Role;
import pl.coderslab.model.User;
import pl.coderslab.repository.DepartmentRepository;
import pl.coderslab.repository.RoleRepository;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.UserService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    DepartmentRepository departmentRepository;

    @ModelAttribute("departments")
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    @Autowired
    RoleRepository roleRepository;

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @RequestMapping("/users")
    public String findUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("page", "Users");
        return "user/content";
    }

    @GetMapping("/users/new")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("page", "Add new user");
        return "user/form";
    }

    @PostMapping("users/new")
    public String saveUser(@Valid User user, BindingResult result, Model model) {
        user.setUsername(user.getEmail());
        if (result.hasErrors()) {
            model.addAttribute("page", "Add new user");
            return "user/form";
        }

        userService.saveUser(user);

        return "redirect:/users";
    }

    @RequestMapping("users/{id}/edit")
    public String editUser(@PathVariable long id, Model model) {
        User user = userRepository.findOne(id);
        model.addAttribute("user", user);
        return "user/form";
    }

}
