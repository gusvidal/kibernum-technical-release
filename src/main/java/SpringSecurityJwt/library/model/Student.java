package SpringSecurityJwt.library.model;

import SpringSecurityJwt.model.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student")
    private Long studentId;
    private String dni;
    private String name;
    private String email;
    private String phone;
    private boolean isActive;
/*
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_books",
            joinColumns = @JoinColumn(
                    name = "fk_student", referencedColumnName = "id_student"
            ),
            inverseJoinColumns = @JoinColumn(name = "fk_book", referencedColumnName = "id_book")
    )
    private List<Book> roles = new ArrayList<>();


 */
}
