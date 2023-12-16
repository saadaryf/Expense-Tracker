package com.managers.expensetracker.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
    private String error;
    private String details;

    public ErrorResponse(String error, String details) {
        this.error = error;
        this.details = details;
    }
}
