package com.geektrust.backend.exceptions;

public class InvalidSubscriptionPlanException extends RuntimeException{
    public InvalidSubscriptionPlanException()
    {
     super();
    }
    public InvalidSubscriptionPlanException(String msg)
    {
     super(msg);
    }
}