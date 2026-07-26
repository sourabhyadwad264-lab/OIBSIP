package ATM_Interface_task01;

public class Account {
    private String userId;
    private int pin;
    private double balance;
    private String accountNumber;
    private String accountStatus;

    public Account(String userId, int pin, double balance, String accountNumber, String accountStatus) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.accountNumber = accountNumber;
        this.accountStatus = accountStatus;
    }

    public String getUserId() {
        return userId;
    }

    public boolean verifyPin(int enteredPin) {
        return this.pin == enteredPin;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(accountStatus);
    }

    public String deposit(double amount) {
        if (!isActive()) return "ACCOUNT_INACTIVE";
        if (amount <= 0) return "INVALID_AMOUNT";

        balance += amount;
        return "SUCCESS";
    }

    public String withdraw(double amount) {
        if (!isActive()) return "ACCOUNT_INACTIVE";
        if (amount <= 0) return "INVALID_AMOUNT";
        if (balance < amount) return "INSUFFICIENT_FUNDS";

        balance -= amount;
        return "SUCCESS";
    }
}