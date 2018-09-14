package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
