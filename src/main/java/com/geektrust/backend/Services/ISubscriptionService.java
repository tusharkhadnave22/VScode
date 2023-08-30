package com.geektrust.backend.Services;

public interface ISubscriptionService {
     void startSubscription(String date);
 
       void addSubscription(String subscriptionType,String subscriptionValidity);
}
