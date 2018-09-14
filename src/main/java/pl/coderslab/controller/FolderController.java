package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.model.Department;
import pl.coderslab.model.Folder;
import pl.coderslab.repository.DepartmentRepository;
import pl.coderslab.repository.FolderRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class FolderController {
    @Autowired
    FolderRepository folderRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @ModelAttribute("departments")
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    @RequestMapping("/folders")
    public String getFolders(Model model) {
        List<Folder> folders = folderRepository.showAll();
        model.addAttribute("folders", folders);
        model.addAttribute("page", "Folders");
        return "folder/list";
    }

    @RequestMapping("folders/add")
    public String addFolder(Model model) {
        Folder folder = new Folder();
        model.addAttribute("folder", folder);
        return "folder/form";
    }

    @PostMapping("folders/save")
    public String saveFolder(@Valid Folder folder, BindingResult result) {
        if (result.hasErrors()) {
            return "folder/form";
        }

        // set new folder details
        if (folder.getId() == null) {
            folder.setYear(LocalDate.now().getYear());
            Department department = folder.getDepartment();
            folder.setCaseId(folderRepository.countByDepartment(department)+1);
        }

        folderRepository.save(folder);

        return "redirect:/folders";
    }

    @RequestMapping("folders/edit/{id}")
    public String editFolder(@PathVariable long id, Model model) {
        Folder folder = folderRepository.findOne(id);
        model.addAttribute("folder", folder);
        return "folder/form";
    }
}
