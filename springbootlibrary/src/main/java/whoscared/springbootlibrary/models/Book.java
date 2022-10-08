package whoscared.springbootlibrary.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Title of book should not be empty!")
    @Size(min = 1, max = 30)
    @Column(name = "title")
    private String title;
    @NotEmpty(message = "Author name should not be empty")
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Z]\\w+ [A-Z].([A-Z].|)",
            message = "The author's name must contain the last name and initials with a dot")
    @Column(name = "author")
    private String author;
    @Min(value = 1000, message = "Year should not be < 1000")
    @Column(name = "year")
    private int year;

    @Column(name = "taken_at")
    private Date time;

    @Transient
    private boolean arrears;

    //owning side
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.time = new Date();
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public User getOwner() {
        return owner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


    public boolean getArrears() {
        Date now = new Date();
        long difference = now.getTime() - this.time.getTime();
        int days = (int) (difference / (24 * 60 * 60 * 1000));
        return days > 10;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setArrears(boolean arrears) {
        this.arrears = arrears;
    }
}
