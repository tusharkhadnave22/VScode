package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.Services.ISubscriptionService;

public class StartSubscriptionCommand implements ICommand {
    ISubscriptionService subscriptionService;
    
    public StartSubscriptionCommand(ISubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void execute(List<String> tokens) {
       subscriptionService.startSubscription(tokens.get(1));
    }
    
}
