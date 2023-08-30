package com.geektrust.backend.Services;

import com.geektrust.backend.repositories.AvailableSubscriptionPlansRepository;
import com.geektrust.backend.repositories.IRenewalReminderRepository;
import com.geektrust.backend.repositories.TopUpRepository;

public class TopupService implements ITopupService {

    private TopUpRepository topUpRespository;
    private AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository;
    private IRenewalReminderRepository renewalReminderRepository;

    public TopupService(TopUpRepository topUpRespository,
            AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository,
            IRenewalReminderRepository renewalReminderRepository) {
        this.topUpRespository = topUpRespository;
        this.availableSubscriptionPlansRepository = availableSubscriptionPlansRepository;
        this.renewalReminderRepository = renewalReminderRepository;
    }

    public void addTopUpService(String TopUpType, String validity) {

        topUpRespository.setTopUpPlanDetails(TopUpType);
        topUpRespository.setTopUpPlanDetails(validity);
        if (topUpRespository.getTopUpPlanDetails().size() > 2) {
            System.out.println("ADD_TOPUP_FAILD DUPLICATE_TOPUP");
            System.exit(0);
        }
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
