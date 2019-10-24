package de.ur.adp.backtracking.sheepwolves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Shore {

    // Stores the amount of animals
    private List<Animal> animalsOnShore = new ArrayList<>();

    public void add(Animal... animals) {
        animalsOnShore.addAll(Arrays.asList(animals));
    }

    public void remove(Animal... animals) {
        for (Animal animal : animals) {
            if (!animalsOnShore.remove(animal)) {
                throw new RuntimeException("Tried to remove " + animal + " from shore but there is none of that type");
            }
        }
    }

    // Returns a list filtered with the specified animal
    // e.g. List<Sheep> sheeps = getAnimalsOfType(Sheep.class);
    // Do not touch as it is used by JUnit-Test
    public <T extends Animal> List<T> getAnimalsOfType(Class<T> animalType) {
        return animalsOnShore.stream().filter(animalType::isInstance).map(animalType::cast).collect(Collectors.toList());
    }

    public boolean areSheepsStillSave() {
        if(!hasSheep()) return true; // if there is no sheep on shore return true
        int wolfCounter = 0; // counts wolves on shore
        int sheepCounter = 0; // counts sheep on shore

        for(Animal a : animalsOnShore) { // counts every animal

            if(a instanceof Wolf) wolfCounter++;
            else sheepCounter++;
        }
        return sheepCounter >= wolfCounter; // returns true if at least as much sheep as wolves
    }

    public boolean isShoreEmpty() {
        return animalsOnShore.isEmpty();
    }

    public boolean hasWolf(){ // returns true if shore has wolf on it
        if(!animalsOnShore.isEmpty()){
            for (Animal a : animalsOnShore) {
                if (a instanceof Wolf) return true;
            }
        }
        return false;
    }
    public boolean hasSheep(){ // returns true if shore has sheep on it
        if(!animalsOnShore.isEmpty()){
            for (Animal a : animalsOnShore) {
                if (a instanceof Sheep) return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        List<Wolf> wolves = getAnimalsOfType(Wolf.class);
        List<Sheep> sheep = getAnimalsOfType(Sheep.class);

        return String.format("Shore(wolves=%d, sheep=%d)", wolves.size(), sheep.size());
    }

    // Clones the shore, i.e. a new independent object is produced
    @Override
    public Shore clone() {
        Shore newShore = new Shore();
        Animal[] animalsOnThisShore = getAnimalsOfType(Animal.class).toArray(new Animal[0]);
        newShore.add(animalsOnThisShore);
        return newShore;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Shore)) return false;
        Shore other = (Shore) obj;
        return animalsOnShore.equals(other.animalsOnShore);
    }
}
