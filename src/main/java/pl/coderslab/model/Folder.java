package pl.coderslab.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "folders")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Department department;

    private int year;

    private Long caseId;

    public String getClassificationSymbol() {
        return department.getName().substring(0,3).toUpperCase()+"/"+caseId+"/"+year;
    }

}
