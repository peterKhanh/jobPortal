package com.lifesupport.exception;

import java.util.Date;
import java.util.NoSuchElementException;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public String handleResourceNotFoundEror(HttpServletRequest request, ResourceNotFoundException ex) {
	
		ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                new Date());
			errorResponse.ShowErrors();
			return "error/500";
	}

	
	@ExceptionHandler(value = NumberFormatException.class)
	public String handleProductNotFoundError(HttpServletRequest request, NumberFormatException ex) {
		
		ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                //"Sai du lie ddaasy",
                new Date());
			errorResponse.ShowErrors();
			return "error/500";
	}

	@ExceptionHandler(value = NoSuchElementException.class)
	public String handleObjectNotFoundError(HttpServletRequest request, NoSuchElementException ex) {
		ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
               // "khong thay ban ghi",
                new Date());
              
			errorResponse.ShowErrors();
			return "error/500";
	}
	
    @ExceptionHandler(value = NullPointerException.class)
    public String nullPointerHandler(NullPointerException ex) {
    		ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.FORBIDDEN.value(),
                    ex.getMessage(),
                    new Date());
    			errorResponse.ShowErrors();
    			return "error/500";
    }
//     
//    @ExceptionHandler(value = Exception.class)
//    public String AnyOtherHandler(Exception ex) {
//  		ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                "Sai du lie",
//                System.currentTimeMillis());
//			errorResponse.ShowErrors();
//			return "error/500";
//
//    }
//     
	
	
}
