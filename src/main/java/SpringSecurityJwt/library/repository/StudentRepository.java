package SpringSecurityJwt.library.repository;

import SpringSecurityJwt.library.model.Book;
import SpringSecurityJwt.library.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Student findStudentByDni(String dni);
}
