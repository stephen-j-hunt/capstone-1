package com.techelevator;

import java.math.BigDecimal;

public class Log {


    private String dateTime;
    private String transactionType;
    private BigDecimal firstAmount;
    private BigDecimal secondAmount;


    public void Log(String dateTime, String transactionType, BigDecimal firstAmount, BigDecimal secondAmount) {
        this.dateTime = dateTime;
        this.transactionType = transactionType;
        this.firstAmount = firstAmount;
        this.secondAmount = secondAmount;
    }

}
