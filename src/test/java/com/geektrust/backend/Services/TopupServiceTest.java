package com.geektrust.backend.Services;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import com.geektrust.backend.repositories.AvailableSubscriptionPlansRepository;
import com.geektrust.backend.repositories.IRenewalReminderRepository;
import com.geektrust.backend.repositories.TopUpRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


public class TopupServiceTest 
{
    private AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository;
    private TopUpRepository topUpRepository;
    private IRenewalReminderRepository renewalReminderRepository;
    private TopupService topupService;

    @BeforeEach
    public void dependencies(){
         availableSubscriptionPlansRepository=mock(AvailableSubscriptionPlansRepository.class);
        topUpRepository=mock(TopUpRepository.class);
        renewalReminderRepository=mock(IRenewalReminderRepository.class);
        topupService=new TopupService(topUpRepository, availableSubscriptionPlansRepository, renewalReminderRepository);
    }

    @Test
    public void testAddTopUpService() {
        when(topUpRepository.getTopUpPlanDetails()).thenReturn(new ArrayList<>());
        
        topupService.addTopUpService("FOUR_DEVICES", "1");

        verify(topUpRepository, times(2)).setTopUpPlanDetails(anyString());
    }

    @Test
    public void testCalculateTopUpPrice() {
        when(topUpRepository.getTopUpPlanDetails()).thenReturn(Arrays.asList("FOUR_DEVICES", "1"));

        when(availableSubscriptionPlansRepository.getTopupPricePerMonth()).thenReturn(
                Collections.singletonMap("FOUR_DEVICES", 50));

        topupService.calculateTopUpPrice();

        verify(renewalReminderRepository).setTotalRenewalAmount(50 * 1);
    }
}
