package com.techelevator;

import java.math.BigDecimal;

public class MoneyManagement {  //cash box in the machine
    private BigDecimal balance = BigDecimal.ZERO;


    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
        return amount;
    }

    public void debit(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);

    }

    public void EndTransaction() {
        BigDecimal quarter = new BigDecimal("0.25");
        BigDecimal dime = new BigDecimal("0.10");
        BigDecimal nickel = new BigDecimal(".05");
        BigDecimal[] quartersReturned = balance.divideAndRemainder(quarter);
        balance = balance.remainder(quarter);
        BigDecimal[] dimesReturned = balance.divideAndRemainder(dime);
        balance = balance.remainder(dime);
        BigDecimal[] nickelsReturned = balance.divideAndRemainder(nickel);
        balance = balance.remainder(nickel);
        System.out.printf("You got %s quarters, %s dimes, %s nickels as change\n", quartersReturned[0], dimesReturned[0], nickelsReturned[0]);

        //divideAndRemainder returns a two-element array BD[0] = quotient, BD[1] = remainder.
    }

    //  BigDecimal[] quartersReturned = new BigDecimal[3];
    //  BigDecimal[] dimesReturned = new BigDecimal[2];
    //  BigDecimal[] nickelsReturned = new BigDecimal[1];

}



