package Matching;

import java.util.*;

public class GaleShapleyAlgorithm {
    Map<String, Person> setA;
    Map<String, Person> setB;
    Map<String, Person> setC;
    int sizeA, sizeB, sizeC;

    public GaleShapleyAlgorithm(Map<String, Person> setA, Map<String, Person> setB, Map<String, Person> setC) {
        this.setA = setA;
        this.setB = setB;
        this.setC = setC;
        this.sizeA = setA.size();
        this.sizeB = setB.size();
        this.sizeC = setC.size();
    }

    public void stableMatching() {
        Queue<Person> freeSetA = new LinkedList<>(setA.values());
        Set<String> unmatchedA = new HashSet<>();

        while (!freeSetA.isEmpty()) {
            Person a = freeSetA.poll();
            boolean matchedB = false;
            boolean matchedC = false;

            // Ghép cặp với B
            while (!matchedB) {
                String bName = a.getNextPreferenceForB(0);  // Truyền padding = 0 vì không cần bù đắp khi lấy từ set A
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

            // Ghép cặp với C
            while (!matchedC) {
                String cName = a.getNextPreferenceForC(sizeB);  // Truyền padding là kích thước set B
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
                        freeSetA.add(setA.get(c.currentMatchC));
                        c.setMatchForC(a.name);
                        a.setMatchForC(cName);
                        matchedC = true;
                    }
                }
            }

            // Nếu A không ghép được với ai trong B và C
            if (!matchedB || !matchedC) {
                unmatchedA.add(a.name);
                freeSetA.add(a);
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
