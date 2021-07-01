package nl.mattworld.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, String> {
    List<Admin> findAll();
}
