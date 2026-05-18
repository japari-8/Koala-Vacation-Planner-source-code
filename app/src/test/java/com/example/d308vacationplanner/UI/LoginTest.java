package com.example.d308vacationplanner.UI;

import junit.framework.TestCase;

public class LoginTest extends TestCase {
    Login login;

    public void testCorrectCredentials() {
        login = new Login();
        assertTrue(login.validateUser("user", "Welcome"));
    }


    public void testInvalidCredentials() {
        login = new Login();
        assertFalse(login.validateUser("incorrect", "wrong"));
    }
}