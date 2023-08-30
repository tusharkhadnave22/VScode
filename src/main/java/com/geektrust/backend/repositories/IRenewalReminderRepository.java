package com.geektrust.backend.repositories;

import java.util.List;

public interface IRenewalReminderRepository {
    public void setTotalRenewalAmount(int totalRenewalAmount);
    public void setRenewalData(String renewalData);
    public List<String> getRenewalData();
    public int getTotalRenewalAmount();

    }
