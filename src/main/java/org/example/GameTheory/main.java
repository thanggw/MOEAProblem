package org.example.GameTheory;

import org.example.GameTheory.MarketCompetitionProblem;
import org.moeaframework.algorithm.NSGAIII;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.spi.AlgorithmFactory;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.util.TypedProperties;

public class main {

    public static void main(String[] args) {
        Problem problem = new MarketCompetitionProblem();

        // Cấu hình các thuộc tính cần thiết cho NSGA-III
        TypedProperties properties = new TypedProperties();
        properties.setInt("divisionsOuter", 12);  // Số lượng phần chia ranh giới bên ngoài
        properties.setInt("divisionsInner", 0);   // Số lượng phần chia bên trong (có thể để là 0)

        // Khởi tạo thuật toán NSGA-III với cấu hình
        NSGAIII algorithm = (NSGAIII) AlgorithmFactory.getInstance().getAlgorithm(
                "NSGAIII", properties, problem
        );

        // Chạy thuật toán trong một số bước nhất định
        for (int i = 0; i < 100; i++) {
            algorithm.step();  // Chạy từng bước
        }

        // Lấy kết quả Pareto tối ưu
        NondominatedPopulation result = algorithm.getResult();

        // Hiển thị các giải pháp tối ưu
        for (Solution solution : result) {
            System.out.println("Profit of Company A: " + (-solution.getObjective(0)));
            System.out.println("Profit of Company B: " + (-solution.getObjective(1)));
            System.out.println("Profit of Company C: " + (-solution.getObjective(2)));
            System.out.println("Strategies: " +
                    EncodingUtils.getInt(solution.getVariable(0)) + " " +
                    EncodingUtils.getInt(solution.getVariable(1)) + " " +
                    EncodingUtils.getInt(solution.getVariable(2)));
            System.out.println("------");
        }
    }
}
