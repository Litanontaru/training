package com.epam.training.task5.philosophers.concur;


import java.util.concurrent.locks.ReentrantLock;

//Маркерный класс - это антипаттерн. По возможности его нужно избегать
public class Fork extends ReentrantLock {

    public Fork() {
    }
}
