package pl.coderslab.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Department department;

    @ManyToOne
    private Folder folder;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate documentDate;

    private String sender;

    private String description;

    @ManyToOne
    @NotNull
    private Delivery delivery;

    @ManyToOne
    private User author;


    private String note;

//    @PrePersist
//    protected void onCreate() {
//        this.createdDate = LocalDate.now();
//    }

}
