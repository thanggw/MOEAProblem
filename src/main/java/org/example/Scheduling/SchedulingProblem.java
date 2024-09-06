package org.example.Scheduling;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.Permutation;
import org.moeaframework.problem.AbstractProblem;

//Sắp xếp các công việc sao cho hoàn thành tất cả các công việc trong thời gian ngắn nhất ,
//đồng thời tuân thủ các ràng buộc về tà nguyên(ví dụ: nhân lực, máy móc)
public class SchedulingProblem extends AbstractProblem {
    private int numberOfJobs;
    private int numberOfResource;
    private int[] jobDurations;
    private int[] resourceCapacities;
    public SchedulingProblem(int numberOfJobs,int numberOfResource, int[] jobDurations, int[] resourceCapacities){
        super(1,2);//1 biến và 2 mục tiêu
        this.numberOfJobs=numberOfJobs;
        this.numberOfResource=numberOfResource;
        this.jobDurations=jobDurations;  //thời gian
        this.resourceCapacities=resourceCapacities;

    }
    @Override
    public void evaluate(Solution solution) {
        // Biến đầu vào là chuỗi các công việc (được mã hóa thành hoán vị)
        Permutation jobs = (Permutation) solution.getVariable(0);

        // Giả sử mỗi job được gán ngẫu nhiên cho một tài nguyên
        int[] resourceAssignments = new int[numberOfJobs];
        int[] resourceUsage = new int[numberOfResource];

        int timeCompleted = 0;

        // Tính toán tổng thời gian hoàn thành  và mức sử dụng tài nguyên
        for (int i = 0; i < numberOfJobs; i++) {
            int job = jobs.get(i);
            int resource = i % numberOfResource;  // Phân chia công việc theo tài nguyên

            resourceAssignments[job] = resource;
            resourceUsage[resource] += jobDurations[job];

            // In thông tin chi tiết
            System.out.println("Job " + job + " assigned to Resource " + resource + " from " +  (resourceUsage[resource] - jobDurations[job]) + " to " + resourceUsage[resource]);
            timeCompleted = Math.max(timeCompleted, resourceUsage[resource]);
        }
        //Dặt giá trị mục tiêu
        solution.setObjective(0,timeCompleted);  //Mục tiêu1: thời gian hoàn thành
        solution.setObjective(1,calculateResource(resourceUsage)); //Mục tiêu2: Sử dụng tài nguyên
    }
    private double calculateResource(int[] resourceUsage){
        int totalUsage = 0;
        for(int usage: resourceUsage){
            totalUsage+=usage;
        }
        return totalUsage;
    }

    @Override
    public Solution newSolution() {
        Solution solution=new Solution(1,2);
        solution.setVariable(0,new Permutation(numberOfJobs));//Hoán vị công việc
        return solution;
    }
}
