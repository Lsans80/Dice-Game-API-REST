package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.f2.S05T02N01F2SansMollLuisa.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PlayerNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handlePlayerNotFound (PlayerNotFound e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GameNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleGameNotFound (GameNotFound e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlayerAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> handlePlayerAlredyExists (PlayerAlreadyExists e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>("Runtime error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
