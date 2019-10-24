package de.ur.adp.backtracking.sheepwolves;

import java.util.Arrays;

public class ShoreSet {

    public final Shore start;       // Don't touch
    public final Shore destination; // Don't touch
    public final Animal[] strategy; // Don't touch
    private boolean startHasBoat;

    //TODO A ShoreSet should also store where the boat is (active shore)


    //TODO You can defiantly modify this constructor
    public ShoreSet(Shore start, Shore destination, Animal[] strategy) {
        this.start = start;
        this.destination = destination;
        this.strategy = strategy;
    }

    public boolean getBoat(){
        return startHasBoat;
    } // get boat position

    public void changeBoat(){
        startHasBoat = !startHasBoat;
    } // change actual boat position

    public void setBoat(boolean b){
        startHasBoat = b;
    } // set boat position


    @Override
    public String toString() { // prints animals at each shore, boat position and used strategy
        //TODO Expand your string representation with boat
        String boat = "destination";
        if(startHasBoat) boat = "start";
        return String.format("ShoreSet(start=%s, destination=%s, boat=" + boat + ", strategy=%s)",
                start, destination, Arrays.toString(strategy));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ShoreSet)) return false;
        ShoreSet other = (ShoreSet) obj;
        if (!start.equals(other.start)) return false;
        if (!destination.equals(other.destination)) return false;
        return Arrays.equals(strategy, other.strategy);
    }
}
