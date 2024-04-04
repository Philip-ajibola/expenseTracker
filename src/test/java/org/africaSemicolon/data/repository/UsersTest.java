package org.africaSemicolon.data.repository;

import org.africaSemicolon.data.model.User;
import org.africaSemicolon.dto.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UsersTest {
    @Autowired
    private Users users;
    @Test
    public void testThatUserAreSaved(){
        User user = new User();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setLastName("lastname");
        registerRequest.setFirstName("firstname");
        user.setUsername(registerRequest.getUsername());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        users.save(user);
        assertEquals(1,users.count());
    }

}