// Niklas Keller 2104858

package de.ur.adp.backtracking.sheepwolves;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Scenario {

    private List<ShoreSet> solution = new ArrayList<>();
    public List<String> solutionPath = new ArrayList<>();
    /**  i assumed that if the sheppard is at a shore with more wolves than sheep,
     *  it is legal, because he watches them. he is not allowed to leave
     *  until he takes as much wolves/ sheep with him that sheep are at least same amount as wolves
     *  f.E. there is 1 wolf and 1 sheep at destination and the sheppard arrives with 2 wolves
     *  (3 wolves, 1 sheep at destination), but takes the sheep back to boat it is legal. **/

    // different strategy's from start to destination than from destination to start,
    // start -> desti always with 2 animals, and desti -> start always with only 1
    // to avoid infinite loop and shorten runtime
    private final Animal[] STRATEGY_START_1 = {new Wolf(), new Sheep()};
    private final Animal[] STRATEGY_START_2 = {new Wolf(), new Wolf()};
    private final Animal[] STRATEGY_START_3 = {new Sheep(), new Sheep()};
    private final Animal[] STRATEGY_DESTI_1 = {new Sheep()};
    private final Animal[] STRATEGY_DESTI_2 = {new Wolf()};


    public Scenario(int animals) {
        Shore start = new Shore(); // creating start shore
        Shore destination = new Shore(); // creating destination shore

        for(int i = 0; i < animals; i++) { // filling start shore with animals
            start.add(new Wolf());
            start.add(new Sheep());
        }
        ShoreSet shores = new ShoreSet(start,destination,STRATEGY_START_1); // creating shoreset
        shores.setBoat(true); // setting boat to start
        solve(shores); // calling recursive method
    }

    private boolean solve(ShoreSet shores) {
        if(shores.start.isShoreEmpty()){ // finished when every animal is at destination
            shores.changeBoat();
            return true;
        }
        if(shores.getBoat()) {  // try strategy's from start to destination

            for(int i = 0; i < 3; i++) { // loops 3 times, because there's 3 strategy's from s to d

                if(canMove(chooseStrategy(i,shores),shores)){ // tests if all sheep are still alive after the move

                    move(chooseStrategy(i,shores), shores); // moves the animals for chosen strategy
                    if(solve(shores)){

                        addPath(chooseStrategy(i,shores),shores); // add move to solutionPath
                        solution.add(shores); // add shore to solution
                        return true;
                    }
                }
            }
        }
        else {  // try strategy's from destination to start

            for(int i = 0; i < 2; i++) {// loops 2 times, because there's 2 strategy's from d to s

                if(canMove(chooseStrategy(i,shores),shores)){  // tests if all sheep are still alive after the move

                    move(chooseStrategy(i,shores), shores); // moves the animals for chosen strategy
                    if(solve(shores)){

                        addPath(chooseStrategy(i, shores),shores); // add move to solutionPath
                        solution.add(shores); // add shore to solution
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Animal[] chooseStrategy(int i, ShoreSet shores) {
        if(shores.getBoat()){ // if boat is at start-shore, choose start-strategy
            if(i == 0) return STRATEGY_START_1;
            if(i == 1) return STRATEGY_START_2;
            if(i == 2) return STRATEGY_START_3;
        }
        if(!shores.getBoat()){ // if boat is at desti-shore, choose desti-strategy
            if(i == 0) return STRATEGY_DESTI_1;
            if(i == 1) return STRATEGY_DESTI_2;
        }
        return null;
    }

    public void addPath(Animal[] strategy, ShoreSet shores){
        shores.changeBoat(); // always changeBoat spot, because while jumping back,
                             // the boat doesn't move by calling move method
        String s = "";

        if(shores.getBoat()){ // if boat is at start now it moved from Destination to start
            s += "DESTINATION -> START: ";
        }
        else{
            s += "START -> DESTINATION: ";
        }

        if(strategy.length == 1){ // if only 1 animal is shipped

            if(strategy[0] instanceof Wolf) s += "[Wolf]";
            else s += "[Sheep]";
        }
        else{ // if 2 animals are shipped

            if(strategy[0] instanceof Wolf) s += "[Wolf]";
            else s += "[Sheep]";

            if(strategy[1] instanceof Wolf) s += "[Wolf]";
            else s += "[Sheep]";
        }

        s += "\n";
            solutionPath.add(s); // add new way to solutionPath
    }

    public void move(Animal[] strategy, ShoreSet shores) {
        if(canMove(strategy,shores)){
            for(Animal a : strategy) {

                if(strategy.length > 1) { //case from start to destination
                    shores.setBoat(false); // set boat to destination location
                    if(a instanceof Wolf) { // if a wolf is shipped
                        shores.start.remove(new Wolf()); // remove him from start
                        shores.destination.add(new Wolf()); // add him to destination
                    }
                    else { // if a sheep is shipped
                        shores.start.remove(new Sheep()); // remove it from start
                        shores.destination.add(new Sheep()); // add it to destination
                    }
                }
                else { // case from destination to start
                    shores.setBoat(true); // set boat to start location
                    if(a instanceof Wolf) {
                        shores.destination.remove(new Wolf()); // remove it from start
                        shores.start.add(new Wolf());break; // add it to destination
                    }
                    else {
                        shores.destination.remove(new Sheep()); // remove it from stat
                        shores.start.add(new Sheep()); // add it to destination
                    }
                }
            }
        }
    }

    public boolean canMove(Animal[] strategy, ShoreSet shores) {
        /* method tests if the boat move is legal by simulating the move with copy's and testing if
        there are still enough animals to ship and there are no more wolves than sheep on one shore*/

        Shore startCopy = shores.start.clone(); // clone startShore
        Shore destiCopy = shores.destination.clone(); // clone destinationShore
        for(Animal a : strategy) {

            if(strategy.length > 1) { //case from start to destination
                if(a instanceof Wolf) {
                    if(startCopy.hasWolf()){
                        startCopy.remove(new Wolf());
                        destiCopy.add(new Wolf());
                    }
                    else return false; // return false if there is no wolf to ship left
                }
                else {
                    if(startCopy.hasSheep()){
                        startCopy.remove(new Sheep());
                        destiCopy.add(new Sheep());
                    }
                    else return false; // return false if there is no sheep left
                }
            }
            else { // case from destination to start
                if(a instanceof Wolf) {
                    if(destiCopy.hasWolf()){
                        destiCopy.remove(new Wolf());
                        startCopy.add(new Wolf());
                    }
                    else return false; // return false if there is no wolf to ship left
                }
                else
                    if(destiCopy.hasSheep()){
                        destiCopy.remove(new Sheep());
                        startCopy.add(new Sheep());
                    }
                    else return false; // return false if there is no sheep left
            }
        }
        if (strategy.length > 1 && startCopy.areSheepsStillSave()) return true;
        if(strategy.length == 1 && destiCopy.areSheepsStillSave()) return true;
        return false; // return false if sheep are not alive anymore
    }

    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();
        solution.forEach(x -> representation.append(x).append("\n"));
        return representation.toString();
    }

    // This method is needed for JUnit-Test
    public String getSolutionPath() {
        String path = "";
        for(int i = solutionPath.size() - 1; i >= 0; i --){
            path += solutionPath.get(i);
        }
        return path;
    }

    // Do not touch as it is needed for JUnit-Test
    public List<ShoreSet> getSolution() {
        return solution;
    }
}

