package com.geektrust.backend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("App Test")
class GeektrustTest {

    @Test
    public void Application_Test() throws Exception{
        
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("create method Should Throw UserNotFoundException If No Creator User Found")
    public void testRun(){
        
    }
}
