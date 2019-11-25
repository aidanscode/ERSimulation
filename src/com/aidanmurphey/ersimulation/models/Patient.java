package com.aidanmurphey.ersimulation.models;

import java.util.Random;

public class Patient implements Comparable {

    private int condition, waitTimeStart, waitTimeEnd, timeToAssist;

    /**
     * Constructor
     * Initializes new patient with a random condition level (1-8) and calculates timeToAssist based on condition
     */
    public Patient() {
        Random random = new Random();
        condition = random.nextInt(8) + 1; //Condition is 1-8
        timeToAssist = 5 + (condition * 5); //Between 10-45 minutes to assist a patient (Once they arrive at a ER room)

        waitTimeStart =
        waitTimeEnd = -1;
    }

    /**
     * Return the condition/severity level of the patient
     * @return The condition/severity level of the patient
     */
    public int getCondition() {
        return condition;
    }

    /**
     * Return the time at which the patient arrived in the waiting room
     * @return The time at which the patient arrived in the waiting room
     */
    public int getWaitTimeStart() {
        return waitTimeStart;
    }

    /**
     * Set the time at which the patient arrived in the waiting room
     * @param waitTimeStart The time at which the patient arrived in the waiting room
     */
    public void setWaitTimeStart(int waitTimeStart) {
        this.waitTimeStart = waitTimeStart;
    }

    /**
     * Return the time at which the patient left the waiting room (moved to ER room)
     * @return The time at which the patient left the waiting room (moved to ER room)
     */
    public int getWaitTimeEnd() {
        return waitTimeEnd;
    }

    /**
     * Set the time at which the patient left the waiting room (moved to ER room)
     * @param waitTimeEnd The time at which the patient left the waiting room (moved to ER room)
     */
    public void setWaitTimeEnd(int waitTimeEnd) {
        this.waitTimeEnd = waitTimeEnd;
    }

    /**
     * Return the time needed to assist the patient once they are moved to the ER room
     * @return The time needed to assist the patient once they are moved to the ER room
     */
    public int getTimeToAssist() {
        return timeToAssist;
    }

    /**
     * Compare this Patient instance to another, compares based off of patient's condition level
     * @param o The patient instance to compare to
     * @return An integer representing the value comparison between the two patients
     */
    @Override
    public int compareTo(Object o) {
        Patient other = (Patient) o;

        return Integer.compare(this.getCondition(), other.getCondition());
    }

    /**
     * Return a string showing some attributes of this patient
     * @return A string showing some attributes of this patient
     */
    @Override
    public String toString() {
        return "\nCondition Level: " + condition + "\nTime to Assist: " + timeToAssist + "\n";
    }

}
