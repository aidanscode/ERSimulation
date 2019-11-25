package com.aidanmurphey.ersimulation.models;

import java.util.Random;

public class Hospital {

    private WaitingRoom waitingRoom;
    private Patient[] erRooms;

    public Hospital(int erRooms) {
        this.waitingRoom = new WaitingRoom();
        this.erRooms = new Patient[erRooms];
    }

    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

    public boolean isErRoomAvailable() {
        for(Patient patient : erRooms) {
            if (patient == null) return true;
        }

        return false;
    }

    public void potentialNewPatient() {
        Random random = new Random();
        int chanceOfPatientArrivalConverted = (int) Math.pow(Simulation.getInstance().getChanceOfPatientArrival(), -1);
        boolean didPatientArrive = random.nextInt(chanceOfPatientArrivalConverted) == 0; //1 out of "chanceOfPatientArrivalConverted" chance of new patient arriving

        if (didPatientArrive) {
            System.out.println("New patient being seated at " + Simulation.getInstance().getSimulationTime());
            Patient newPatient = new Patient();
            getWaitingRoom().seatNewPatient(newPatient);
        }
    }

    public boolean attemptToFillErRoom(Patient patient) {
        //Don't bother if no rooms are available
        if (!isErRoomAvailable()) return false;

        //Put patient in first available room and then return true
        for(int i = 0; i < erRooms.length; i++) {
            if (erRooms[i] == null) {
                erRooms[i] = patient;
                break;
            }
        }

        return true;
    }

    public void attemptToClearWaitingRoom() {
        while(getWaitingRoom().hasPatients() && isErRoomAvailable()) {
            Patient nextPatient = getWaitingRoom().getNextPatient();
            nextPatient.setWaitTimeEnd(Simulation.getInstance().getSimulationTime());

            System.out.println("Patient being moved to ER room at " + Simulation.getInstance().getSimulationTime());
            attemptToFillErRoom(nextPatient);

            //Log wait time
            int waitTime = nextPatient.getWaitTimeEnd() - nextPatient.getWaitTimeStart();
            Simulation.getInstance().logWaitTime(waitTime);
        }
    }

    public void checkCurrentPatientsDone() {
        for(int i = 0; i < erRooms.length; i++) {
            Patient curPatient = erRooms[i];
            if (curPatient == null) continue;

            int doneTime = curPatient.getWaitTimeEnd() + curPatient.getTimeToAssist();

            if (doneTime <= Simulation.getInstance().getSimulationTime()) { //Patient is done being treated, open up room
                System.out.println("Patient finished treatment at " + Simulation.getInstance().getSimulationTime());
                erRooms[i] = null;
            }
        }
    }

}
