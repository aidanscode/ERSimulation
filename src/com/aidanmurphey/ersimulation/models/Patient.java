package com.aidanmurphey.ersimulation.models;

import java.util.Random;

public class Patient implements Comparable {

    private int condition, waitTimeStart, waitTimeEnd, timeToAssist;

    public Patient() {
        Random random = new Random();
        condition = random.nextInt(8) + 1; //Condition is 1-8
        timeToAssist = 5 + (condition * 5); //Between 10-45 minutes to assist a patient (Once they arrive at a ER room)

        waitTimeStart =
        waitTimeEnd = -1;
    }

    public int getCondition() {
        return condition;
    }

    public int getWaitTimeStart() {
        return waitTimeStart;
    }

    public void setWaitTimeStart(int waitTimeStart) {
        this.waitTimeStart = waitTimeStart;
    }

    public int getWaitTimeEnd() {
        return waitTimeEnd;
    }

    public void setWaitTimeEnd(int waitTimeEnd) {
        this.waitTimeEnd = waitTimeEnd;
    }

    public int getTimeToAssist() {
        return timeToAssist;
    }

    @Override
    public int compareTo(Object o) {
        Patient other = (Patient) o;

        return Integer.compare(this.getCondition(), other.getCondition());
    }

    @Override
    public String toString() {
        return "\nCondition Level: " + condition + "\nTime to Assist: " + timeToAssist + "\n";
    }
}
