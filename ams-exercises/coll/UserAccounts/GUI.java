package coll.UserAccounts;

/**
 * A primitive front-end for the User and BankAccount classes.
 */
public class GUI {
    /**
     * Transfers money from one account to another. If the withdraw fails due to a
     * UserException then print "Unauthorized access!" If the withdraw fails due to
     * a FundsException then print "Insufficient funds!"
     *
     * @param user - the user that is attempting to execute the transfer.
     * @param b1 - the bank account that the funds are being transferred from.
     * @param b2 - the bank account that the funds are being transferred to.
     * @param amount - the amount of money to be transferred in $.
     */
    public static void transfer(User user, BankAccount b1, BankAccount b2, double amount) {
        try{
            b1.withdraw(user,amount);
            b2.deposit(amount);
        }catch (FundsException f){
            System.out.println(f.getMessage());
        }catch (UserException u){
            System.out.println(u.getMessage());
        }

    }
}