package com.yassir.budgetbuddy.cloud;
public class BucketNotFoundException extends RuntimeException {
    public BucketNotFoundException(String message) {
        super(message);
    }
}
