package com.aidanmurphey.ersimulation.models;

import java.util.Random;

public class Hospital {

    private WaitingRoom waitingRoom;
    private Patient[] erRooms;

    /**
     * Constructor
     * @param erRooms The number of ER rooms available for use in the hospital
     */
    public Hospital(int erRooms) {
        this.waitingRoom = new WaitingRoom();
        this.erRooms = new Patient[erRooms];
    }

    /**
     * Get the WaitingRoom in use by the hospital
     * @return WaitingRoom The WaitingRoom in use by the hospital
     */
    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

    /**
     * Checks and returns whether any ER rooms are not currently in use
     * @return Whether any ER rooms are not currently in use
     */
    public boolean isErRoomAvailable() {
        for(Patient patient : erRooms) {
            if (patient == null) return true;
        }

        return false;
    }

    /**
     * Using simulation parameters, checks whether a new patient has arrived and adds them to the hospital's waiting room if so
     */
    public void potentialNewPatient() {
        Random random = new Random();
        int chanceOfPatientArrivalConverted = (int) Math.pow(Simulation.getInstance().getChanceOfPatientArrival(), -1);
        boolean didPatientArrive = random.nextInt(chanceOfPatientArrivalConverted) == 0; //1 out of "chanceOfPatientArrivalConverted" chance of new patient arriving

        if (didPatientArrive) {
            Simulation.getInstance().logMessage("New patient being seated");
            Patient newPatient = new Patient();
            this.getWaitingRoom().seatNewPatient(newPatient);
        }
    }

    /**
     * Checks if any patients currently being treated in an ER room have finished treatment, removing them if so
     */
    public void checkCurrentPatientsDone() {
        for(int i = 0; i < erRooms.length; i++) {
            Patient curPatient = erRooms[i];
            if (curPatient == null) continue;

            int doneTime = curPatient.getWaitTimeEnd() + curPatient.getTimeToAssist();

            if (doneTime <= Simulation.getInstance().getSimulationTime()) { //Patient is done being treated, open up room
                Simulation.getInstance().logMessage("Patient finished treatment");
                erRooms[i] = null;
            }
        }
    }

    /**
     * For each currently available ER room, move the highest priority patient in the waiting room to an ER room
     */
    public void attemptToClearWaitingRoom() {
        while(this.getWaitingRoom().hasPatients() && this.isErRoomAvailable()) {
            Patient nextPatient = this.getWaitingRoom().getNextPatient();

            Simulation.getInstance().logMessage("Patient being moved to ER room");
            this.fillErRoom(nextPatient);

            //Log wait time
            int waitTime = nextPatient.getWaitTimeEnd() - nextPatient.getWaitTimeStart();
            Simulation.getInstance().logWaitTime(waitTime);
        }
    }

    /**
     * Place a given patient in the first available ER room
     * @param patient The patient to be placed in an ER room
     */
    private void fillErRoom(Patient patient) {
        //Put patient in first available room and then return true
        for(int i = 0; i < erRooms.length; i++) {
            if (erRooms[i] == null) {
                erRooms[i] = patient;
                break;
            }
        }
    }

}
