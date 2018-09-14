package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.model.*;
import pl.coderslab.repository.DeliveryRepository;
import pl.coderslab.repository.DepartmentRepository;
import pl.coderslab.repository.DocumentRepository;
import pl.coderslab.repository.FolderRepository;
import pl.coderslab.service.CurrentUser;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DocumentController {
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @ModelAttribute("folders")
    public List<Folder> getFolders() {
        return folderRepository.findAll();
    }

    @ModelAttribute("departments")
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    @ModelAttribute("deliveries")
    public List<Delivery> getDeliveries() {
        return deliveryRepository.findAll();
    }

    @RequestMapping("/documents")
    public String getDocuments(Model model) {
        List<Document> documents = documentRepository.findAll();
        model.addAttribute("documents", documents);
        return "document/list";
    }

    @RequestMapping("documents/new")
    public String addDocument(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        Document document = new Document();
        document.setAuthor(currentUser.getUser());
        document.setCreatedDate(LocalDate.now());
        model.addAttribute("document", document);
        model.addAttribute("page", "Add new document");
        return "document/form";
    }

    @PostMapping("documents/new")
    public String saveNewDocument(@Valid Document document, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("page", "Add new document");
            return "document/form";
        }

        if(document.getFolder() == null) {
            Folder folder = new Folder();
            folder.setYear(document.getCreatedDate().getYear());
            folder.setDepartment(document.getDepartment());
            Department department = folder.getDepartment();
            folder.setCaseId(folderRepository.countByDepartment(department)+1);
            folderRepository.save(folder);
            document.setFolder(folder);

        }
        documentRepository.save(document);

        return "redirect:/folders";
    }
}
