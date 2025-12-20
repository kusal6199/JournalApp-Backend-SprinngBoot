package com.example.journalApp.services;

import com.example.journalApp.model.User;
import com.example.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Disabled
    @Test
    public void testFindUserByUserName(){
        assertNotNull(userRepository.findByUsername("admin"));
        assertNotNull(userRepository.findByUsername("adad"));
    }


    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "sunita",
            "gopal",
            "Vipul"
    })
    public void isJournalEmpty(String name){
        User user = userRepository.findByUsername(name);
        assertNotNull(user," user not found"+name);
        assertFalse(user.getJournalEntry().isEmpty(),"journal is empty for "+name);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "10,10,20",
            "40,40,20"
    })
    public void testResult(int a, int b, int expected){
        assertEquals(expected, a+b );
    }



}
