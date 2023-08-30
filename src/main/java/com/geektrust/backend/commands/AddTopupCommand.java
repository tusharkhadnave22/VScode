package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.Services.ITopupService;

public class AddTopupCommand implements ICommand{
    ITopupService topupService;
    
    public AddTopupCommand(ITopupService topupService) {
        this.topupService = topupService;
    }

    @Override
    public void execute(List<String> tokens) {
        topupService.addTopUpService(tokens.get(1), tokens.get(2));
        
    }
    
}
