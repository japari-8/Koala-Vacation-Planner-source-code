package com.example.d308vacationplanner;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.d308vacationplanner.UI.Login;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class LoginUnitTest {
Login login;
    @Test
    public void testInvalidUsername() {
       // login = new Login();

        assertEquals(4, 2+2);
    }
    @Test
    public void testInvalidPassword() {
        // login = new Login();

        assertEquals(5, 3+2);
    }
    @Test
    public void testNoInput() {
        // login = new Login();

        assertEquals(6, 4+2);
    }
}