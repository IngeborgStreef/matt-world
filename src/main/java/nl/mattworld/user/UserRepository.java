package nl.mattworld.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    List<User> findAll();
    List<User> findAllByRole(User.Role role);
    Optional<User> findByEmail(String email);
}
