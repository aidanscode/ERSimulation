package com.aidanmurphey.ersimulation.models;

import java.util.ArrayList;

public class Simulation {

	//Static logic
	static private Simulation instance;

	/**
	 * Return a singleton instance of the Simulation class
	 * @return A singleton instance of the Simulation class
	 */
	public static Simulation getInstance() {
		if (instance == null)
			instance = new Simulation();
		return instance;
	}

	//Instance logic
	private Hospital hospital;
	private int simulationTime, simulationLength, totalRooms, timeIncrement, startDelay;
	private double chanceOfPatientArrival;
	private ArrayList<Integer> waitTimes;

	/**
	 * Constructor
	 * Initializes the simulation, setting its parameters
	 */
	private Simulation() {
		simulationTime = 0; //The "current time" of the simulation
		simulationLength = 6 * 60; //6 hours (in minutes)
		totalRooms = 3; //The total available ER rooms at the hospital
		timeIncrement = 5; //In minutes, how much time passes each tick
		chanceOfPatientArrival = 0.33; //Each tick, the chance of a new patient arrival
		startDelay = 5; //Time before beginning simulation (seconds)

		hospital = new Hospital(totalRooms);
		waitTimes = new ArrayList<>();
	}

	/**
	 * Return the current time of the simulation (minutes since the simulation started)
	 * @return The current time of the simulation (minutes since the simulation started)
	 */
	public int getSimulationTime() {
		return simulationTime;
	}

	/**
	 * Return the chance of a patient arriving at the hospital at a given tick
	 * @return The chance of a patient arriving at the hospital at a given tick
	 */
	public double getChanceOfPatientArrival() {
		return chanceOfPatientArrival;
	}

	/**
	 * Store the new wait time of a patient to be used later
	 * @param waitTime The new wait time of a patient to be used later
	 */
	public void logWaitTime(int waitTime) {
		waitTimes.add(waitTime);
	}

	/**
	 * Begin the simulation
	 */
	public void launchSimulation() {
		printAllParameters();
		showCountdown();

		do {
			tick();
			simulationTime += timeIncrement;
		} while(simulationTime < simulationLength);

		System.out.println("Finished simulation, running calculations...");
		calculateStatistics();
	}

	/**
	 * Log a given message to the console, also showing the current time of the simulation
	 * @param message
	 */
	public void logMessage(String message) {
		System.out.println(this.getSimulationTime() + ": " + message);
	}

	/**
	 * The logic ran for each tick of the simulation
	 */
	private void tick() {
		hospital.potentialNewPatient();
		hospital.checkCurrentPatientsDone();
		hospital.attemptToClearWaitingRoom();
	}

	/**
	 * Print the average/maximum wait time of all patients who have left the waiting room
	 */
	private void calculateStatistics() {
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

	/**
	 * Print all of the parameters being used by the simulation
	 */
	private void printAllParameters() {
		System.out.println("Simulation Parameters:");
		System.out.println("Simulation Length: " + (simulationLength / 60) + " hours");
		System.out.println("ER Rooms Available: " + totalRooms);
		System.out.println("Chance of Patient Arrival Per Tick: " + chanceOfPatientArrival);
	}

	/**
	 * Print a countdown in console. Will be startDelay seconds long
	 */
	private void showCountdown() {
		while (startDelay > 0) {
			System.out.println("Starting simulation in " + (startDelay--) + "...");
			try {
				Thread.sleep(1000);
			} catch(Exception e) {}
		}
		System.out.println("\n\n\n\n\n\n");
	}

}
