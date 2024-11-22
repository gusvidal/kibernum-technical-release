package SpringSecurityJwt.library.controller;

import SpringSecurityJwt.library.model.Book;
import SpringSecurityJwt.library.service.IReservaService;
import SpringSecurityJwt.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/")
@Tag(name = "Book", description = "Endpoints para mantención de libros")
public class BookController {

    @Autowired
    private IReservaService iReservaService;

    @PostMapping("save")
    public ResponseEntity<?> saveBook(@RequestBody Book book) {
        return iReservaService.BookSave(book);
    }

    @DeleteMapping("delete/{code}")
    public ResponseEntity<?> listadoPorDni(@PathVariable String code){
        return iReservaService.BookDelete(code);
    }

    @GetMapping("list")
    public List<?> getBookList() {
        Response res = new Response();
        try{
            return iReservaService.ListBookAll();
        }catch (Exception e) {
            res.response = "No existen libros prestados.";
            return (List<?>) new ResponseEntity<Response>(res, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
        return iReservaService.UpdateBook(book);
    }
}
