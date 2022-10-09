package whoscared.springbootlibrary.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "library_user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login")
    private String login;
    @NotEmpty(message = "Full name should not be empty!")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+",
            message = "You need to enter the full name according to the example:" +
                    "Senatorova Sofya Stanislavovna")
    @Column(name = "full_name")
    private String fullName;
    @Min(value = 1900, message = "Year of birth should not < 1900")
    @Column(name = "year_of_birth")
    private int year;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public User(int id, String fullName, int yearOfBirth) {
        this.id = id;
        this.fullName = fullName;
        this.year = yearOfBirth;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public int getYear() {
        return year;
    }

    public int getAge() {
        return (2022 - this.year);
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBooks(Book book) {
        if (this.books == null) {
            this.books = new ArrayList<>();
        }
        this.books.add(book);
        book.setOwner(this);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
