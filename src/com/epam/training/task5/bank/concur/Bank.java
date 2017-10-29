package com.epam.training.task5.bank.concur;

import com.epam.training.task5.bank.ImpossibleOperationException;

import java.util.concurrent.Semaphore;


public class Bank {
    //В будущем стоит изучить необходимость использования volatile для подобных переменных
    //В реальном продакшене без volatile это не будет работать
    private int money;

    //наручшение JCC - статические поля должны быть объявлены выше нестатических
    //По семантике - это поле не является статическим, поскольку регулирует доступ к полю money инстанса класса Bank
    //Это поле нарушает принцип "Open/Closed" из SOLID
    public static final Semaphore SEMAPHORE = new Semaphore(1, true);

    public Bank(int money) {
        this.money = money;
    }

    public int getMoney(int amount) throws ImpossibleOperationException {
        if (amount > money) {
            throw new ImpossibleOperationException("Bank has no money");
        }
        money -= amount;
        System.out.println("Money in bank: " + money);
        return amount;
    }

    public boolean hasMoney() {
        return money > 0;
    }
}
