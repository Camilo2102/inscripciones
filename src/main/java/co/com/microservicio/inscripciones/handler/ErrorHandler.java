package co.com.microservicio.inscripciones.handler;


import co.com.microservicio.inscripciones.exeptions.HttpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

/**
 * Clase de manejo de excepciones global para manejar instancias de HttpException en toda la aplicación.
 *
 * @author Sebastian Garzon
 * @see HttpException
 * @see ExceptionHandler
 * @see ControllerAdvice
 * @since 1.3-SNAPSHOT
 */
@ControllerAdvice
public class ErrorHandler {

    /**
     * Maneja las excepciones del tipo HttpException y genera una respuesta con información detallada.
     *
     * @param ex Excepción de tipo HttpException.
     * @return ResponseEntity con un mapa que contiene el mensaje de error y el código de estado.
     * @since 1.0
     */
    @ExceptionHandler({HttpException.class})
    public ResponseEntity<Map<String, String>> handleException(HttpException ex) {
        Map<String, String> response = Map.of("error", ex.getMessage(), "status", "rejected");

        return new ResponseEntity<>(response, ex.getHttpCode());
    }
}
