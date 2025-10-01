package br.com.douglas.kol.service;

import java.math.BigDecimal;

public class Check {

    public static void checker(String string, BigDecimal bigDecimal) throws IllegalArgumentException {
        if (string.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (bigDecimal.equals(new BigDecimal(0))) {
            throw new IllegalArgumentException("Price cannot be zero");
        }
    }


    public static void checker(String string, BigDecimal bigDecimal, Integer quantidade) throws IllegalArgumentException {
        if (string.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (bigDecimal.equals(new BigDecimal(0))) {
            throw new IllegalArgumentException("Price cannot be zero");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantity cannot be zero");
        }
    }


}
