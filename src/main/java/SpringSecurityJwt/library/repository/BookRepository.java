package SpringSecurityJwt.library.repository;

import SpringSecurityJwt.library.model.Book;
import SpringSecurityJwt.library.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    public Book findBookByCode(String code);
    public void deleteBookByCode(String code);
}
