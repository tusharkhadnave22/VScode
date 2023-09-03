package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.List;

public class TopUpRepository {
    private List<String> topUpPlanDetails;
    
    public TopUpRepository() {
        this.topUpPlanDetails =new ArrayList<>();
    }

    public List<String> getTopUpPlanDetails() {
        return topUpPlanDetails;
    }

    public void setTopUpPlanDetails(String TopUpDetails) {
        topUpPlanDetails.add(TopUpDetails);
    }
    public void removeTopUpEntry(int index){
        topUpPlanDetails.remove(index);
    }

    
    
}
