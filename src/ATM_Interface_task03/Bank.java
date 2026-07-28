package ATM_Interface_task03;

import java.util.ArrayList;

public class Bank {
    private ArrayList<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();

        // Sample accounts for testing
        accounts.add(new Account("SBI101", 1234, 50000, "ACC101", "ACTIVE"));
        accounts.add(new Account("SBI102", 2222, 30000, "ACC102", "ACTIVE"));
        accounts.add(new Account("SBI103", 3333, 10000, "ACC103", "INACTIVE"));
    }

    private Account findAccount(String userId) {
        for (Account account : accounts) {
            if (account.getUserId().equalsIgnoreCase(userId)) {
                return account;
            }
        }
        return null;
    }



    //Account InActive
    public String authenticateStatus(String userId, int pin) {
        Account account = findAccount(userId);

        if (account == null) {
            return "ACCOUNT_NOT_FOUND";
        }

        if (!account.verifyPin(pin)) {
            return "INVALID_PIN";
        }

        if (!account.isActive()) {
            return "ACCOUNT_INACTIVE";
        }

        return "SUCCESS";
    }

    public Account getAccount(String userId) {
        return findAccount(userId);
    }

    //=====================================================================================
    public String processDeposit(Account account, double amount) {
        if (account == null) return "ACCOUNT_NOT_FOUND";

        String result = account.deposit(amount);
        if ("SUCCESS".equals(result)) {
            new Transaction("Deposit", amount, account.getUserId(), "SELF", account.getBalance()).recordTransaction();
        }
        return result;
    }

    public String processWithdraw(Account account, double amount) {
        if (account == null) return "ACCOUNT_NOT_FOUND";

        String result = account.withdraw(amount);
        if ("SUCCESS".equals(result)) {
            new Transaction("Withdraw", amount, account.getUserId(), "SELF", account.getBalance()).recordTransaction();
        }
        return result;
    }

    public String processTransfer(Account sender, String recipientUserId, double amount) {
        if (sender == null) return "ACCOUNT_NOT_FOUND";
        if (!sender.isActive()) return "ACCOUNT_INACTIVE";
        if (amount <= 0) return "INVALID_AMOUNT";

        Account recipient = findAccount(recipientUserId);
        if (recipient == null) return "RECIPIENT_NOT_FOUND";
        if (sender.getUserId().equalsIgnoreCase(recipient.getUserId())) return "SAME_ACCOUNT";

        String withdrawResult = sender.withdraw(amount);
        if (!"SUCCESS".equals(withdrawResult)) {
            return withdrawResult;
        }

        String depositResult = recipient.deposit(amount);
        if (!"SUCCESS".equals(depositResult)) {
            // rollback if recipient deposit somehow fails
            sender.deposit(amount);
            return depositResult;
        }

        new Transaction(
                "Transfer",
                amount,
                sender.getUserId(),
                recipient.getUserId(),
                sender.getBalance()
        ).recordTransaction();

        return "SUCCESS";
    }
}