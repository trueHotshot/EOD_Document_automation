package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE first_name LIKE %?1% OR last_name LIKE %?1% LIMIT 6", nativeQuery = true)
    List<User> findByStringLike(@Param("string") String string);

    User findByUsername(String username);
}
