package Matching;

import java.util.*;

public class StableMatchingWithThreeSets {
    public static void main(String[] args) {
        int setSize = 10;  // Tổng số phần tử của mỗi set

        // Khởi tạo các danh sách ưu tiên giả lập cho A, B, C
        Map<String, Person> setA = new HashMap<>();
        Map<String, Person> setB = new HashMap<>();
        Map<String, Person> setC = new HashMap<>();

        // Giả lập dữ liệu cho mỗi set
        for (int i = 1; i <= setSize; i++) {
            List<String> preferencesForB = new ArrayList<>();
            List<String> preferencesForC = new ArrayList<>();

            // Tạo danh sách ưu tiên cho B và C
            for (int j = 1; j <= setSize; j++) {
                preferencesForB.add("B" + j);
                preferencesForC.add("C" + j);
            }

            // Shuffle để giả lập danh sách ngẫu nhiên
            Collections.shuffle(preferencesForB);
            Collections.shuffle(preferencesForC);

            // Phân biệt danh sách ưu tiên cho Set A, B và C
            setA.put("A" + i, new Person("A" + i, preferencesForB, preferencesForC));
            setB.put("B" + i, new Person("B" + i, preferencesForC, preferencesForB));  // Bác sĩ cũng có danh sách ưu tiên
            setC.put("C" + i, new Person("C" + i, preferencesForB, preferencesForC));  // C cũng có danh sách ưu tiên
        }

        // Tạo đối tượng GaleShapleyAlgorithm và thực hiện ghép cặp
        GaleShapleyAlgorithm gsa = new GaleShapleyAlgorithm(setA, setB, setC);
        gsa.stableMatching();
    }
}
