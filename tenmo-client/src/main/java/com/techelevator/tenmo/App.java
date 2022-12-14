package com.techelevator.tenmo;

import com.techelevator.tenmo.exceptions.UserNotFoundException;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final UserService userService = new UserService(API_BASE_URL);
    private final TransferService transferService = new TransferService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) throws UserNotFoundException {
        App app = new App();
        app.run();
    }

    private void run() throws UserNotFoundException {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() throws UserNotFoundException {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        System.out.println("Your current account balance is: $" + accountService.getBalance(currentUser));
	}

    /*













     */

	private void viewTransferHistory() {
		// Prints out all Transfers
        System.out.println(consoleService.printTransferList(currentUser.getUser(), transferService.getAllTransfers(currentUser)));
        // Asks for user input
        int transferIdInput = consoleService.promptForInt("\nPlease enter transfer ID to view details (0 to cancel): ");
        // If a Transfer ID was selected print out the selected Transferprivate final long SEND_MONEY_ID = 2L;
        if (transferIdInput != 0) {
            System.out.println(transferService.getTransfer(currentUser, transferIdInput));
        }
	}

    /*
















     */
	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() throws UserNotFoundException {
		// TODO Auto-generated method stub
        List<User> users = userService.getAllUsersExceptCurrent(currentUser);
        System.out.println(consoleService.printUserList(users));
        // method given in starter code
        Long recipientId =(long) consoleService.promptForInt("Enter ID of user you are sending to (0 to cancel): ");
        // method given in starter code
        BigDecimal amountToTransfer = consoleService.promptForBigDecimal("Enter amount: ");
        System.out.println(transferService.transfer(currentUser, recipientId, amountToTransfer));
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
