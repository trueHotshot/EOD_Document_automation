package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.Department;
import pl.coderslab.model.User;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Long countByMembers(User user);
}
