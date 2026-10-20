package Task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;

public class Bank {
    private final List<Account> accounts;

    public Bank(int accountsQty) {
        accounts = createAccounts(accountsQty);
    }

    private class Transaction implements Runnable {
        @Override
        public void run() {
            int fromId = ThreadLocalRandom.current().nextInt(0, accounts.size());
            int toId = ThreadLocalRandom.current().nextInt(0, accounts.size());

            Account from = accounts.get(fromId);
            Account to = accounts.get(toId);

            transfer(from, to, ThreadLocalRandom.current().nextInt(1, 301));
        }
    }

    private List<Account> createAccounts(int accountsQty) {
        List<Account> accounts = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < accountsQty; i++) {
            accounts.add(new Account(i, rand.nextInt(1, 1001)));
        }

        return accounts;
    }

    public int getAllAccountsBalance() {
        int totalBalance = 0;
        for (Account account : accounts) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }

    public void transfer(Account from, Account to, int amount) {
        if (from.equals(to) || amount <= 0) return;

        Lock firstLock = from.getId() < to.getId() ? from.getLock() : to.getLock();
        Lock secondLock = from.getId() < to.getId() ? to.getLock() : from.getLock();

        firstLock.lock();
        try {
            secondLock.lock();

            try {
                if (from.withdraw(amount)) {
                    to.deposit(amount);
                    System.out.printf("Transferred %d money from Account-%d to Account-%d%n",
                            amount, from.getId(), to.getId());
                }
            } finally {
                secondLock.unlock();
            }
        } finally {
            firstLock.unlock();
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank(100);

        int initialBankBalance = bank.getAllAccountsBalance();

        try (ExecutorService pool = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 2000; i++) {
                pool.execute(bank.new Transaction());
            }
        }

        int finalBankBalance = bank.getAllAccountsBalance();

        System.out.printf("%nInitial bank balance: %d%n", initialBankBalance);
        System.out.printf("Final bank balance: %d", finalBankBalance);
    }
}
