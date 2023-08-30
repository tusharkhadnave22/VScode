package com.geektrust.backend.Services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.geektrust.backend.repositories.AvailableSubscriptionPlansRepository;
import com.geektrust.backend.repositories.IRenewalReminderRepository;
import com.geektrust.backend.repositories.SubscriptionDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SubscriptionServiceTest {
   private ReminderService reminderService;
   private SubscriptionDataRepository subscriptionDataRepository;
   private IRenewalReminderRepository renewalReminderRepository;
   private AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository;
   private ITopupService topupService;

   @BeforeEach
   public void setUp() {
      subscriptionDataRepository = mock(SubscriptionDataRepository.class);
      renewalReminderRepository = mock(IRenewalReminderRepository.class);
      availableSubscriptionPlansRepository = mock(AvailableSubscriptionPlansRepository.class);
      topupService = mock(ITopupService.class);
      reminderService = new ReminderService(subscriptionDataRepository, renewalReminderRepository,
            availableSubscriptionPlansRepository, topupService);
   }


   @Test
   @DisplayName("year validator")
   public void testYearValidator() {
      // arragne
      final int year = 2003;
      // Act
      boolean actual = true;
      SubscriptionService obj = new SubscriptionService(subscriptionDataRepository,
            availableSubscriptionPlansRepository, reminderService);
      boolean expected = obj.yearValidator(year);
      // Assert
      Assertions.assertEquals(actual, expected);
   }

   @Test
   @DisplayName("Month validator")
   public void testMonthValidator() {
      // arragne
      final int month = 10;
      // Act
      boolean actual = true;
      SubscriptionService obj = new SubscriptionService(subscriptionDataRepository,
            availableSubscriptionPlansRepository, reminderService);
      boolean expected = obj.monthValidator(month);
      // Assert
      Assertions.assertEquals(actual, expected);

   }

   @Test
   @DisplayName("Month validator")
   public void testMonthValidatorEdgeCase() {
      // arragne
      final int month = 13;
      // Act
      boolean actual = false;
      SubscriptionService obj = new SubscriptionService(subscriptionDataRepository,
            availableSubscriptionPlansRepository, reminderService);
      boolean expected = obj.monthValidator(month);
      // Assert
      Assertions.assertEquals(actual, expected);
   }

   @Test
   @DisplayName("Day validator")
   public void testDayValidator() {
      // arragne
      final int day = 30;
      final int month = 2;
      final int year = 2004;
      // Act
      boolean actual = false;
      SubscriptionService obj = new SubscriptionService(subscriptionDataRepository,
            availableSubscriptionPlansRepository, reminderService);
      boolean expected = obj.dayValidator(day, year, month);
      // Assert
      Assertions.assertEquals(actual, expected);
   }

   @Test
   @DisplayName("Day validator check 29/03/2004 is valid")
   public void testDayValidatorEdgeCase() {
      // arragne
      final int day = 29;
      final int month = 2;
      final int year = 2004;
      // Act
      boolean actual = true;
      SubscriptionService obj = new SubscriptionService(subscriptionDataRepository,
            availableSubscriptionPlansRepository, reminderService);
      boolean expected = obj.dayValidator(day, year, month);
      // Assert
      Assertions.assertEquals(actual, expected);

   }

   @Test
   @DisplayName("Day validator check 31/03/2004 is valid")
   public void testDayValidatorEdgeCase2() {

      // arragne
      final int day = 31;
      final int month = 3;
      final int year = 2004;

      // Act
      boolean actual = true;
      SubscriptionService obj = new SubscriptionService(subscriptionDataRepository,
            availableSubscriptionPlansRepository, reminderService);
      boolean expected = obj.dayValidator(day, year, month);

      // Assert
      Assertions.assertEquals(actual, expected);
   }

   @Test
   @DisplayName("check if the inserted startdate is saved in the repo properly or not")
   public void testStartSubscription(){
      //Arrange
      String startDate="14-4-2005";
      Map<String,Integer> actualDate=new HashMap<>();
      actualDate.put("day",14);
      actualDate.put("month",4);
      actualDate.put("year",2005);
      //Act
      SubscriptionDataRepository obj2=new SubscriptionDataRepository();
      SubscriptionService obj=new SubscriptionService(obj2,availableSubscriptionPlansRepository,reminderService);
      obj.startSubscription(startDate);
      Map<String,Integer>expectedDate=obj2.getStartDate();
      boolean expected=Objects.equals(actualDate, expectedDate);
      //Assert
      assertTrue(expected);
   }


}
