package nl.mattworld.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChildRepository extends CrudRepository<Child, String> {
    List<Child> findAllByParentId(String parentId);
}
