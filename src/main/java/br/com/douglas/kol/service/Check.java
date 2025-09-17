package br.com.douglas.kol.service;

import java.math.BigDecimal;

public class Check {

    public static void checker(String string, BigDecimal bigDecimal) throws RuntimeException {
        if (string.isBlank()) {
            throw new RuntimeException("Name cannot be blank");
        }
        if (bigDecimal.equals(new BigDecimal(0))) {
            throw new RuntimeException("Price cannot be zero");
        }
    }
}
