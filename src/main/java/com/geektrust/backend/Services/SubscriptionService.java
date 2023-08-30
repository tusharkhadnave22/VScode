package com.geektrust.backend.Services;

import java.util.ArrayList;
import java.util.List;

import com.geektrust.backend.exceptions.InvalidSubscirptionValidityException;
import com.geektrust.backend.exceptions.InvalidSubscriptionPlanException;
import com.geektrust.backend.repositories.AvailableSubscriptionPlansRepository;
import com.geektrust.backend.repositories.SubscriptionDataRepository;

public class SubscriptionService implements ISubscriptionService {

   private SubscriptionDataRepository subscriptionDataRepository;
   private List<String> individualSubscriptionPlanData;
   private IReminderService reminderService;

   public SubscriptionService(SubscriptionDataRepository subscriptionDataRepository,
         AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository,
         IReminderService reminderService) {
      this.subscriptionDataRepository = subscriptionDataRepository;
      this.reminderService = reminderService;
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
      
      List<String> individualSubscription =
      setIndividualSubscriptionPlanData(subscriptionType, subscriptionValidity);
      subscriptionDataRepository.setAllSubscriptionTypeAndValidityPlan(individualSubscription);
     
   }

   public boolean doWeHaveThisSubscription(String subscriptionType)
   {
      if(subscriptionType.equals("MUSIC") || subscriptionType.equals("PODCAST") || subscriptionType.equals("VIDEO"))
      {
         return true;
      }
      return false;
   }
    public boolean doWeHaveThisSubscriptionValidity(String subscriptionValidity)
   {
      if(subscriptionValidity.equals("FREE") || subscriptionValidity.equals("PERSONAL") || subscriptionValidity.equals("PREMIUM"))
      {
         return true;
      }
      return false;
   }

   @Override
   public void startSubscription(String date) {

      String[] StartDate = date.split("-");

      int month = reminderService.toIntConverter(StartDate[1]);
      int day = reminderService.toIntConverter(StartDate[0]);
      int year = reminderService.toIntConverter(StartDate[2]);
      // yearValidator(year);
      // monthValidator(month);
      // dayValidator(day, year, month);
      if (yearValidator(year) == false || monthValidator(month) == false
            || dayValidator(day, year, month) == false) {
         System.out.println("ADD_SUBSCRIPTION_FAILD INVALID_DATE");
         System.exit(0);
      }

      subscriptionDataRepository.setStartDate(day, month, year);
      // availableSubscriptionPlansRepository.setPrices();
   }

   public List<String> setIndividualSubscriptionPlanData(String subscriptionType,
         String subscriptionValidity) {
      for (List<String> subscription : subscriptionDataRepository
            .getAllSubscriptionTypeAndValidityPlan()) {
         if (subscription.get(0).equals(subscriptionType)) {
            System.out.println("ADD_SUBSCRIPTION_FAILED DUPLICATE_CATEGOTY");
            System.exit(0);
         }
      }
      individualSubscriptionPlanData = new ArrayList<>();
      individualSubscriptionPlanData.add(subscriptionType);
      individualSubscriptionPlanData.add(subscriptionValidity);
      return individualSubscriptionPlanData;
   }



   public boolean yearValidator(int year) {
      if (year < 0) {

         return false;
      } else {
         return true;
      }

   }

   public boolean monthValidator(int month) {
      if (month > 12 || month < 1) {

         return false;
      } else {
         return true;
      }
   }

   public boolean dayValidator(int day, int year, int month) {
      if (day < 1) 
      {
         return false;
      } 
      else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10
            || month == 12) {
         if (day > 31) {

            return false;
         } else {
            return true;
         }
      } 
      else if (month == 4 || month == 6 || month == 9 || month == 11 || month == 12) {
         if (day > 30) {

            return false;
         } else {
            return true;
         }
      }

      else if (reminderService.isLeapYear(year) && month == 2) {
         if (day > 29) {

            return false;
         } else {
            return true;
         }
      } else if (reminderService.isLeapYear(year) == false && month == 2) {
         if (day > 28) {

            return false;
         } else {
            return true;
         }
      }
      return true;

   }



}
