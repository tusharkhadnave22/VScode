package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionDataRepository{
     
     private List<List<String>> allSubscriptionTypeAndValidityPlan;
     private Map<String, Integer> startDate;
   

     public SubscriptionDataRepository() {
          this.allSubscriptionTypeAndValidityPlan =new ArrayList<>();
          this.startDate=new HashMap<>();
     }
   
     public List<List<String>> getAllSubscriptionTypeAndValidityPlan() {
          return allSubscriptionTypeAndValidityPlan;
     }

     public void setAllSubscriptionTypeAndValidityPlan(List<String> individualSubscriptionAndValidity) {
        allSubscriptionTypeAndValidityPlan.add(individualSubscriptionAndValidity);
     }

     public Map<String,Integer> getStartDate() {
          return startDate;
     }
     public void setStartDate(int day, int month, int year) {
         startDate.put("day",day);
         startDate.put("month",month);
         startDate.put("year",year);
     }
}