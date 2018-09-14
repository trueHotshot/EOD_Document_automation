package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.Department;
import pl.coderslab.model.User;
import pl.coderslab.repository.DepartmentRepository;
import pl.coderslab.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    UserRepository userRepository;

    @ModelAttribute("users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @RequestMapping("/departments")
    public String getDepartments(Model model) {
        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);
        model.addAttribute("page", "Departments");
        return "department/content";
    }

    @GetMapping("/departments/new")
    public String addDepartment(Model model) {
        Department department = new Department();
        model.addAttribute("department", department);
        model.addAttribute("page", "Add new department");
        return "department/form";
    }

    @PostMapping("/departments/new")
    public String saveDepartment(@Valid Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("page", "Add new department");
            return "department/form";
        }
        departmentRepository.save(department);

        return "redirect:/departments";
    }

    @RequestMapping("/departments/{id}/edit")
    public String editDepartment(@PathVariable long id, Model model) {
        Department department = departmentRepository.findOne(id);
        model.addAttribute("department", department);
        model.addAttribute("page", "Change department name");
        return "department/form";
    }

    @RequestMapping("/departments/{id}")
    public String showDepartment(@PathVariable long id, Model model) {
        Department department = departmentRepository.findOne(id);
        model.addAttribute("department", department);
        model.addAttribute("page", "Department: "+department.getName());
        return "department/details";
    }

    @RequestMapping("/departments/{id}/add")
    public String addUserToDepartment(@PathVariable long id, Model model) {
        Department department = departmentRepository.findOne(id);
        model.addAttribute("department", department);
        model.addAttribute("page", "Department: "+department.getName()+" - Add member");
        return "department/add-user";
    }

    @RequestMapping("/departments/{id}/add/{userId}")
    public String saveUserToDepartment(@PathVariable long id, @PathVariable long userId, Model model) {
        Department department = departmentRepository.findOne(id);
        User user = userRepository.findOne(userId);
        List<User> members = department.getMembers();
        if(departmentRepository.countByMembers(user) == 0) {
            members.add(user);
            department.setMembers(members);
            departmentRepository.save(department);
        }

        return "redirect:/departments/{id}";
    }

}
