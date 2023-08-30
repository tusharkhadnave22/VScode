package com.geektrust.backend.Services;

import java.util.Map;

public interface IReminderService {
     void saveAllRenualReminder();
     String calculateReminderDate(String validityType,Map<String,Integer> startDate);
     void renewalAmount();  
     void printReminders(); 
     boolean isLeapYear(int year);   
     int  toIntConverter(String number);
}
