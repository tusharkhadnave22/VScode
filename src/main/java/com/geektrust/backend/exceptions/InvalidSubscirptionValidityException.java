package com.geektrust.backend.exceptions;

public class InvalidSubscirptionValidityException extends RuntimeException {
    public InvalidSubscirptionValidityException()
    {
     super();
    }
    public InvalidSubscirptionValidityException(String msg)
    {
     super(msg);
    }
}
