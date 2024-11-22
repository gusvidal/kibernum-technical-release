package SpringSecurityJwt.library.dto;

import lombok.Data;

@Data
public class ReservationDto {
    public String dni;
    public String bookCode;
    public String startDate;
    public String endDate;
}
