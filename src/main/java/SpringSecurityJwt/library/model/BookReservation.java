package SpringSecurityJwt.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="books_reservation")
@Builder
public class BookReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long reservationId;
    @Column(name = "fk_student")
    private Long fkStudent;
    @Column(name = "fk_book")
    private Long fkBook;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "end_date")
    private String endDate;
}
