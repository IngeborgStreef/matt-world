package nl.mattworld.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParentRepository extends CrudRepository<Parent, String> {
    List<Parent> findAll();
}
