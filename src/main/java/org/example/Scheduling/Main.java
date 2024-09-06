package org.example.Scheduling;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;

public class Main {
    public static void main(String[] args) {
        int numberOfJobs = 10;
        int numberOfResources = 3;
        int[] jobDurations = {2,4,6,8,1,3,5,7,2,4};
        int[] resourceCapacities = {10,10,10};
        Problem problem = new SchedulingProblem(numberOfJobs,numberOfResources,jobDurations,resourceCapacities);
        Algorithm algorithm = new NSGAII(problem);
        algorithm.step();
        NondominatedPopulation population = algorithm.getResult();
        for (Solution solution : population) {
            System.out.println("Time to complete: " + solution.getObjective(0));
            System.out.println("Resource Utilization: " + solution.getObjective(1));
        }
    }
}
