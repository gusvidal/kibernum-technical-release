package SpringSecurityJwt.library.service;

import SpringSecurityJwt.library.dto.DevolucionDto;
import SpringSecurityJwt.library.dto.ListaDto;
import SpringSecurityJwt.library.dto.ReservationDto;
import SpringSecurityJwt.library.model.Book;
import SpringSecurityJwt.library.model.BookReservation;
import SpringSecurityJwt.library.model.Student;
import SpringSecurityJwt.library.repository.BookRepository;
import SpringSecurityJwt.library.repository.ReservationRepository;
import SpringSecurityJwt.library.repository.StudentRepository;
import SpringSecurityJwt.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService implements IReservaService{

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Value("${nro.max.reservas}")
    private Long maxReserva;

    @Override
    public String Reservar(ReservationDto reservation) {
        Student myStudent;
        Book myBook;
        try {
            myStudent = studentRepository.findStudentByDni(reservation.getDni());
            if (myStudent==null){
                return "El alumno no existe!!";
            }
        } catch (Exception e) {
            return "Error al consultar alumno!! " + e.getMessage();
        }
        try{
            myBook = bookRepository.findBookByCode(reservation.getBookCode());
            if(myBook==null){
                return "El libro no existe!!";
            }
        } catch (Exception e) {
            return "Error al consultar el libro!! " + e.getMessage();
        }

        List<?> yaEstaPedido = reservationRepository.findReservaByFkBook(myBook.getBookId());
        List<?> reservasPorStudent = reservationRepository.findReservasByFkStudent(myStudent.getStudentId());
        List<?> yaReservadoPorStudent = reservationRepository.findReservasByFkStudentAndFkBook(myStudent.getStudentId(),myBook.getBookId());

        // Validamos que no sobrepase los ejemplares disponibles
        if(yaEstaPedido.size()>=myBook.getCopies()){
            return "No se encuentran copias disponibles.";
        }

        // Validamos que el alumno no sobrepase el màximo de libros que puede pedir
        if(reservasPorStudent.size()>=maxReserva){
            return "El alumnno ya ha pedido el maximo de libros disponible (3).";
        }

        // Validamos que el alumno no pueda pedir mas de una copia del mismo libro
        if(!yaReservadoPorStudent.isEmpty()){
            return "No es posible pedir dos ejemplares del mismo libro.";
        }

        if(!myStudent.isActive()){
            return "El alumno " + myStudent.getName() + " se encuentra inhabilitado para pedir libros.";
        }

        BookReservation res = BookReservation.builder()
                .fkStudent(myStudent.getStudentId())
                .fkBook(myBook.getBookId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .build();

        reservationRepository.save(res);

        return "El libro " + myBook.getCode() + " ha sido reservado por " + myStudent.getName() + " exitosamente.";
    }

    @Override
    public String EliminarReserva(DevolucionDto devolucionDto) {
        Student myStudent;
        Book myBook;
        try {
            myStudent = studentRepository.findStudentByDni(devolucionDto.getDni());
            if (myStudent==null){
                return "El alumno no existe!!";
            }
        } catch (Exception e) {
            return "Error al consultar alumno!! " + e.getMessage();
        }
        try{
            myBook = bookRepository.findBookByCode(devolucionDto.getBookCode());
            if(myBook==null){
                return "El libro no existe!!";
            }
        } catch (Exception e) {
            return "Error al consultar el libro!! " + e.getMessage();
        }

        List<BookReservation> reservado = reservationRepository.findReservasByFkStudentAndFkBook(myStudent.getStudentId(),myBook.getBookId());

        Long idRes = 0L;
        for (BookReservation res : reservado) {
            idRes = res.getReservationId();
        }

        if (reservado.size()==1 && idRes!=null){
            reservationRepository.deleteById(idRes);
            return "Reserva eliminada con exito.";
        } else {
            return "No se ha podido eliminar la reserva.";
        }
    }

    @Override
    public String ActualizarReservaById(Long reserva) {
        return "";
    }

    @Override
    public List<?> ListarReservasByDni(String dni) {
        Student myStudent;
        Optional<Book> myBook;
        try {
            myStudent = studentRepository.findStudentByDni(dni);
            if (myStudent==null){
                throw new NullPointerException("El alumno no existe!!");
            }
        } catch (Exception e) {
            return Collections.singletonList("Error al consultar alumno!! " + e.getMessage());
        }

        Long idStudent = myStudent.getStudentId();

        List<BookReservation> list = new ArrayList<>();
        list = reservationRepository.findAllByFkStudent(idStudent);
        List<ListaDto> listaDtos = new ArrayList<>();
        for(BookReservation reservas: list){
            //Tengo que buscar el nombre del libro
            try{
                myBook = bookRepository.findById(reservas.getFkBook());
                if(myBook.isEmpty()){
                    throw new NullPointerException("El libro no existe!!");
                }
            } catch (Exception e) {
                return Collections.singletonList("Error al consultar libro!! " + e.getMessage());
            }

            ListaDto dto = ListaDto.builder()
                    .reservation(reservas.getReservationId())
                    .student(myStudent.getName())
                    .bookName(myBook.get().getTitle())
                    .startDate(reservas.getStartDate())
                    .endDate(reservas.getEndDate())
                    .build();
            listaDtos.add(dto);
        }
        return listaDtos;
    }

    @Override
    public List<?> ListarReservaByBook(String codeBook) {
        Book myBook = new Book();
        Student myStudent = new Student();
        myBook = bookRepository.findBookByCode(codeBook);
        List<BookReservation> libroPedidoList = (List<BookReservation>) reservationRepository.findReservaByFkBook(myBook.getBookId());
        List<ListaDto> listaDtos = new ArrayList<>();
        for(BookReservation reservas: libroPedidoList){
            try{
                myStudent = studentRepository.getReferenceById(reservas.getFkStudent());
                if(myStudent.getName()==null){
                    throw new NullPointerException("Error al consultar los datos de alumno!!");
                }
            } catch (Exception e) {
                return Collections.singletonList("Error al consultar los datos de alumno!! " + e.getMessage());
            }
            ListaDto dto = ListaDto.builder()
                    .reservation(reservas.getReservationId())
                    .student(myStudent.getName())
                    .bookName(myBook.getTitle())
                    .startDate(reservas.getStartDate())
                    .endDate(reservas.getEndDate())
                    .build();
            listaDtos.add(dto);
        }
        return listaDtos;
    }


    @Override
    public ResponseEntity<?> BookSave(Book book){
        Response res = new Response();
        try {
            bookRepository.save(book);
            res.response = "Libro guardado con exito";
            return new ResponseEntity<Response>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.response = "Error al guardar libro!!";
            return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*public String BookDelete(String code){
        try {
            Book myBook = new Book();
            myBook = bookRepository.findBookByCode(code);
            bookRepository.deleteById(myBook.getBookId());
            return "Libro eliminado con exito";
        } catch (Exception e) {
            return "Error al eliminar libro!!";
        }

    }*/

    public ResponseEntity<?> BookDelete(String code){
        Book myBook = new Book();
        Response res = new Response();
        try {
            myBook = bookRepository.findBookByCode(code);
            bookRepository.deleteById(myBook.getBookId());
            res.response = "Libro eliminado con exito";
            return new ResponseEntity<Response>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.response = "Error al eliminar libro!!";
            return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<?> ListBookAll() {
        return bookRepository.findAll();
    }

    public ResponseEntity<?> UpdateBook(Book book){
        Response res = new Response();
        try{
            Optional<Book> myBook = bookRepository.findById(book.getBookId());
            if(myBook.isEmpty()){
                res.response = "El libro no existe en base de datos.";
                return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (Exception e){
            res.response = "Error al actualizar registro";
            return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Book myBook = Book.builder()
                .bookId(book.getBookId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .code(book.getCode())
                .copies(book.getCopies())
                .build();

        try{
            bookRepository.save(myBook);
            res.response = "El registro fue actualizado con exito";
            return new ResponseEntity<Response>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.response = "Error de actualización";
            return new ResponseEntity<Response>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
