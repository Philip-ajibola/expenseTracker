package org.africaSemicolon.dto.request;

import lombok.Data;

@Data
public class UserLogoutRequest {
    private String username;
    private String password;
}
