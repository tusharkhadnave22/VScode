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
   public void saveAllRenualReminder() {

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



   public int toIntConverter(String number) {
      return Integer.parseInt(number);
   }

   public String toStringConverter(int number) {
      return Integer.toString(number);
   }

   public String dateFormater(int number) {

      if (number / 10 == 0) {
         return "0" + toStringConverter(number);
      } else {
         return toStringConverter(number);
      }
   }

   public boolean isLeapYear(int year) {
      if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
         return true;
      } else {
         return false;
      }
   }

   public String convertInDateFormat(int date, int month, int year) {
      String reminderDate =
            dateFormater(date) + "-" + dateFormater(month) + "-" + toStringConverter(year);
      return reminderDate;
   }

   public boolean isMonthEven(int month){
      if(month%2==0)
      return true;
      return false;
   }

   @Override
   public String calculateReminderDate(String validityType, Map<String, Integer> startDate) {



      int month = startDate.get("month");
      int day = startDate.get("day");
      int year = startDate.get("year");

      Map<String, Integer> validityMonthRepo =
            availableSubscriptionPlansRepository.getValidity_month();
      int validityForYourPlan = validityMonthRepo.get(validityType);
      month = month + validityForYourPlan;

      if (month > 12) {
         year = year + 1;
         month = month - 12;
      }

      if (day == 31) {
         day = 30;
      } 


      //boolean isMonthNumberEven =isMonthEven(month);
      // if ((month - 1) % 2 == 0) {
      //     isMonthNumberEven = true;
      // }

      if (day < 10 && isMonthEven(month)) {
         month = month - 1;                   //ACTUALLY what is issue is when i am in last month 1 t0 10 date i am in hard situation 
                                              //because i am updating the year over here but again i have to come the particular year again
                                              //but rather than putting situatoin to the month range i can put if the month value is comming negative
         //if month is going below negative then i have to update the month as well as year
         if(month==0){
            month=12;
            year=year-1;
         }                                     
         if (month == 2 && isLeapYear(year)) {
            day = 29 - (10 - day);
         } else if (month == 2 && isLeapYear(year) == false) {
            day = 28 - (10 - day);
         } else {
            day = 30 - (10 - day);
         }
      } else if (day < 10 && isMonthEven(month)) {
         day = 31 - day;
         month = month - 1;
      }

      else {
         day = day - 10;
      }

      return convertInDateFormat(day, month, year);

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
      // featch the allsubscription data and calculate price
      for (List<String> subscriptoin : subscriptionDataRepository
            .getAllSubscriptionTypeAndValidityPlan()) {
         if (subscriptoin.get(0).equals("MUSIC")) {
            int MusicSubscriptionPrice =
                  availableSubscriptionPlansRepository.getMusicPriceMap().get(subscriptoin.get(1));
            renewalReminderRepository.setTotalRenewalAmount(MusicSubscriptionPrice);

         } else if (subscriptoin.get(0).equals("PODCAST")) {
            int PoadCastSubscriptionPrice = availableSubscriptionPlansRepository
                  .getPodCastPriceMap().get(subscriptoin.get(1));
            renewalReminderRepository.setTotalRenewalAmount(PoadCastSubscriptionPrice);

         } else if (subscriptoin.get(0).equals("VIDEO")) {
            int VideoSubscriptionPrice =
                  availableSubscriptionPlansRepository.getVideoPriceMap().get(subscriptoin.get(1));
            renewalReminderRepository.setTotalRenewalAmount(VideoSubscriptionPrice);

         } else {

         }
      }
      topupService.calculateTopUpPrice();

   }

}
