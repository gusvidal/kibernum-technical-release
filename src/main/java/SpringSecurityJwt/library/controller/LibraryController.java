package SpringSecurityJwt.library.controller;

import SpringSecurityJwt.library.dto.DevolucionDto;
import SpringSecurityJwt.library.dto.ReservationDto;
import SpringSecurityJwt.library.model.BookReservation;
import SpringSecurityJwt.library.service.IReservaService;
import SpringSecurityJwt.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library/")
@Tag(name = "Library", description = "Endpoints para la reserva de libros")
public class LibraryController {

    @Autowired
    private IReservaService iReservaService;

    @PostMapping("reserva")
    public ResponseEntity<Response> reservarLibro(@RequestBody ReservationDto reservationDto) {
        Response res = new Response();
        res.response = iReservaService.Reservar(reservationDto);
        return new ResponseEntity<Response>(res, HttpStatus.OK);
    }
    @DeleteMapping("eliminar")
    public ResponseEntity<Response> eliminarReserva(@RequestBody DevolucionDto devolucionDto){
        Response res = new Response();
        res.response = iReservaService.EliminarReserva(devolucionDto);
        return new ResponseEntity<Response>(res, HttpStatus.OK);
    }
    
    @GetMapping("list-dni/{dni}")
    public ResponseEntity<?> listadoPorDni(@PathVariable String dni){
        Response res = new Response();
        try {
            List<BookReservation> list = (List<BookReservation>) iReservaService.ListarReservasByDni(dni);
            if(list.isEmpty()){
                res.response = "El usuario no posee libros en su poder";
                return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(list);
        }catch (Exception e) {
            res.response = "Error al consultar libros!!";
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("list-code/{bookCode}")
    public ResponseEntity<?> listadoPorBookCode(@PathVariable String bookCode){
        Response res = new Response();
        try {
            List<BookReservation> list = (List<BookReservation>) iReservaService.ListarReservaByBook(bookCode);
            if(list.isEmpty()){
                res.response = "No existen pedidos asociados al libro consultado";
                return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok().body(list);
        }catch (Exception e) {
            res.response = "Error al consultar libros!!";
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }
}


