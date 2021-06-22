package nl.mattworld.user;

import nl.mattworld.book.Book;

import java.util.List;

public class Parent {

    private String id;
    private String name;
    private String email;
    private String password;
    private String avatarUrl;
    private List<Book> ownedBooks;
    private List<Child> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<Book> getOwnedBooks() {
        return ownedBooks;
    }

    public void setOwnedBooks(List<Book> ownedBooks) {
        this.ownedBooks = ownedBooks;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
