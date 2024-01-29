package dev.luismiguelro.movies.exception;

import dev.luismiguelro.movies.exception.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // Corregir esta línea
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleGlobalException(Exception ex, Model model) {
        // Puedes personalizar el modelo y la vista para la página de error aquí
        model.addAttribute("errorMessage", ex.getMessage());
        return "error.html"; // Esto buscará un archivo llamado "error.html" en la carpeta "templates"
    }
}
