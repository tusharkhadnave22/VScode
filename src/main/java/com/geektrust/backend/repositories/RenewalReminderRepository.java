package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.List;

public class RenewalReminderRepository implements IRenewalReminderRepository {
   private List<String> renewalData;
    private int totalRenewalAmount;

    public RenewalReminderRepository() {
        this.renewalData =new ArrayList<>();
    }

    public List<String> getRenewalData() {
        return renewalData;
    }
    public void setRenewalData(String Data) {
        renewalData.add(Data);
    }
    public int getTotalRenewalAmount() {
        return totalRenewalAmount;
    }
    public void setTotalRenewalAmount(int totalRenewalAmount) {
        this.totalRenewalAmount = getTotalRenewalAmount()+totalRenewalAmount;
    }
    
}
