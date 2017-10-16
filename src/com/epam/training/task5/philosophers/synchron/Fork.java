package com.epam.training.task5.philosophers.synchron;

import com.epam.training.task5.philosophers.PositionOfPhilosopher;

public class Fork {

    private PositionOfPhilosopher positionOfPhilosopher = null;

    public PositionOfPhilosopher getPositionOfPhilosopher() {
        return positionOfPhilosopher;
    }

    public void setPositionOfPhilosopher(PositionOfPhilosopher positionOfPhilosopher) {
        this.positionOfPhilosopher = positionOfPhilosopher;
    }
}
