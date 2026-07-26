package ATM_Interface_task01;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Transaction {
    private static final ArrayList<Transaction> transactionHistory = new ArrayList<>();
    private static int transactionCounter = 1;

    private String transactionId;
    private String transactionType;
    private double amount;
    private String dateTime;
    private String fromUserId;
    private String toUserId;
    private double remainingBalance;

    public Transaction(String transactionType, double amount, String fromUserId, String toUserId, double remainingBalance) {
        this.transactionId = "TXN" + String.format("%03d", transactionCounter++);
        this.transactionType = transactionType;
        this.amount = amount;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.remainingBalance = remainingBalance;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateTime = LocalDateTime.now().format(formatter);
    }

    public void recordTransaction() {
        transactionHistory.add(this);
    }

    public static void displayTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("\nNo transactions found.");
            return;
        }

        System.out.println("\n========== Transaction History ==========");
        for (Transaction t : transactionHistory) {
            System.out.println(t);
            System.out.println("----------------------------------------");
        }
    }

    @Override
    public String toString() {
        return "Transaction ID : " + transactionId +
                "\nTransaction Type : " + transactionType +
                "\nAmount           : " + amount +
                "\nDate & Time      : " + dateTime +
                "\nFrom User ID     : " + fromUserId +
                "\nTo User ID       : " + toUserId +
                "\nRemaining Balance: " + remainingBalance;
    }
}