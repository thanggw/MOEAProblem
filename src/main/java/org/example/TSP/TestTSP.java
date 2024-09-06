package org.example.TSP;

import org.example.TSP.TSPProblem;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

import org.moeaframework.core.variable.Permutation;

public class TestTSP {
    public static void main(String[] args) {
        double[][] distanceMatrix = {// 3+1+1+8+8=21
                {0, 2, 9, 10, 1},
                {1, 0, 6, 4, 7},
                {15, 7, 0, 8, 3},
                {6, 3, 12, 0, 5},
                {10, 4, 8, 5, 0}
        };
        TSPProblem problem = new TSPProblem(distanceMatrix);
        Algorithm algorithm = new NSGAII(problem);
        algorithm.step();
        NondominatedPopulation population = algorithm.getResult();
        for (Solution solution : population) {
            Permutation tour = (Permutation) solution.getVariable(0);
            System.out.println("Tour: " + tour.toString());
            System.out.println("Total Distance: " + solution.getObjective(0));
        }
    }
}
