package com.aidanmurphey.ersimulation;

import com.aidanmurphey.ersimulation.models.Simulation;

public class ERSimulation {

    public static void main(String[] args) {
        Simulation simulation = Simulation.getInstance();
        simulation.launchSimulation();
    }

}
