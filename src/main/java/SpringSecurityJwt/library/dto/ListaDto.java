package SpringSecurityJwt.library.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListaDto {
    public Long reservation;
    public String student;
    public String bookName;
    public String startDate;
    public String endDate;
}
