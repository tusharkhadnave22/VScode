package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.Services.IReminderService;

public class RenewalReminderCommand implements ICommand {
    IReminderService reminderService;
    
    public RenewalReminderCommand(IReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @Override
    public void execute(List<String> tokens) {
        reminderService.printReminders();
        
    }
    
}
