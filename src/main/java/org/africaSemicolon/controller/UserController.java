package org.africaSemicolon.controller;

import org.africaSemicolon.dto.request.RegisterRequest;
import org.africaSemicolon.dto.response.ApiResponse;
import org.africaSemicolon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register_user")
    public ResponseEntity<?>  registerUser(@RequestBody RegisterRequest request){
        try{
            var result = userService.registerUser(request);
            return new ResponseEntity<>(new ApiResponse(true,result), CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(new ApiResponse(false,e.getMessage()),BAD_REQUEST);
        }
    }
}
