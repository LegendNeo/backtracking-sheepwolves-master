package de.ur.adp.backtracking.sheepwolves;

public class Wolf implements Animal {

    // Nothing to do here

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Wolf;
    }

    @Override
    public String toString() {
        return "Wolf";
    }
}
