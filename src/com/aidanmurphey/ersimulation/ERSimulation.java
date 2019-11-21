package com.aidanmurphey.ersimulation;

import com.aidanmurphey.ersimulation.models.Hospital;
import com.aidanmurphey.ersimulation.models.Patient;

import java.util.*;

public class ERSimulation {

    static private Hospital hospital;
    static private int simulationTime, simulationLength, totalRooms, timeIncrement, startDelay;
    static private double chanceOfPatientArrival;
    static private ArrayList<Integer> waitTimes;

    public static void main(String[] args) {
        initializeSimulation();
        launchSimulation();
    }

    public static int getSimulationTime() {
        return simulationTime;
    }

    private static void initializeSimulation() {
        simulationTime = 0; //The "current time" of the simulation
        simulationLength = 6 * 60; //6 hours (in minutes)
        totalRooms = 3; //The total available ER rooms at the hospital
        timeIncrement = 5; //In minutes, how much time passes each tick
        chanceOfPatientArrival = 0.33; //Each tick, the chance of a new patient arrival
        startDelay = 5; //Time before beginning simulation (seconds)

        hospital = new Hospital(totalRooms);
        waitTimes = new ArrayList<>();
    }

    private static void launchSimulation() {
        System.out.println("Simulation Parameters:");
        System.out.println("Simulation Length: " + (simulationLength / 60) + " hours");
        System.out.println("ER Rooms Available: " + totalRooms);
        System.out.println("Chance of Patient Arrival Per Tick: " + chanceOfPatientArrival);

        while (startDelay > 0) {
            System.out.println("Starting simulation in " + (startDelay--) + "...");
            try {
                Thread.sleep(1000);
            } catch(Exception e) {}
        }
        System.out.println("\n\n\n\n\n\n");

        do {
            tick();
            simulationTime += timeIncrement;
        } while(simulationTime < simulationLength);

        System.out.println("Finished simulation, running calculations...");
        calculateStatistics();
    }

    private static void tick() {
        potentialNewPatient();
        hospital.checkCurrentPatientsDone();
        attemptToClearWaitingRoom();
    }

    private static void potentialNewPatient() {
        Random random = new Random();
        int chanceOfPatientArrivalConverted = (int) Math.pow(chanceOfPatientArrival, -1);
        boolean didPatientArrive = random.nextInt(chanceOfPatientArrivalConverted) == 0; //1 out of "chanceOfPatientArrivalConverted" chance of new patient arriving

        if (didPatientArrive) {
            System.out.println("New patient being seated at " + getSimulationTime());
            Patient newPatient = new Patient();
            hospital.getWaitingRoom().seatNewPatient(newPatient);
        }
    }

    private static void attemptToClearWaitingRoom() {
        while(hospital.getWaitingRoom().hasPatients() && hospital.isErRoomAvailable()) {
            Patient nextPatient = hospital.getWaitingRoom().getNextPatient();
            nextPatient.setWaitTimeEnd(getSimulationTime());

            System.out.println("Patient being moved to ER room at " + ERSimulation.getSimulationTime());
            hospital.attemptToFillErRoom(nextPatient);

            //Log wait time
            int waitTime = nextPatient.getWaitTimeEnd() - nextPatient.getWaitTimeStart();
            waitTimes.add(waitTime);
        }
    }

    private static void calculateStatistics() {
        float averageWaitTime;
        int totalWaitedTime = 0, maxWaitTime = 0;

        for(int time : waitTimes) {
            totalWaitedTime += time;

            if (time > maxWaitTime)
                maxWaitTime = time;
        }

        averageWaitTime = (float) totalWaitedTime / waitTimes.size();
        System.out.println("\n\nThe average wait time was: " + averageWaitTime + " minutes");
        System.out.println("The max wait time was: " + maxWaitTime + " minutes");
    }

}
