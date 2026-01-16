package com.pluralsight.data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private LocalDate transactionDate;
    private LocalTime transactionTime;
    private String description;
    private String vendor;
    private double amount;

    public Transaction() {
        this.transactionDate = null;
        this.transactionTime = null;
        this.description = "";
        this.vendor = "";
        this.amount = 0;
    }


    public Transaction(LocalDate transactionDate, LocalTime transactionTime, String description, String vendor, double amount) {
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public String toCsv() {
        return this.transactionDate.format(dateFormatter) + "|" +
                this.transactionTime.format(timeFormatter) + "|" +
                this.description + "|" +
                this.vendor + "|" +
                this.amount;
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = transactionTime.format(formatter2);
        return String.format("%-12s  %-12s %-27s  %-20s  %.2f",
                transactionDate,
                formattedTime,
                description,
                vendor,
                amount
        );
    }
    public void add(Transaction transaction) {
    }
}

