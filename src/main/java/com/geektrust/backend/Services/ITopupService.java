package com.geektrust.backend.Services;

public interface ITopupService {
    void addTopUpService(String TopUpType, String validity);
    void calculateTopUpPrice();
}
