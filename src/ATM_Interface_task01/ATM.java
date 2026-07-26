package ATM_Interface_task01;

import java.util.Scanner;

public class ATM {

    private String userId;
    private int userPin;
    private final Scanner sc;

    public ATM() {
        sc = new Scanner(System.in);
        System.out.println("Welcome to ATM Interface");
    }

    public void login() {
        System.out.print("Enter User ID : ");
        userId = sc.nextLine().trim();

        System.out.print("Enter User PIN : ");
        userPin = readIntInput();
    }

    public String getUserId() {
        return userId;
    }

    public int getUserPin() {
        return userPin;
    }

    public void showMenu() {
        System.out.println("\n===== ATM Menu =====");
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
    }

    public int getUserChoice() {
        return readIntInput();
    }

    public double getAmount() {
        System.out.print("Enter Amount : ");
        return readDoubleInput();
    }

    public String getRecipientUserId() {
        System.out.print("Enter Recipient User ID : ");
        return sc.nextLine().trim();
    }

    public boolean processChoice(int choice, Bank bank, Account currentAccount) {
        switch (choice) {
            case 1 -> {
                Transaction.displayTransactionHistory();
                return true;
            }

            case 2 -> {
                double withdrawAmount = getAmount();
                String result = bank.processWithdraw(currentAccount, withdrawAmount);

                switch (result) {
                    case "SUCCESS" -> {
                        System.out.println("Withdraw Successful");
                        System.out.println("Remaining Balance: " + currentAccount.getBalance());
                    }
                    case "INSUFFICIENT_FUNDS" -> System.out.println("Insufficient Funds.");
                    case "INVALID_AMOUNT" -> System.out.println("Invalid Amount.");
                    case "ACCOUNT_INACTIVE" -> System.out.println("Account Inactive.");
                    case "ACCOUNT_NOT_FOUND" -> System.out.println("Account Not Found.");
                    default -> System.out.println("Withdraw Failed.");
                }
                return true;
            }

            case 3 -> {
                double depositAmount = getAmount();
                String result = bank.processDeposit(currentAccount, depositAmount);

                switch (result) {
                    case "SUCCESS" -> {
                        System.out.println("Deposit Successful");
                        System.out.println("Current Balance: " + currentAccount.getBalance());
                    }
                    case "INVALID_AMOUNT" -> System.out.println("Invalid Amount.");
                    case "ACCOUNT_INACTIVE" -> System.out.println("Account Inactive.");
                    case "ACCOUNT_NOT_FOUND" -> System.out.println("Account Not Found.");
                    default -> System.out.println("Deposit Failed.");
                }
                return true;
            }

            case 4 -> {
                String recipientId = getRecipientUserId();
                double transferAmount = getAmount();
                String result = bank.processTransfer(currentAccount, recipientId, transferAmount);

                switch (result) {
                    case "SUCCESS" -> {
                        System.out.println("Transfer Successful");
                        System.out.println("Remaining Balance: " + currentAccount.getBalance());
                    }
                    case "INSUFFICIENT_FUNDS" -> System.out.println("Insufficient Funds.");
                    case "INVALID_AMOUNT" -> System.out.println("Invalid Amount.");
                    case "ACCOUNT_INACTIVE" -> System.out.println("Account Inactive.");
                    case "RECIPIENT_NOT_FOUND" -> System.out.println("Recipient Account Not Found.");
                    case "SAME_ACCOUNT" -> System.out.println("Cannot Transfer to the Same Account.");
                    case "ACCOUNT_NOT_FOUND" -> System.out.println("Account Not Found.");
                    default -> System.out.println("Transfer Failed.");
                }
                return true;
            }

            case 5 -> {
                System.out.println("Thank you for using ATM Interface. Goodbye!");
                return false;
            }

            default -> {
                System.out.println("Invalid choice. Please try again.");
                return true;
            }
        }
    }

    private int readIntInput() {
        while (true) {
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number : ");
            }
        }
    }

    private double readDoubleInput() {
        while (true) {
            String input = sc.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid amount : ");
            }
        }
    }
}