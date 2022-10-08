package whoscared.springbootlibrary.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whoscared.springbootlibrary.models.Book;
import whoscared.springbootlibrary.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByBooksContaining(Book book);
    Optional<User> findByLogin(String login);
}
