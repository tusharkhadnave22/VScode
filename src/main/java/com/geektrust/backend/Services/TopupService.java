package com.geektrust.backend.Services;

import com.geektrust.backend.repositories.AvailableSubscriptionPlansRepository;
import com.geektrust.backend.repositories.IRenewalReminderRepository;
import com.geektrust.backend.repositories.SubscriptionDataRepository;
import com.geektrust.backend.repositories.TopUpRepository;

public class TopupService implements ITopupService {
   
    private TopUpRepository topUpRespository;
    private AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository;
    private IRenewalReminderRepository renewalReminderRepository;
    private SubscriptionDataRepository subscriptionDataRepository;
    public TopupService(TopUpRepository topUpRespository,
            AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository,
            IRenewalReminderRepository renewalReminderRepository,SubscriptionDataRepository subscriptionDataRepository) {
        this.topUpRespository = topUpRespository;
        this.availableSubscriptionPlansRepository = availableSubscriptionPlansRepository;
        this.renewalReminderRepository = renewalReminderRepository;
        this.subscriptionDataRepository=subscriptionDataRepository;
       
    }

    public void addTopUpService(String TopUpType, String validity) {

        topUpRespository.setTopUpPlanDetails(TopUpType);
        topUpRespository.setTopUpPlanDetails(validity);
        
        if(subscriptionDataRepository.dateValidatorWithoutInput()){
            System.out.println("ADD_TOPUP_FAILED INVALID_DATE");
            return;
        }
        if (topUpRespository.getTopUpPlanDetails().size() > 2) {
            System.out.println("ADD_TOPUP_FAILED DUPLICATE_TOPUP");
            int indexToDelete=2;
            topUpRespository.removeTopUpEntry(indexToDelete);
            topUpRespository.removeTopUpEntry(indexToDelete);
            return;
        }
          
          

        calculateTopUpPrice();
    }

    @Override
    public void calculateTopUpPrice() {
        int validityRequiredInMonth =
                Integer.parseInt(topUpRespository.getTopUpPlanDetails().get(1));
        String validity_Type = topUpRespository.getTopUpPlanDetails().get(0);
        int price = availableSubscriptionPlansRepository.getTopupPricePerMonth().get(validity_Type);
        renewalReminderRepository.setTotalRenewalAmount(price * validityRequiredInMonth);

    }



}
