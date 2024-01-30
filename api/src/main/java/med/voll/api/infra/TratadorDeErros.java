package med.voll.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErros404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {

        List<DadosErrosValidacao> dadosErros = ex.getFieldErrors().stream()
                .map(DadosErrosValidacao::new)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(dadosErros);
    }
    private record DadosErrosValidacao(String campo, String mensagem) {
        public DadosErrosValidacao(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}



