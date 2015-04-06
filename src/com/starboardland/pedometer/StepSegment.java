package com.starboardland.pedometer;

/**
 * Created by ChandlerWu on 4/3/15.
 */
public class StepSegment {


    private int steps;
    private long segment;

    public StepSegment() {
        segment = -1;
        steps = 0;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public long getSegment() {
        return segment;
    }

    public void setSegment(int segment) {
        this.segment = segment;
    }


}
