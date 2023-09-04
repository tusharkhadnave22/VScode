package com.geektrust.backend.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.geektrust.backend.exceptions.InvalidSubscirptionValidityException;
import com.geektrust.backend.exceptions.InvalidSubscriptionPlanException;
import com.geektrust.backend.repositories.AvailableSubscriptionPlansRepository;
import com.geektrust.backend.repositories.SubscriptionDataRepository;

public class SubscriptionService implements ISubscriptionService {
   private AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository;
   private SubscriptionDataRepository subscriptionDataRepository;
   private List<String> individualSubscriptionPlanData;
   private IReminderService reminderService;

   public SubscriptionService(SubscriptionDataRepository subscriptionDataRepository,
         AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository,
         IReminderService reminderService) {
      this.subscriptionDataRepository = subscriptionDataRepository;
      this.availableSubscriptionPlansRepository=availableSubscriptionPlansRepository;
      this.reminderService = reminderService;
   }
   public SubscriptionService(){

   }

   @Override
   public void addSubscription(String subscriptionType, String subscriptionValidity) throws InvalidSubscriptionPlanException {
   
      if(!doWeHaveThisSubscription(subscriptionType))
      {
         throw new InvalidSubscriptionPlanException("Cannot add Subscription type. Please select Subscripton type of VIDEO/MUSIC/POADCAST type");
      }
      if(!doWeHaveThisSubscriptionValidity(subscriptionValidity))
      {
         throw new InvalidSubscirptionValidityException("Cannot add Subscription validity type. Please select Subscription validity type of PERSONAL/PRIMIUM/FREE type");
      }
      
      List<String> individualSubscription =setIndividualSubscriptionPlanData(subscriptionType, subscriptionValidity);
      subscriptionDataRepository.setAllSubscriptionTypeAndValidityPlan(individualSubscription);
     
   }

   // public boolean doWeHaveThisSubscription(String subscriptionType)
   // {
   //    if(subscriptionType.equals("MUSIC") || subscriptionType.equals("PODCAST") || subscriptionType.equals("VIDEO"))
   //    {
   //       return true;
   //    }
   //    return false;
   // }
   public boolean doWeHaveThisSubscription(String subscriptionType) {
    Set<String> validSubscriptionTypes = new HashSet<>(Arrays.asList("MUSIC", "PODCAST", "VIDEO"));
    return validSubscriptionTypes.contains(subscriptionType);
}
   //  public boolean doWeHaveThisSubscriptionValidity(String subscriptionValidity)
   // {
   //    if(subscriptionValidity.equals("FREE") || subscriptionValidity.equals("PERSONAL") || subscriptionValidity.equals("PREMIUM"))
   //    {
   //       return true;
   //    }
   //    return false;
   // }
   public boolean doWeHaveThisSubscriptionValidity(String subscriptionValidity) {
      Set<String> validSubscriptionValidities = new HashSet<>(Arrays.asList("FREE", "PERSONAL", "PREMIUM"));
      return validSubscriptionValidities.contains(subscriptionValidity);
  }
   @Override
   public void startSubscription(String date) {

      String[] StartDate = date.split("-");

      int month = reminderService.toIntConverter(StartDate[1]);
      int day = reminderService.toIntConverter(StartDate[0]);
      int year = reminderService.toIntConverter(StartDate[2]);

     subscriptionDataRepository.setStartDate(day, month, year);

      if(dateValidator(day, month, year)==false){
         System.out.println("INVALID_DATE");  
      }
      
      
   }
   
   public boolean dateValidator(int day,int month,int year)
   {  
      
      if (yearValidator(year) == false || monthValidator(month) == false|| dayValidator(day, year, month) == false) 
      {
         return false;
      }
         return true;
   }


   public List<String> setIndividualSubscriptionPlanData(String subscriptionType,String subscriptionValidity) 
   {   
      individualSubscriptionPlanData = new ArrayList<>();
      individualSubscriptionPlanData.add(subscriptionType);
      individualSubscriptionPlanData.add(subscriptionValidity);
      
      if(subscriptionDataRepository.dateValidatorWithoutInput())
         {
         System.out.println("ADD_SUBSCRIPTION_FAILED INVALID_DATE");
         return new ArrayList<>();
         }
      
      for (List<String> subscription : subscriptionDataRepository.getAllSubscriptionTypeAndValidityPlan()) 
      {  
         if (subscription.get(0).equals(subscriptionType)) 
         {
            System.out.println("ADD_SUBSCRIPTION_FAILED DUPLICATE_CATEGORY");
            return new ArrayList<>();
         }
      }

      return individualSubscriptionPlanData;
   }



   // public boolean yearValidator(int year) {
   //    if (year < 0) {

   //       return false;
   //    } 
   //    else {
   //       return true;
   //    }

   // }
   public boolean yearValidator(int year) {
      return year >= 0;
  }
  
   // public boolean monthValidator(int month) {
   //    if (month > 12 || month < 1) {

   //       return false;
   //    } else {
   //       return true;
   //    }
   // }
   public boolean monthValidator(int month) {
      return month >= 1 && month <= 12;
  }
  

   public boolean dayValidator(int day, int year, int month) {
      if (day < 1) {
          return false;
      }
  
      switch (month) {
          case 4:
          case 6:
          case 9:
          case 11:
              return day <= 30;
          case 1:
          case 3:
          case 5:
          case 7:
          case 8:
          case 10:
          case 12:
              return day <= 31;
          case 2:
              return (checkIfLeapYear(year) && day <= 29) || (!checkIfLeapYear(year) && day <= 28);
          default:
              return false;
      }
  }
  
   public boolean checkIfLeapYear(int year){
      // Leap years are divisible by 4, except for years divisible by 100 but not by 400.
      return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
   }

}

