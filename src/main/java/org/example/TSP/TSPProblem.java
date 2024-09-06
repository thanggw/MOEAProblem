package org.example.TSP;

import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.Permutation;

//Mục đích của TSP là tìm ra hành trình ngắn nhất mà một người bán hàng có thể đi qua
// tất cả các thành phố đúng một lần và trở lại điểm xuất phát.
public class TSPProblem implements Problem {
    private final double[][] distanceMatrix; //đại diện cho khoảng cách từ thành phố i đến thành phố j
    private final int numberOfCities;
    public TSPProblem(double[][] distanceMatrix){
        this.distanceMatrix=distanceMatrix;
        this.numberOfCities= distanceMatrix.length;
    }
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getNumberOfVariables() {
        return 1; //chỉ có 1 biến là hoán vị của các thành phố
    }

    @Override
    public int getNumberOfObjectives() {
        return 1; // chỉ có 1 mục tiêu là giảm thiểu khoảng cách
    }

    @Override
    public int getNumberOfConstraints() {
        return 0;
    }

    @Override
    public void evaluate(Solution solution) {
        int[] tour = ((Permutation) solution.getVariable(0)).toArray();// lấy hóan vị của các thành phố từ đối tượng solution
        double totalDistance = 0.0;
        //Tổng khoảng cách di chuyển
        for (int i=0;i<numberOfCities-1;i++){
            totalDistance+=distanceMatrix[tour[i]][tour[i+1]]; //Trong mỗi lần lặp, dòng này cộng thêm khoảng cách từ thành phố tour[i] đến thành phố tour[i + 1] vào totalDistance.
                                                              //  tour là một mảng chứa thứ tự của các thành phố trong lộ trình. Ví dụ, nếu tour = {0, 2, 1, 3}, nghĩa là lộ trình sẽ đi qua các thành phố theo thứ tự: 0 -> 2 -> 1 -> 3.
        }
        //Cộng khoảng cách từ thành phố cuối cùng trở lại
        totalDistance+=distanceMatrix[tour[numberOfCities-1]][tour[0]];
        //Tổng khoảng cách là mục tiêu cần giảm thiểu
        solution.setObjective(0,totalDistance);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(this.getNumberOfVariables(),this.getNumberOfObjectives());//tạo 1 đối tượng với số lượng biến và mục tiêu đã sác định
        solution.setVariable(0,new Permutation(numberOfCities));//gán 1 hoán vị ngẫu nhiên của các cities cho biến đầu tiên(duy nhất)
        return solution;
    }

    @Override
    public void close() {

    }
}
