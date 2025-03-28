package com.pdfconverter.exceptions;

public class UnsuccessfulConversionException extends Exception {
    public UnsuccessfulConversionException() {
        super();
    }

    public UnsuccessfulConversionException(String message) {
        super(message);
    }
}
