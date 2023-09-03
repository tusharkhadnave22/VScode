package com.geektrust.backend.appConfig;

import com.geektrust.backend.Services.IReminderService;
import com.geektrust.backend.Services.ISubscriptionService;
import com.geektrust.backend.Services.ITopupService;
import com.geektrust.backend.Services.IValidityPlanService;
import com.geektrust.backend.Services.ReminderService;
import com.geektrust.backend.Services.SubscriptionService;
import com.geektrust.backend.Services.TopupService;
import com.geektrust.backend.Services.ValidityPlanService;
import com.geektrust.backend.commands.AddSubscriptionCommand;
import com.geektrust.backend.commands.AddTopupCommand;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.commands.RenewalReminderCommand;
import com.geektrust.backend.commands.StartSubscriptionCommand;
import com.geektrust.backend.repositories.AvailableSubscriptionPlansRepository;
import com.geektrust.backend.repositories.IRenewalReminderRepository;
import com.geektrust.backend.repositories.RenewalReminderRepository;
import com.geektrust.backend.repositories.SubscriptionDataRepository;
import com.geektrust.backend.repositories.TopUpRepository;

public class applicatoinConfig {
    private final IRenewalReminderRepository renewalReminderRepository = new RenewalReminderRepository();
    private final SubscriptionDataRepository subscriptionDataRepository = new SubscriptionDataRepository();
    private final TopUpRepository topUpRepository=new TopUpRepository();
    private final AvailableSubscriptionPlansRepository availableSubscriptionPlansRepository=new AvailableSubscriptionPlansRepository();
    
    private final ITopupService topupService = new TopupService(topUpRepository,availableSubscriptionPlansRepository,renewalReminderRepository,subscriptionDataRepository);

    private final IReminderService reminderService = new ReminderService(subscriptionDataRepository,renewalReminderRepository,availableSubscriptionPlansRepository,topupService);
    private final ISubscriptionService subscriptionService = new SubscriptionService(subscriptionDataRepository,availableSubscriptionPlansRepository,reminderService);
    private final IValidityPlanService validityPlanService = new ValidityPlanService(renewalReminderRepository);

    private final StartSubscriptionCommand startSubscriptionCommand = new StartSubscriptionCommand(subscriptionService);
    private final AddSubscriptionCommand addSubscriptionCommand = new AddSubscriptionCommand(subscriptionService);
    private final AddTopupCommand addTopupCommand = new AddTopupCommand(topupService);
    private final RenewalReminderCommand renewalReminderCommand = new RenewalReminderCommand(reminderService);
    //this class contains the storage for this commands in register method
    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker(){
        commandInvoker.register("START_SUBSCRIPTION",startSubscriptionCommand);
        commandInvoker.register("ADD_SUBSCRIPTION",addSubscriptionCommand);
        commandInvoker.register("ADD_TOPUP",addTopupCommand);
        commandInvoker.register("PRINT_RENEWAL_DETAILS",renewalReminderCommand);
        return commandInvoker;
    }

    
}




