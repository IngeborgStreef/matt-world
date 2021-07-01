package nl.mattworld.user;

import lombok.RequiredArgsConstructor;
import nl.mattworld.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AdminRepository adminRepository;
    private final ParentRepository parentRepository;
    private final ChildRepository childRepository;

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
    public Parent createParent(Parent parent) {
        return parentRepository.save(parent);
    }
    public Child createChild(Child child) {
        return childRepository.save(child);
    }

    public Optional<Admin> findAdminById(String id) {
        return adminRepository.findById(id);
    }
    public Optional<Parent> findParentById(String id) {
        return parentRepository.findById(id);
    }
    public Optional<Child> findChildById(String id) {
        return childRepository.findById(id);
    }

    public List<Admin> retrieveAllAdmins() {
        return adminRepository.findAll();
    }
    public List<Parent> retrieveAllParents() {
        return parentRepository.findAll();
    }
    public List<Child> retrieveAllChildrenFromParent(String parentId) {
        return childRepository.findAllByParentId(parentId);
    }

    public void updateAdmin(String id, Admin update) {
        if (!adminRepository.existsById(id)) {
            throw new NotFoundException("Unable to update. Admin not found by ID: " + id);
        }
        update.setId(id);
        adminRepository.save(update);
    }
    public void updateParent(String id, Parent update) {
        if (!parentRepository.existsById(id)) {
            throw new NotFoundException("Unable to update. Parent not found by ID: " + id);
        }
        update.setId(id);
        parentRepository.save(update);
    }
    public void updateChild(String id, Child update) {
        if (!childRepository.existsById(id)) {
            throw new NotFoundException("Unable to update. Child not found by ID: " + id);
        }
        update.setId(id);
        childRepository.save(update);
    }

    public void deleteAdmin(String id) {
        adminRepository.deleteById(id);
    }
    public void deleteParent(String id) {
        parentRepository.deleteById(id);
    }
    public void deleteChild(String id) {
        childRepository.deleteById(id);
    }

}
