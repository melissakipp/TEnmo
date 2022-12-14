package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public StringBuilder printUserList(List<User> users) {
        StringBuilder userList = new StringBuilder();
        Formatter userListFormatter = new Formatter(userList);

        System.out.println("\n-------------------------------------------");
        System.out.println("Users");
        System.out.println("ID                Name");
        System.out.println("-------------------------------------------");

        for (User user : users) {
            userListFormatter.format("%-14d %7s %n", user.getId(), user.getUsername());
        }
        return userList;
    }

    /*









*/
    public StringBuilder printTransferList(User user, Transfer[] transfers) {
        StringBuilder transferList = new StringBuilder();
        Formatter transferListFormatter = new Formatter(transferList);

        transferList.append("\n-------------------------------------------");
        transferList.append("\nTransfers");
        transferList.append("\nID          From/To                 Amount");
        transferList.append("\n-------------------------------------------");

        for (Transfer transfer : transfers) {
            String toOrFrom = "";
            // Logic to display whether the transaction was sent or received
            if (user.getUsername().equals(transfer.getRecipient())) {
                toOrFrom = "From: " + transfer.getSender();
            } else {
                toOrFrom = "To: " + transfer.getRecipient();
            }
            transferListFormatter.format("%n%-11d %-23s %2s",
                    transfer.getTransferId(),
                    toOrFrom,
                    transfer.getAmount().toString());
        }
        return transferList;
    }

}
