package com.aidanmurphey.ersimulation.models;

import java.util.ArrayList;

public class Simulation {

	//Static logic
	static private Simulation instance;
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

	public Simulation() {
		simulationTime = 0; //The "current time" of the simulation
		simulationLength = 6 * 60; //6 hours (in minutes)
		totalRooms = 3; //The total available ER rooms at the hospital
		timeIncrement = 5; //In minutes, how much time passes each tick
		chanceOfPatientArrival = 0.33; //Each tick, the chance of a new patient arrival
		startDelay = 5; //Time before beginning simulation (seconds)

		hospital = new Hospital(totalRooms);
		waitTimes = new ArrayList<>();
	}

	public int getSimulationTime() {
		return simulationTime;
	}

	public double getChanceOfPatientArrival() {
		return chanceOfPatientArrival;
	}

	public void logWaitTime(int waitTime) {
		waitTimes.add(waitTime);
	}

	public void launchSimulation() {
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

	private void tick() {
		hospital.potentialNewPatient();
		hospital.checkCurrentPatientsDone();
		hospital.attemptToClearWaitingRoom();
	}

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

}
