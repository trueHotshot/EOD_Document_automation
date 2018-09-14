package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
