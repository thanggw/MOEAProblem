package Matching;

import java.util.*;

public class GaleShapleyAlgorithm {
    Map<String, Person> setA;
    Map<String, Person> setB;
    Map<String, Person> setC;

    public GaleShapleyAlgorithm(Map<String, Person> setA, Map<String, Person> setB, Map<String, Person> setC) {
        this.setA = setA;
        this.setB = setB;
        this.setC = setC;
    }

    public void stableMatching() {
        // Giai đoạn 1: Ghép bệnh nhân (A) với bác sĩ (B)
        Queue<Person> freeSetA = new LinkedList<>(setA.values());
        Set<String> unmatchedA = new HashSet<>();

        while (!freeSetA.isEmpty()) {
            Person a = freeSetA.poll();
            boolean matchedB = false;

            while (!matchedB) {
                String bName = a.getNextPreferenceForB(0);  // Truyền padding = 0
                if (bName == null) {
                    break;
                }
                if (setB.containsKey(bName)) {
                    Person b = setB.get(bName);

                    if (b.currentMatchB == null) {
                        b.setMatchForB(a.name);
                        a.setMatchForB(bName);
                        matchedB = true;
                    } else if (b.prefersForB(a.name)) {
                        freeSetA.add(setA.get(b.currentMatchB));
                        b.setMatchForB(a.name);
                        a.setMatchForB(bName);
                        matchedB = true;
                    }
                }
            }

            // Nếu A không ghép được với ai trong B
            if (!matchedB) {
                unmatchedA.add(a.name);
                freeSetA.add(a);
            }
        }

        // Giai đoạn 2: Ghép cặp bệnh nhân-bác sĩ với bệnh viện (C)
        Queue<Person> freeSetPairs = new LinkedList<>(setA.values());
        while (!freeSetPairs.isEmpty()) {
            Person a = freeSetPairs.poll();
            boolean matchedC = false;

            String bName = a.currentMatchB;
            Person b = setB.get(bName);

            // Tạo danh sách ưu tiên cho cặp bệnh nhân-bác sĩ với bệnh viện
            List<String> combinedPreferences = new ArrayList<>(a.preferencesForC);
            combinedPreferences.addAll(b.preferencesForC); // Giả định là cặp này có thể chọn bệnh viện từ cả hai

            while (!matchedC) {
                String cName = a.getNextPreferenceForC(setB.size());  // Truyền padding là kích thước set B
                if (cName == null) {
                    break;
                }
                if (setC.containsKey(cName)) {
                    Person c = setC.get(cName);

                    if (c.currentMatchC == null) {
                        c.setMatchForC(a.name);
                        a.setMatchForC(cName);
                        matchedC = true;
                    } else if (c.prefersForC(a.name)) {
                        freeSetPairs.add(setA.get(c.currentMatchC));
                        c.setMatchForC(a.name);
                        a.setMatchForC(cName);
                        matchedC = true;
                    }
                }
            }

            // Nếu A không ghép được với C
            if (!matchedC) {
                unmatchedA.add(a.name);
                freeSetPairs.add(a);
            }
        }

        // In kết quả ghép cặp
        System.out.println("Kết quả ghép cặp:");
        for (Person a : setA.values()) {
            System.out.println(a.name + " ghép với B: " + (a.currentMatchB != null ? a.currentMatchB : "null")
                    + ", C: " + (a.currentMatchC != null ? a.currentMatchC : "null"));
        }

        if (!unmatchedA.isEmpty()) {
            System.out.println("Những phần tử không ghép cặp được: " + unmatchedA);
        }
    }
}
