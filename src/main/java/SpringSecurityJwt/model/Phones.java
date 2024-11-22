package SpringSecurityJwt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="phones")
public class Phones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phone")
    private Long idPhone;
    @Column(name = "telefono")
    private Long number;
    @Column(name = "city_code")
    private int citycode;
    @Column(name = "country_code")
    private int countrycode;
}
