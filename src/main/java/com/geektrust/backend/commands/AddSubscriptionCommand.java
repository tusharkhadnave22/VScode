package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.Services.ISubscriptionService;

public class AddSubscriptionCommand implements ICommand {

    ISubscriptionService subscriptionService;
    
    public AddSubscriptionCommand(ISubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void execute(List<String> tokens)
    {
       subscriptionService.addSubscription(tokens.get(1),tokens.get(2));
    }
    
    
}
