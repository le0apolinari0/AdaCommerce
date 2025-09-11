package br.com.AdaCommerce.execption;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}