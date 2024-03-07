package com.GlamourByNora.api.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class OrderReference {
    public String generateOrderReference(){
        return generateRandomPrefix() + "-" + generateOrderId();
    }
    private static String generateRandomPrefix() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            prefix.append(characters.charAt(random.nextInt(characters.length())));
        }
        return prefix.toString();
    }
    private static String generateOrderId() {
        String randomNumber = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
        return randomNumber;
    }
}