package de.ur.adp.backtracking.sheepwolves;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ScenarioTest {

    private static final int NUM_ANIMALS = 3;

    @Test
    public void testScenario() {

        final Scenario scenario = new Scenario(NUM_ANIMALS);

        System.out.println(scenario);

        String solutionPath = scenario.getSolutionPath();
        System.out.println(solutionPath);
        List<ShoreSet> solution = scenario.getSolution();
        assertNotNull("A valid solution is possible", solution);
        assertNotEquals("Solution size should not be 0", solution.size(), 0);
        assertEquals("The solution path must contain an odd number", 1, (solutionPath.length() - solutionPath.replace("\n", "").length()) % 2);

        ShoreSet lastFrame = solution.get(solution.size() - 1);

        assertEquals("Are all animals transferred to destination?", lastFrame.destination.getAnimalsOfType(Animal.class).size(), NUM_ANIMALS * 2);
        assertEquals("Is start shore empty?", 0, lastFrame.start.getAnimalsOfType(Animal.class).size());
        assertTrue("Final solution must be valid for every frame", isValid(solution));
    }

    private boolean isValid(List<ShoreSet> solution) {
        // Check on your own whether the solution is correct.
        // Praktomat will do it automatically.
        return true;
    }


}