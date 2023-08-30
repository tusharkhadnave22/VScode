package com.geektrust.backend.Services;

import com.geektrust.backend.repositories.IRenewalReminderRepository;

public class ValidityPlanService implements IValidityPlanService {
    private final IRenewalReminderRepository renewalReminderRepository;

    public ValidityPlanService(IRenewalReminderRepository renewalReminderRepository) {
        this.renewalReminderRepository = renewalReminderRepository;
    }

    @Override
    public void totalPriceCalculation(String price) {
        int subscriptionPrice = Integer.parseInt(price);
        renewalReminderRepository.setTotalRenewalAmount(subscriptionPrice);

    }



}
