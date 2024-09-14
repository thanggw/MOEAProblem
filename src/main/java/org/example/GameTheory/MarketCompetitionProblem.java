package org.example.GameTheory;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

public class MarketCompetitionProblem extends AbstractProblem {

    private static final double[][][] profitMatrix = {
            // A-D,         A-N,        N-D,             N-N
            {{10, 10, 10}, {8, 12, 8},    {9, 11, 9},  {7, 13, 7}},
            {{12, 8, 8},   {10, 10, 10},  {11, 9, 9},  {9, 11, 9}},
            {{9, 9, 10},   {7, 11, 12},   {8, 10, 11}, {6, 12, 13}},
            {{8, 10, 8},   {6, 12, 10},   {7, 11, 11}, {5, 13, 12}}
    };

    public MarketCompetitionProblem() {
        super(3, 3);  // 3 variables (for 3 companies) and 3 objectives (profit of each company)
    }

    @Override
    public void evaluate(Solution solution) {
        int companyAStrategy = EncodingUtils.getInt(solution.getVariable(0));  // 0 to 3 for company A's strategy
        int companyBStrategy = EncodingUtils.getInt(solution.getVariable(1));  // 0 to 3 for company B's strategy
        int companyCStrategy = EncodingUtils.getInt(solution.getVariable(2));  // 0 to 3 for company C's strategy

        // Calculate profits based on the strategy choices of all companies
        double[] profits = profitMatrix[companyAStrategy][companyBStrategy];

        solution.setObjective(0, -profits[0]);  // Maximizing profit for company A
        solution.setObjective(1, -profits[1]);  // Maximizing profit for company B
        solution.setObjective(2, -profits[2]);  // Maximizing profit for company C
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(3, 3);  // 3 variables, 3 objectives

        // Each company can choose between 4 strategies (0: A-D, 1: A-N, 2: N-D, 3: N-N)
        solution.setVariable(0, EncodingUtils.newInt(0, 3));  // Company A's strategy
        solution.setVariable(1, EncodingUtils.newInt(0, 3));  // Company B's strategy
        solution.setVariable(2, EncodingUtils.newInt(0, 3));  // Company C's strategy

        return solution;
    }
}
