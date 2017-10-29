package com.epam.training.task5.bank.concur;

import com.epam.training.task5.bank.ImpossibleOperationException;

public class BankUser implements Runnable {

    //поле может быть final
    private Bank bank;

    //нарушение правила именования констант
    //нарушение порядка объявления полей
    private static int removalAmount = 5;

    public BankUser(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        try {
            //ошибочное распололжение захвата семафора - вне while
            Bank.SEMAPHORE.acquire();
            while (bank.hasMoney()) {
                bank.getMoney(removalAmount);
            }
            Bank.SEMAPHORE.release();

        } catch (ImpossibleOperationException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Thread finished");
    }
}
