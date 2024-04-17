package com.GlamourByNora.api.util;

public enum ConstantMessages {
    SUCCESS("Successful", 1),
    FAILED("Failed", 2),
    CREATED("Created Successfully", 3),
    UNAUTHORIZED("Not Authorized", 4),
    NOT_FOUND("Cannot be found",5),
    UPDATED("Successfully Updated",6),
    EXIST("You already have an account",7),
    DELETED("Deleted", 8),
    FOUND("Found",9),
    INCORRECT("Username or Password Incorrect!",10),
    NOT_LOGGED_IN("User not logged in...",11),
    LOGGED_OUT("User Logged out",12),
    ADDED_TO_CART("Added to cart", 13),
    DELETED_FROM_CART("Deleted from cart", 14),
    PROCEED("proceed",15 ),
    INCORRECT_OLD_PASSWORD("Old Password Incorrect", 16),
    CHECK_INPUT("Input Mismatch", 17),
    INVALID_EMAIL("Email not valid", 18),
    OTP_IS_EXPIRED("OTP is expired", 19),
    OTP_INCORRECT("OTP not correct", 20),
    NEW_PASSWORD_SAVED("New password is saved! ", 21),
    NEW_OTP_SENT("New OTP has been sent", 22),
    USER_LOGGED_IN_SUCCESSFULLY("User logged in successfully", 23),
    PRODUCT_DOES_NOT_EXIST("Product does not exist", 24),
    TOTAL_VALUE("Total value is:", 25),
    PROCESSING("Processing", 26),
    PAID("Paid", 27),
    DELIVERED("Delivered", 28),
    CANCELLED("Cancelled", 29),
    TRANSACTION_SUCCESSFUL("Transaction Successful",30 ),
    INPUT_A_NEW_PASSWORD("Input a new password different from your old password", 31);

    private String message;
    private int status;

    ConstantMessages(String message, int status) {
        this.message=message;
        this.status=status;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ApiResponseMessage{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
