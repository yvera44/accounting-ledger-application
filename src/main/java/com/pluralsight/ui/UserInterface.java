package com.pluralsight.ui;

import com.pluralsight.data.Transaction;

import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    // Display main menu
    public void displayMainMenu() {
        System.out.println("""
                ======= Main Menu =======
                (D) Add Deposit
                (P) Make Payment (Debit)
                (L) Display Ledger Screen
                (X) Exit""");
    }

    // Display ledger menu
    public void displayLedgerMenu() {
        System.out.println("""
                ========= Ledger =========
                (A) Display All Entries
                (D) Display deposits
                (P) Display payments
                (R) Reports
                (H) Home Page""");
    }

    // Display reports menu
    public void displayReportsMenu() {
        System.out.println("""
                ============== Reports ==============
                (1) Display by Month to Date
                (2) Display Previous Month
                (3) Display Year to Date
                (4) Display Previous Year
                (5) Display by Vendor
                (0) Return to Ledger
                (H) Exit to Home Screen""");
    }

    // Get user input with prompt
    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim().toUpperCase();
    }

    // Get string input without uppercase conversion
    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Get double input
    public double getDoubleInput(String prompt) {
        System.out.print(prompt);
        double value = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return value;
    }

    // Display success messages
    public void displayDepositSuccess() {
        System.out.println("\nDeposit made. Thank you!");
    }

    public void displayPaymentSuccess() {
        System.out.println("\nPayment received. Thank you!");
    }

    // Display error messages
    public void displayError(String message) {
        System.out.println("ERROR: " + message);
    }

    public void displayInvalidChoice() {
        System.out.println("Invalid choice. Please try again.");
    }

    // Display navigation messages
    public void displayReturningToLedger() {
        System.out.println("Returning to Ledger Screen....");
    }

    public void displayReturningToHome() {
        System.out.println("Returning to Home Screen....");
    }

    public void displayExitingToHome() {
        System.out.println("Exiting to Home Screen.....");
    }

    public void displayExitMessage() {
        System.out.println("Exiting.... Goodbye!");
    }

    // Display transaction
    public void displayTransaction(Transaction transaction) {
        System.out.println(transaction);
    }

    // Display separator
    public void displaySeparator() {
        System.out.println("===========================================");
    }

    // Display blank line for spacing
    public void displayBlankLine() {
        System.out.println();
    }

    // Display vendor not found message
    public void displayVendorNotFound() {
        System.out.println("Vendor not found. Please try again.");
    }
}