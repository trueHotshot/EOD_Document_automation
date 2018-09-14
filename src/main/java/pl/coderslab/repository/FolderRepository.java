package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.model.Department;
import pl.coderslab.model.Folder;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("SELECT f FROM Folder f ORDER BY f.department.name, f.year, f.caseId")
    List<Folder> showAll();

    // @Query("SELECT COUNT(f) FROM Folder f WHERE f.department.id = department.id")
    Long countByDepartment(Department department);
}
