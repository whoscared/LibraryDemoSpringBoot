package whoscared.springbootlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import whoscared.springbootlibrary.models.Book;
import whoscared.springbootlibrary.models.User;
import whoscared.springbootlibrary.services.BookService;
import whoscared.springbootlibrary.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }


    @GetMapping()
    public String allBooks(Model model,
                           // "Integer" because value can be null
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "count", required = false) Integer count,
                           @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        Sort sort = Sort.by("title");
        if (sortByYear) {
            sort = Sort.by("year");
            model.addAttribute("sort", true);
        }
        if (count != null) {
            Page<Book> bookPage = bookService.getPage(page, count, sort);
            List<Book> bookListPage = new ArrayList<>(bookPage.getContent());

            if (page > 0) {
                model.addAttribute("previousPage", page - 1);
            }
            if (page < bookPage.getTotalPages() - 1) {
                model.addAttribute("nextPage", page + 1);
            }

            model.addAttribute("page", page);
            model.addAttribute("books", bookListPage);
            model.addAttribute("count", count);

            return "/book/page";
        }
        model.addAttribute("books", bookService.findAll(sort));
        return ("/book/list");
    }

    @PostMapping("/search")
    public String search(Model model,
                         @RequestParam(value = "queryString", required = false) String queryString) {
        model.addAttribute("bookList", bookService.getByQuerySearch(queryString));
        model.addAttribute("queryString", queryString);
        return "/book/search";
    }

    @GetMapping("/search")
    public String search() {
        return "/book/search";
    }

    @GetMapping("/{id}")
    public String oneBook(@PathVariable("id") int id,
                          Model model,
                          @ModelAttribute("user") User user) {
        Book currentBook = bookService.oneBook(id);
        model.addAttribute("book", currentBook);
        User bookOwner = userService.getOwnerByBook(currentBook);
        if (bookOwner != null) {
            model.addAttribute("owner", bookOwner);
        } else {
            model.addAttribute("users", userService.findAll());
        }
        return "/book/id";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "/book/new";
    }

    @PostMapping()
    public String addBook(@ModelAttribute("book") @Valid Book book,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "book/new";
        }
        bookService.addBook(book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        bookService.releaseBook(id);
        return "redirect:/book/" + id;
    }

    @PatchMapping("/{id}/addowner")
    public String addOwner(@PathVariable("id") int idBook,
                           @ModelAttribute("person") User newOwner) {
        //Book book = bookService.oneBook(idBook);
        bookService.userGetBook(newOwner, idBook);
        return "redirect:/book/" + idBook;
    }
}
