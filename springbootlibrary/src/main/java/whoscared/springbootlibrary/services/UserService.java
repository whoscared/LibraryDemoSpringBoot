package whoscared.springbootlibrary.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whoscared.springbootlibrary.models.Book;
import whoscared.springbootlibrary.models.User;
import whoscared.springbootlibrary.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
//readOnly for all methods without Annotation @Transaction
//Annotation for a particular method has higher precedence
@Transactional(readOnly = true)
//transactions start and end in service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void update(int id, User user) {
        //can set id in html form
        user.setId(id);
        //if in database had user with the same id
        //update command is executed
        userRepository.save(user);
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User getOwnerByBook(Book book) {
        return userRepository.getUserByBooksContaining(book);
    }
    public List<Book> getBookByPersonId(int id){
        User user = userRepository.getOne(id);
        // lazy loading -> initialize list independently
        Hibernate.initialize(user.getBooks());
        return user.getBooks();
    }

    public Optional<User> findByLogin (String login){
        return userRepository.findByLogin(login);
    }
}
