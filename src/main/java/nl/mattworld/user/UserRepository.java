package nl.mattworld.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {
    List<User> findAll();
    List<User> findAllByRole(User.Role role);
}
