package com.geektrust.backend.Services;

import java.util.List;
import java.util.Map;
import com.geektrust.backend.repositories.AvailableSubscriptionPlansRepository;
import com.geektrust.backend.repositories.IRenewalReminderRepository;
import com.geektrust.backend.repositories.SubscriptionDataRepository;

public class ReminderService implements IReminderService 
{
   private SubscriptionDataRepository subscriptionDataRepository;
   private IRenewalReminderRepository renewalReminderRepository;
   private AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository;
   private ITopupService topupService;

   public ReminderService(SubscriptionDataRepository subscriptionDataRepository,
      IRenewalReminderRepository renewalReminderRepository,
      AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository,
      ITopupService topupService) {
      this.subscriptionDataRepository = subscriptionDataRepository;
      this.renewalReminderRepository = renewalReminderRepository;
      this.availableSubscriptionPlansRepository = availableSubscriptionPlansRepository;
      this.topupService = topupService;
   }

   public ReminderService(SubscriptionDataRepository subscriptionDataRepository) {
      this.subscriptionDataRepository = subscriptionDataRepository;
   }

   @Override
   public void saveAllRenualReminder() 
   {
      try{
      for (List<String> subscription : subscriptionDataRepository
            .getAllSubscriptionTypeAndValidityPlan()) {
         String subscriptionType = subscription.get(0);// name of subscrion
         String validityType = subscription.get(1);// validity type
         Map<String, Integer> startDate = subscriptionDataRepository.getStartDate();
         String reminderDate = calculateReminderDate(validityType, startDate);
         String renewalStatement = "RENEWAL_REMINDER" + " " + subscriptionType + " " + reminderDate;
         renewalReminderRepository.setRenewalData(renewalStatement);
      }
   }
   catch(IndexOutOfBoundsException e){}
   }

   public int toIntConverter(String number) {
      return Integer.parseInt(number);
   }

   public String toStringConverter(int number) {
      return Integer.toString(number);
   }

   public String dateFormater(int number) 
   {

         final int TEN = 10;

         if (number / TEN == 0) 
         {
            return "0" + toStringConverter(number);
         } 
         else 
         {
            return toStringConverter(number);
         }
   }

   public boolean isLeapYear(int year) {
     
         //  it's divisible by 4 ?
         boolean isDivisibleBy4 = (year % 4 == 0);
 
         //  if it's divisible by 100 ?
         boolean isDivisibleBy100 = (year % 100 == 0);
 
         //  if it's divisible by 400 ?
         boolean isDivisibleBy400 = (year % 400 == 0);
 
         // Determine if it's a leap year  ?
         return isDivisibleBy4 && (!isDivisibleBy100 || isDivisibleBy400);
     
   }

   public String convertInDateFormat(int date, int month, int year) 
   {
      return dateFormater(date) + "-" + dateFormater(month) + "-" + toStringConverter(year);
   }

   public boolean isMonthEven(int month)
   {
      final int EVEN_MONTH = 2; // Replace with the appropriate even month constant
      return month % EVEN_MONTH == 0;
   }


   

   @Override
public String calculateReminderDate(String validityType, Map<String, Integer> startDate) {
     int MONTH = startDate.get("month");
     int DAY = startDate.get("day");
     int YEAR = startDate.get("year");
    
    final boolean LAST_DAY_OF_MONTH = isLastDayOfMonth(DAY, MONTH, YEAR);
    
    Map<String, Integer> validityMonthRepo = availableSubscriptionPlansRepository.getValidity_month();
    int validityForYourPlan = validityMonthRepo.get(validityType);
    int updatedMonth = MONTH + validityForYourPlan;
    
    if (updatedMonth > 12) {
        YEAR += 1;
        updatedMonth -= 12;
    }
    
    if (LAST_DAY_OF_MONTH) {
        DAY = lastDaySenderForGivenMonth(DAY, updatedMonth, YEAR);
    }
    
    final int REMINDER_DAY_THRESHOLD = 10;
    
    if (DAY > REMINDER_DAY_THRESHOLD) {
        DAY -= REMINDER_DAY_THRESHOLD;
    } else {
        final int ONE_MONTH_BEFORE = 1;
        updatedMonth -= ONE_MONTH_BEFORE;
        
        if (updatedMonth == 0) {
            YEAR -= 1;
            updatedMonth = 12;
        }
        
        final int REMINDER_DAY = 10;
        DAY = lastDaySenderForGivenMonth(DAY, updatedMonth, YEAR) - (REMINDER_DAY - DAY);
    }
    
    return convertInDateFormat(DAY, updatedMonth, YEAR);
}




   public boolean isLastDayOfMonth(int day,int month,int year){
    if(day==30 && (month==4 ||month==6 ||month==9 ||month==11 )){
      return true;
    }
    if(day==31 && (month==1 ||month==3 ||month==5 ||month==7 ||month==8 ||month==12 )){
      return true;
    }
    if(month==2){
      if(day==28 && isLeapYear(year)==false){
         return true;
      }
      if(day==29 && isLeapYear(year)){
         return true;
      }
    }

    return false;
   }

   public int lastDaySenderForGivenMonth(int day,int month,int year){
      if(month==4 ||month==6 ||month==9 ||month==11){
         return 30;
      }
      if(month==1 ||month==3 ||month==5 ||month==7 ||month==8 ||month==12){
         return 31;
      }
      if(month==2 && isLeapYear(year)==false){
         return 28;
      }
      if(month==2 && isLeapYear(year)){
         return 29;
      }

     return 0;
   }


   

   @Override
   public void printReminders() {
      saveAllRenualReminder();
      if (renewalReminderRepository.getRenewalData().size() == 0) {
         System.out.println("SUBSCRIPTIONS_NOT_FOUND");
         System.exit(0);
      }

      for (String reminder : renewalReminderRepository.getRenewalData()) {
         System.out.println(reminder);
      }
      renewalAmount();

      String finalAmount = Integer.toString(renewalReminderRepository.getTotalRenewalAmount());
      System.out.println("RENEWAL_AMOUNT " + finalAmount);

   }



   @Override
   public void renewalAmount() {
      
      for (List<String> subscriptoin : subscriptionDataRepository.getAllSubscriptionTypeAndValidityPlan()) {
         try{
         if (subscriptoin.get(0).equals("MUSIC")) 
         {
            int musicSubscriptionPrice = availableSubscriptionPlansRepository.getMusicPriceMap().get(subscriptoin.get(1));
            renewalReminderRepository.setTotalRenewalAmount(musicSubscriptionPrice);
         } 
         else if (subscriptoin.get(0).equals("PODCAST")) 
         {
            int poadCastSubscriptionPrice = availableSubscriptionPlansRepository.getPodCastPriceMap().get(subscriptoin.get(1));
            renewalReminderRepository.setTotalRenewalAmount(poadCastSubscriptionPrice);
         } 
         else if (subscriptoin.get(0).equals("VIDEO")) 
         {
            int videoSubscriptionPrice = availableSubscriptionPlansRepository.getVideoPriceMap().get(subscriptoin.get(1));
            renewalReminderRepository.setTotalRenewalAmount(videoSubscriptionPrice);
         } 
         else 
         {

         }
      }
      catch(IndexOutOfBoundsException e){
         
      }
      }
   }

}
