package org.sid.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor
public class RegisterForm {
private String username;
private String password;
private String repassword;
}
