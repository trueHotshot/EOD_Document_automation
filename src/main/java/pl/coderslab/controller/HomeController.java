package pl.coderslab.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.service.CurrentUser;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        model.addAttribute("page", "Welcome "+currentUser.getUser().getFirstName());
        return "index";
    }
}
