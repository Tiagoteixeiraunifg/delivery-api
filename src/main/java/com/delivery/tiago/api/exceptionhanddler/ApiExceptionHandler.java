package com.delivery.tiago.api.exceptionhanddler;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.delivery.tiago.domain.exception.EntidadeNaoEncontradaException;
import com.delivery.tiago.domain.exception.NegocioException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private MessageSource messageSource;
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ApiErros.Fields> fields = new ArrayList<>();
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String fieldName = ((FieldError) error).getField();
			String fieldMsg = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			fields.add(new ApiErros.Fields( fieldName, fieldMsg));
		}
		
		ApiErros err = new ApiErros();
		err.setStatus(status.value());
		err.setDataHora(LocalDateTime.now());
		err.setTitulo("Um ou mmais campos precisam ser preenchidos corretamente, tente novamente");
		err.setField(fields);

		// TODO Auto-generated method stub
		return handleExceptionInternal(ex, err, headers, status, request);
	}
	
	//para uso com o NegocioException de forma mais limpa.
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request){
		HttpStatus status =  HttpStatus.BAD_REQUEST;
		ApiErros err = new ApiErros();
		err.setStatus(status.value());
		err.setDataHora(LocalDateTime.now());
		err.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, err, new HttpHeaders(), status, request);
	}
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleNegocio(EntidadeNaoEncontradaException ex, WebRequest request){
		HttpStatus status =  HttpStatus.NOT_FOUND;
		ApiErros err = new ApiErros();
		err.setStatus(status.value());
		err.setDataHora(LocalDateTime.now());
		err.setTitulo(ex.getMessage());
		
		return handleExceptionInternal(ex, err, new HttpHeaders(), status, request);
	}
	
}
