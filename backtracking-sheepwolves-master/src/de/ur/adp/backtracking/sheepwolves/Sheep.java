package de.ur.adp.backtracking.sheepwolves;

public class Sheep implements Animal {

    // Nothing to do here

    @Override
    public int hashCode() {
        return 2;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Sheep;
    }

    @Override
    public String toString() {
        return "Sheep";
    }
}
