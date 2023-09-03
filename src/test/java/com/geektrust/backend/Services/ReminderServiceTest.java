package com.geektrust.backend.Services;

import com.geektrust.backend.repositories.AvailableSubscriptionPlansRepository;
import com.geektrust.backend.repositories.IRenewalReminderRepository;
import com.geektrust.backend.repositories.SubscriptionDataRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@DisplayName("ReminderServiceTest")
@ExtendWith(MockitoExtension.class)
public class ReminderServiceTest {
 
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
        reminderService = new ReminderService(subscriptionDataRepository, renewalReminderRepository, availableSubscriptionPlansRepository, topupService);
    }

    @Test
    public void testcalculateReminderDateForPersonalPlan(){
        //Arrange
        Map<String,Integer> date=new HashMap<>();
        date.put("day",14);
        date.put("month",02);
        date.put("year",2020);
        Map<String,Integer> validitymonth=new HashMap<>();
        validitymonth.put("FREE",1);
        validitymonth.put("PERSONAL",1);
        validitymonth.put("PREMIUM",3);
        when(availableSubscriptionPlansRepository.getValidity_month()).thenReturn(validitymonth);

        //Act
        String actualDate="04-03-2020";
        String dateExpected=reminderService.calculateReminderDate("FREE", date);
        
        //Assert
         assertEquals(actualDate, dateExpected);
    }

    @Test
    public void testcalculateReminderDateForPremiumPlan(){
         //Arrange
        Map<String,Integer> date=new HashMap<>();
        date.put("day",14);
        date.put("month",02);
        date.put("year",2020);
        Map<String,Integer> validitymonth=new HashMap<>();
        validitymonth.put("FREE",1);
        validitymonth.put("PERSONAL",1);
        validitymonth.put("PREMIUM",3);
        when(availableSubscriptionPlansRepository.getValidity_month()).thenReturn(validitymonth);
    
        //Act
        String actualDate="04-05-2020";
        String dateExpected=reminderService.calculateReminderDate("PREMIUM", date);
        
        //Assert
         assertEquals(actualDate, dateExpected);
    }

    @Test
    @DisplayName("isLeapYear should check the valid or invalid leap year")
    public void test2004isLeapYear(){
       Assertions.assertEquals(reminderService.isLeapYear(2004),true);
    }


    @Test
    @DisplayName("isLeapYear should check the valid or invalid leap year")
    public void test2003isLeapYear(){
       Assertions.assertEquals(reminderService.isLeapYear(2003),false);
    }

    @Test 
    @DisplayName("testDateFormater should return givern number in 01 format if 1 is passed")
    public void testdateFormaterEdgeCase(){
        Assertions.assertEquals(reminderService.dateFormater(1), "01");
    }

    @Test 
    @DisplayName("testDateFormater should return givern number in 10 format if 10 is passed")
    public void testdateFormater(){
        Assertions.assertEquals(reminderService.dateFormater(10), "10");
    }

    @Test
    @DisplayName("convertInDateFormat it should give us day month year in readable format")
    public void testConvertInDateFormat(){
        //arrange
        final int date=1;
        final int month=4;
        final int year=2004;

        //act
        String actualDate ="01-04-2004";
        String expectedDate=reminderService.convertInDateFormat(date, month, year);

        //assert
        Assertions.assertEquals(actualDate,expectedDate);
    }
















        @Test
    @DisplayName("calculateReminderDate it shuld give date 10 days before the startdate ")
    public void testcalculateReminderDateEdgeCasePremiumValidity2(){
           //Arrange
           Map<String,Integer> date=new HashMap<>();
           date.put("day",10);
           date.put("month",12);
           date.put("year",2019);
           Map<String,Integer> validitymonth=new HashMap<>();
           validitymonth.put("FREE",1);
           validitymonth.put("PERSONAL",1);
           validitymonth.put("PREMIUM",3);
           when(availableSubscriptionPlansRepository.getValidity_month()).thenReturn(validitymonth);
       
           //Act
           String actualDate="29-02-2020";
           String dateExpected=reminderService.calculateReminderDate("PREMIUM", date);
           
           //Assert
            assertEquals(actualDate, dateExpected);
        
    }











      @Test
    @DisplayName("calculateReminderDate it shuld give date 10 days before the startdate ")
    public void testcalculateReminderDateEdgeCasePremiumValidity3(){
           //Arrange
           Map<String,Integer> date=new HashMap<>();
           date.put("day",8);
           date.put("month",12);
           date.put("year",2019);
           Map<String,Integer> validitymonth=new HashMap<>();
           validitymonth.put("FREE",1);
           validitymonth.put("PERSONAL",1);
           validitymonth.put("PREMIUM",3);
           when(availableSubscriptionPlansRepository.getValidity_month()).thenReturn(validitymonth);
       
           //Act
           String actualDate="29-12-2019";
           String dateExpected=reminderService.calculateReminderDate("PERSONAL", date);
           
           //Assert
            assertEquals(actualDate, dateExpected);
        
    }





























    @Test
    @DisplayName("calculateReminderDate it shuld give date 10 days before the startdate")
    public void testcalculateReminderDate(){
         //Arrange
         Map<String,Integer> date=new HashMap<>();
         date.put("day",8);
         date.put("month",12);
         date.put("year",2019);
         Map<String,Integer> validitymonth=new HashMap<>();
         validitymonth.put("FREE",1);
         validitymonth.put("PERSONAL",1);
         validitymonth.put("PREMIUM",3);
         when(availableSubscriptionPlansRepository.getValidity_month()).thenReturn(validitymonth);
     
         //Act
         String actualDate="29-12-2019";
         String dateExpected=reminderService.calculateReminderDate("FREE", date);
         
         //Assert
          assertEquals(actualDate, dateExpected);
      
    }

    @Test
    @DisplayName("calculateReminderDate it shuld give date 10 days before the startdate edge case leap year in feb month")
    public void testcalculateReminderDateEdgeCase(){
          //Arrange
          Map<String,Integer> date=new HashMap<>();
          date.put("day",1);
          date.put("month",2);
          date.put("year",2004);
          Map<String,Integer> validitymonth=new HashMap<>();
          validitymonth.put("FREE",1);
          validitymonth.put("PERSONAL",1);
          validitymonth.put("PREMIUM",3);
          when(availableSubscriptionPlansRepository.getValidity_month()).thenReturn(validitymonth);
      
          //Act
          String actualDate="20-02-2004";
          String dateExpected=reminderService.calculateReminderDate("PERSONAL", date);
          
          //Assert
           assertEquals(actualDate, dateExpected);
    }

    @Test
    @DisplayName("calculateReminderDate it shuld give date 10 days before the startdate ")
    public void testcalculateReminderDateEdgeCasePremiumValidity(){
           //Arrange
           Map<String,Integer> date=new HashMap<>();
           date.put("day",1);
           date.put("month",2);
           date.put("year",2004);
           Map<String,Integer> validitymonth=new HashMap<>();
           validitymonth.put("FREE",1);
           validitymonth.put("PERSONAL",1);
           validitymonth.put("PREMIUM",3);
           when(availableSubscriptionPlansRepository.getValidity_month()).thenReturn(validitymonth);
       
           //Act
           String actualDate="20-02-2004";
           String dateExpected=reminderService.calculateReminderDate("PERSONAL", date);
           
           //Assert
            assertEquals(actualDate, dateExpected);
        
    }

    @Test
    @DisplayName("calculateReminderDate it shuld give date 10 days before the startdate in december month of the year startdate is")
    public void testcalculateReminderDateEdgeCaseEndOfYear(){
         //Arrange
         Map<String,Integer> date=new HashMap<>();
         date.put("day",1);
         date.put("month",12);
         date.put("year",2002);
         Map<String,Integer> validitymonth=new HashMap<>();
         validitymonth.put("FREE",1);
         validitymonth.put("PERSONAL",1);
         validitymonth.put("PREMIUM",3);
         when(availableSubscriptionPlansRepository.getValidity_month()).thenReturn(validitymonth);
     
         //Act
         String actualDate="19-02-2003";
         String dateExpected=reminderService.calculateReminderDate("PREMIUM", date);
         
         //Assert
          assertEquals(actualDate, dateExpected);
     
    }

     @Test
    @DisplayName("calculateReminderDate it shuld give date 10 days before the startdate in december month of the year startdate is")
    public void testcalculateReminderDateEdgeCaseEndOfYear2(){
         //Arrange
         Map<String,Integer> date=new HashMap<>();
         date.put("day",30);
         date.put("month",12);
         date.put("year",2019);
         Map<String,Integer> validitymonth=new HashMap<>();
         validitymonth.put("FREE",1);
         validitymonth.put("PERSONAL",1);
         validitymonth.put("PREMIUM",3);
         when(availableSubscriptionPlansRepository.getValidity_month()).thenReturn(validitymonth);
     
         //Act
         String actualDate="20-03-2020";
         String dateExpected=reminderService.calculateReminderDate("PREMIUM", date);
         
         //Assert
          assertEquals(actualDate, dateExpected);
    }

    @Test
    @DisplayName("Test if the string is converted in to int format or not")
    public void testToIntConverter(){
     //Arrange
     String number="10";
     //Act
     int actual=10;
     int expected=reminderService.toIntConverter(number);
     //Assert
     assertEquals(actual,expected);
    }

    @Test
    @DisplayName("Test if the int is converted in to string format or not")
    public void testToStringConverter(){
        //Arrange
        int number=10;
        //Act
        String actual="10";
        String expected=reminderService.toStringConverter(number);
        //Assert
        assertEquals(actual,expected);
     
    }
    
    @Test
    @DisplayName("test if the single digit passed to the method is it giving output in two digit format or not")
    public void testDateformater(){
        //Arrange
        int num=1;
        //act
       String actual="01";
       //arrange
       String expected=reminderService.dateFormater(num);
       //assert
       assertEquals(expected, actual);
    }
   
}
