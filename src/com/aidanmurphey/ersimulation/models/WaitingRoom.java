package com.aidanmurphey.ersimulation.models;

import java.util.Collections;
import java.util.PriorityQueue;

public class WaitingRoom {

    private PriorityQueue<Patient> queue;

    /**
     * Constructor
     */
    public WaitingRoom() {
        queue = new PriorityQueue<>(10, Collections.reverseOrder());
    }

    /**
     * Seat a given patient in the waiting room. Will set their waitTimeStart
     * @param patient The new patient to be seated
     */
    public void seatNewPatient(Patient patient) {
        patient.setWaitTimeStart(Simulation.getInstance().getSimulationTime());

        queue.add(patient);
    }

    /**
     * Return the highest priority patient in the waiting room - can be null
     * @return The highest priority patient in the waiting room - can be null
     */
    public Patient getNextPatient() {
        Patient patient = queue.poll();
        if (patient != null) {
            patient.setWaitTimeEnd(Simulation.getInstance().getSimulationTime());
        }

        return patient;
    }

    /**
     * Return whether or not the waiting room has any patients
     * @return Whether or not the waiting room has any patients
     */
    public boolean hasPatients() {
        return !queue.isEmpty();
    }

}
