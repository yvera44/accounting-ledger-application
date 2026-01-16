package com.pluralsight;

import com.pluralsight.data.Transaction;
import com.pluralsight.ui.UserInterface;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Application {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserInterface userInterface = new UserInterface(scanner);
    static ArrayList<Transaction> transactions = readTransactions();

    public static void main(String[] args) {
        runMainMenu();
        scanner.close();
    }

    // Main menu loop
    public static void runMainMenu() {
        boolean running = true;

        while (running) {
            userInterface.displayMainMenu();
            String choice = userInterface.getUserInput("Choose an option: ");

            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    runLedger();
                    break;
                case "X":
                    userInterface.displayExitMessage();
                    running = false;
                    break;
                default:
                    userInterface.displayInvalidChoice();
            }

            userInterface.displayBlankLine();
        }
    }

    public static void addDeposit() {
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            double userDepositAmount;
            Transaction transaction;
            try {
                userDepositAmount = userInterface.getDoubleInput("Add deposit amount: ");
                String userDescription = userInterface.getStringInput("Enter description: ");
                String userToVendor = userInterface.getStringInput("Enter vendor: ");

                transaction = new Transaction(
                        LocalDate.now(),
                        LocalTime.now(),
                        userDescription,
                        userToVendor,
                        userDepositAmount
                );
                userInterface.displayDepositSuccess();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            bufferedWriter.write("\n" + transaction.toCsv());
            bufferedWriter.flush();
            bufferedWriter.close();
            transactions.add(transaction);
        } catch (IOException e) {
            userInterface.displayError("An unexpected error occurred");
            e.printStackTrace();
        }
    }

    private static void makePayment() {
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            double userPaymentAmount;
            Transaction transaction;
            try {
                userPaymentAmount = userInterface.getDoubleInput("Add payment amount: ");
                String userDescription = userInterface.getStringInput("Enter description: ");
                String userToVendor = userInterface.getStringInput("Enter vendor: ");

                transaction = new Transaction(
                        LocalDate.now(),
                        LocalTime.now(),
                        userDescription,
                        userToVendor,
                        -userPaymentAmount
                );
                userInterface.displayPaymentSuccess();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            bufferedWriter.write("\n" + transaction.toCsv());
            bufferedWriter.flush();
            bufferedWriter.close();
            transactions.add(transaction);
        } catch (IOException e) {
            userInterface.displayError("An unexpected error occurred");
            e.printStackTrace();
        }
    }

    // Ledger loop
    public static void runLedger() {
        boolean running = true;

        while (running) {
            userInterface.displayLedgerMenu();
            String choice = userInterface.getUserInput("Choose an option: ");

            switch (choice) {
                case "A":
                    displayAllEntries();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    runReports();
                    break;
                case "H":
                    userInterface.displayReturningToHome();
                    running = false;
                    break;
                default:
                    userInterface.displayInvalidChoice();
            }

            userInterface.displayBlankLine();
        }
    }

    // Display All Entries
    public static void displayAllEntries() {
        for (Transaction transaction : transactions) {
            userInterface.displayTransaction(transaction);
        }
    }

    // Display Deposits
    public static void displayDeposits() {
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() >= 0) {
                userInterface.displayTransaction(transaction);
            }
        }
    }

    // Display Payments
    public static void displayPayments() {
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                userInterface.displayTransaction(transaction);
            }
        }
    }

    // Open Reports Menu
    public static void runReports() {
        boolean running = true;

        while (running) {
            userInterface.displayReportsMenu();
            String choice = userInterface.getUserInput("Choose an option: ");

            switch (choice) {
                case "1":
                    displayMonthToDate();
                    break;
                case "2":
                    displayPreviousMonth();
                    break;
                case "3":
                    displayYearToDate();
                    break;
                case "4":
                    displayPreviousYear();
                    break;
                case "5":
                    displayByVendor();
                    break;
                case "0":
                    userInterface.displayReturningToLedger();
                    running = false;
                    break;
                case "H":
                    userInterface.displayExitingToHome();
                    running = false;
                    break;
                default:
                    userInterface.displayInvalidChoice();
            }

            userInterface.displayBlankLine();
        }
    }

    // Report options
    public static void displayMonthToDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.withDayOfMonth(1);

        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().isAfter(startDate)) {
                userInterface.displayTransaction(transaction);
            }
        }
        userInterface.displaySeparator();
    }

    public static void displayPreviousMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        int previousMonth = lastMonth.getMonthValue();
        int currentYear = lastMonth.getYear();

        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().getMonthValue() == previousMonth
                    && transaction.getTransactionDate().getYear() == currentYear) {
                userInterface.displayTransaction(transaction);
            }
        }
        userInterface.displaySeparator();
    }

    public static void displayYearToDate() {
        int currentYear = LocalDate.now().getYear();

        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().getYear() == currentYear) {
                userInterface.displayTransaction(transaction);
            }
        }
        userInterface.displaySeparator();
    }

    public static void displayPreviousYear() {
        LocalDate currentYear = LocalDate.now();
        LocalDate oneYearAgo = currentYear.minusYears(1);
        int previousYear = oneYearAgo.getYear();

        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().getYear() == previousYear) {
                userInterface.displayTransaction(transaction);
            }
        }
        userInterface.displaySeparator();
    }

    public static void displayByVendor() {
        String searchVendor = userInterface.getStringInput("Enter vendor name: ");
        boolean found = false;

        for (Transaction transaction : transactions) {
            if (transaction.getVendor().equalsIgnoreCase(searchVendor)) {
                userInterface.displayTransaction(transaction);
                found = true;
            }
        }

        if (!found) {
            userInterface.displayVendorNotFound();
        }
    }

    private static ArrayList<Transaction> readTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("|"));

                if (parts.length != 5) continue;

                Transaction transaction = new Transaction();

                String transactionDateAsString = parts[0];
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate transactionDate = LocalDate.parse(transactionDateAsString, formatter1);
                transaction.setTransactionDate(transactionDate);

                String transactionTimeAsString = parts[1];
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime transactionTime = LocalTime.parse(transactionTimeAsString, formatter2);
                transaction.setTransactionTime(transactionTime);

                String description = parts[2];
                transaction.setDescription(description);

                String vendor = parts[3];
                transaction.setVendor(vendor);

                String amountAsString = parts[4];
                double amount = Double.parseDouble(amountAsString);
                transaction.setAmount(amount);
                transactions.add(transaction);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
