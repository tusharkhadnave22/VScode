package com.geektrust.backend.Services;

import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import com.geektrust.backend.repositories.IRenewalReminderRepository;

public class ValidityPlanServiceTest {

    
    private IRenewalReminderRepository renewalReminderRepository;

    private ValidityPlanService validityPlanService;

    @BeforeEach
    public void setUp() {
        renewalReminderRepository=mock(IRenewalReminderRepository.class);
        validityPlanService = new ValidityPlanService(renewalReminderRepository);
    }

   @org.junit.jupiter.api.Test
    public void testTotalPriceCalculation() {
        // Arrange
        String price = "100"; 
        // Act
        validityPlanService.totalPriceCalculation(price);

        // Assert
        verify(renewalReminderRepository).setTotalRenewalAmount(100);
    }
}
