package SpringSecurityJwt.library.repository;

import SpringSecurityJwt.library.model.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<BookReservation,Long> {
    public List<?> findReservaByFkBook(Long id);
    public List<?> findReservasByFkStudent(Long id);
    public List<BookReservation> findReservasByFkStudentAndFkBook(Long fkStudent, Long fkBook);
    public List<BookReservation> findAllByFkStudent(Long id);
}
