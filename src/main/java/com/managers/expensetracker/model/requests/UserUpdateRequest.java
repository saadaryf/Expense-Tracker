package com.managers.expensetracker.model.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "Please Enter Your Name")
    private String name;

    @NotBlank(message = "username must be entered!")
    @Size(min = 4,max = 16,message = "username length must be between 4 and 16")
    private String username;
}
