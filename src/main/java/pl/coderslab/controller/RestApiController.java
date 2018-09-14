package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.model.User;
import pl.coderslab.repository.UserRepository;

import java.util.List;
@RestController
public class RestApiController {
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/users/find/{string}")
    public List<User> findUsers(Model model, @PathVariable String string) {
        return userRepository.findByStringLike(string);
    }
}
