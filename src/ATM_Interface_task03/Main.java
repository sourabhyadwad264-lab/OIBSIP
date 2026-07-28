package ATM_Interface_task03;

public class Main {

    public static void main(String[] args) {

        ATM atm = new ATM();
        Bank bank = new Bank();

        String authResult = "INVALID_PIN";
        Account currentAccount = null;

        // Maximum 3 login attempts
        for (int attempt = 1; attempt <= 3; attempt++) {

            System.out.println("\n========== Login Attempt " + attempt + " ==========");

            atm.login();

            authResult = bank.authenticateStatus(
                    atm.getUserId(),
                    atm.getUserPin()
            );

            if ("SUCCESS".equals(authResult)) {
                currentAccount = bank.getAccount(atm.getUserId());
                System.out.println("\nLogin Successful.");
                break;
            } else if ("ACCOUNT_INACTIVE".equals(authResult)) {
                System.out.println("\nAccount Inactive.");
                return;
            } else {
                System.out.println("\nInvalid User ID or PIN.");

                if (attempt < 3) {
                    System.out.println("Remaining Attempts : " + (3 - attempt));
                }
            }
        }


        if (currentAccount == null) {
            System.out.println("\nAccess Denied.");
            System.out.println("Maximum login attempts exceeded.");
            return;
        }


        boolean continueSession;
        do {
            atm.showMenu();

            System.out.print("Enter your choice : ");
            int choice = atm.getUserChoice();

            continueSession = atm.processChoice(choice, bank, currentAccount);

        } while (continueSession);
    }
}