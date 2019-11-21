package com.aidanmurphey.ersimulation.models;

import com.aidanmurphey.ersimulation.ERSimulation;

import java.util.Collections;
import java.util.PriorityQueue;

public class WaitingRoom {

    private PriorityQueue<Patient> queue;

    public WaitingRoom() {
        queue = new PriorityQueue<>(10, Collections.reverseOrder());
    }

    public void seatNewPatient(Patient patient) {
        patient.setWaitTimeStart(ERSimulation.getSimulationTime());

        queue.add(patient);
    }

    public Patient getNextPatient() {
        return queue.poll();
    }

    public boolean hasPatients() {
        return queue.size() > 0;
    }

}