package com.epam.training.task5.philosophers.synchron;

import com.epam.training.task5.philosophers.PositionOfPhilosopher;

import java.util.concurrent.atomic.AtomicInteger;

public class Philosopher implements Runnable {

    private int number;
    private Fork leftFork;
    private Fork rightFork;

    private static AtomicInteger TOTAL_NUMBERS_ITERATION = new AtomicInteger(100);


    public Philosopher(Fork leftFork, Fork rightFork, int number) {
        this.number = number;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        try {
            while (TOTAL_NUMBERS_ITERATION.decrementAndGet() > 0) {
                synchronized (leftFork) {
                    synchronized (rightFork) {
                        takeLeftFork(leftFork);
                        takeRightFork(rightFork);
                        eat();
                        put(rightFork);
                        put(leftFork);
                    }
                }
                think();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.format("Philosopher %d ate \n", number);
    }

    private void takeLeftFork(Fork fork){
        while (fork.getPositionOfPhilosopher() == PositionOfPhilosopher.LEFT) {
            continue;
        }
        fork.setPositionOfPhilosopher(PositionOfPhilosopher.RIGHT);
    }

    private void takeRightFork(Fork fork){
        while (fork.getPositionOfPhilosopher() == PositionOfPhilosopher.RIGHT) {
            continue;
        }
        fork.setPositionOfPhilosopher(PositionOfPhilosopher.LEFT);
    }

    private void eat() throws InterruptedException {
        System.out.format("Philosopher %d eating \n", number);
        //Thread.sleep(10);
    }

    private void put(Fork fork) {
        fork.setPositionOfPhilosopher(null);
    }

    private void think() throws InterruptedException {
        System.out.format("Philosopher %d thinking \n", number);
        Thread.sleep(200);
    }
}
