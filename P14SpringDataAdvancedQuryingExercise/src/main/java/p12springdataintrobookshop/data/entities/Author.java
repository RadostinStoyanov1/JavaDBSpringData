package p12springdataintrobookshop.data.entities;

import jakarta.persistence.*;
import p12springdataintrobookshop.data.entities.base.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
public class Author extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @OneToMany(mappedBy = "author", targetEntity = Book.class, fetch = FetchType.EAGER)
    private Set<Book> books;

    public Author(String firstName, String lastName, Set<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }
    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = new HashSet<>();
    }

    public Author() {
        this.books = new HashSet<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
