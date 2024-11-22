package SpringSecurityJwt.library.service;

import SpringSecurityJwt.library.dto.DevolucionDto;
import SpringSecurityJwt.library.dto.ReservationDto;
import SpringSecurityJwt.library.model.Book;
import SpringSecurityJwt.library.model.BookReservation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReservaService {
    public String Reservar(ReservationDto reservation);
    public String EliminarReserva(DevolucionDto devol);
    public String ActualizarReservaById(Long reserva);
    public List<?> ListarReservasByDni(String dni);
    public List<?> ListarReservaByBook(String codeBook);

    public ResponseEntity<?> BookSave(Book book);
    public ResponseEntity<?> BookDelete(String code);
    public List<?> ListBookAll();
    public ResponseEntity<?> UpdateBook(Book book);
}
