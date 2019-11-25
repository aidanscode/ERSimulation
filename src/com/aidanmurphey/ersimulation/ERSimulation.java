package com.aidanmurphey.ersimulation;

import com.aidanmurphey.ersimulation.models.Simulation;

public class ERSimulation {

    /**
     * Main method
     * Initializes and begins simulation
     * @param args (Unused)
     */
    public static void main(String[] args) {
        Simulation simulation = Simulation.getInstance();
        simulation.launchSimulation();
    }

}
