package com.aidanmurphey.ersimulation.models;

import com.aidanmurphey.ersimulation.ERSimulation;

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

    public void checkCurrentPatientsDone() {
        for(int i = 0; i < erRooms.length; i++) {
            Patient curPatient = erRooms[i];
            if (curPatient == null) continue;

            int doneTime = curPatient.getWaitTimeEnd() + curPatient.getTimeToAssist();

            if (doneTime <= ERSimulation.getSimulationTime()) { //Patient is done being treated, open up room
                System.out.println("Patient finished treatment at " + ERSimulation.getSimulationTime());
                erRooms[i] = null;
            }
        }
    }

}
