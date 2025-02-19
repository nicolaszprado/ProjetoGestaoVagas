package br.com.nicolaszprado.projetogestaovagas.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Essa classe funciona como um "aconselhador" global de exceções
 * (ControllerAdvice). Quando uma determinada exceção ocorre em qualquer
 * Controller, podemos tratá-la aqui de forma centralizada.
 */
@ControllerAdvice
public class ExceptionHandlerController {

    // Objeto que nos permite resolver mensagens de validação (por exemplo,
    // buscar o texto em um arquivo messages.properties, incluindo suporte a i18n).
    private final MessageSource messageSource;

    /**
     * Construtor onde o Spring injeta o MessageSource configurado.
     *
     * @param message Fonte de mensagens para tradução/obtenção de textos de erro.
     */
    public ExceptionHandlerController(MessageSource message) {
        this.messageSource = message;
    }

    /**
     * Método que trata a exceção MethodArgumentNotValidException, que ocorre
     * quando a validação de argumentos de um método Controller falha
     * (geralmente através de anotações como @Valid, @NotNull, etc.).
     *
     * @param e A exceção que contém informações sobre quais campos falharam na validação.
     * @return Retorna uma lista de ErrorMessageDTO com as mensagens e campos que falharam,
     *         junto ao status HTTP 400 (BAD_REQUEST).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {

        // Lista para armazenar as mensagens de erro de cada campo inválido.
        List<ErrorMessageDTO> dto = new ArrayList<>();

        // Percorre todos os erros de campo encontrados na validação.
        for (FieldError err : e.getBindingResult().getFieldErrors()) {
            // Usa o MessageSource para obter a mensagem de erro correspondente ao campo,
            // levando em conta o locale atual (para fins de internacionalização).
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());

            // Cria um objeto de erro com o nome do campo e a mensagem traduzida.
            ErrorMessageDTO error = new ErrorMessageDTO(err.getField(), message);

            // Adiciona o erro à lista.
            dto.add(error);
        }

        // Retorna a lista de erros (dto) no corpo da resposta, com o status 400 (BAD_REQUEST).
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

}

