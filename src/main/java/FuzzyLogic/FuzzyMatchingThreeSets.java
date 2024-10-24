package FuzzyLogic;

import java.util.HashMap;
import java.util.Map;

public class FuzzyMatchingThreeSets {

    public static void main(String[] args) {
        // Create entities for set A
        Entity A1 = new Entity("A1");
        Entity A2 = new Entity("A2");
        Entity A3 = new Entity("A3");
        Entity A4 = new Entity("A4");
        Entity A5 = new Entity("A5");

        // Create entities for set B
        Entity B1 = new Entity("B1");
        Entity B2 = new Entity("B2");
        Entity B3 = new Entity("B3");
        Entity B4 = new Entity("B4");
        Entity B5 = new Entity("B5");

        // Create entities for set C
        Entity C1 = new Entity("C1");
        Entity C2 = new Entity("C2");
        Entity C3 = new Entity("C3");
        Entity C4 = new Entity("C4");
        Entity C5 = new Entity("C5");

        // Set fuzzy preferences for A1 to A5 for set B
        A1.addPreference("B1", 0.8);
        A1.addPreference("B2", 0.5);
        A1.addPreference("B3", 0.3);
        A1.addPreference("B4", 0.7);
        A1.addPreference("B5", 0.9);

        A2.addPreference("B1", 0.6);
        A2.addPreference("B2", 0.7);
        A2.addPreference("B3", 0.8);
        A2.addPreference("B4", 0.4);
        A2.addPreference("B5", 0.9);
        // A3, A4, A5 are same

        // Set fuzzy preferences for B1 to B5 for set A
        B1.addPreference("A1", 0.9);
        B1.addPreference("A2", 0.5);
        B1.addPreference("A3", 0.7);
        B1.addPreference("A4", 0.6);
        B1.addPreference("A5", 0.8);
        // B2, B3, B4, B5 are same

        // Set fuzzy preferences for A1 to A5 for set C
        A1.addPreference("C1", 0.6);
        A1.addPreference("C2", 0.7);
        A1.addPreference("C3", 0.9);
        A1.addPreference("C4", 0.4);
        A1.addPreference("C5", 0.8);

        // Set fuzzy preferences for C1 to C5 for set A
        C1.addPreference("A1", 0.6);
        C1.addPreference("A2", 0.8);
        C1.addPreference("A3", 0.7);
        C1.addPreference("A4", 0.9);
        C1.addPreference("A5", 0.5);

        // Set fuzzy preferences for B1 to B5 for set C
        B1.addPreference("C1", 0.8);
        B1.addPreference("C2", 0.5);
        B1.addPreference("C3", 0.6);
        B1.addPreference("C4", 0.9);
        B1.addPreference("C5", 0.7);


        // Set fuzzy preferences for C1 to C5 for set B
        C1.addPreference("B1", 0.9);
        C1.addPreference("B2", 0.6);
        C1.addPreference("B3", 0.8);
        C1.addPreference("B4", 0.7);
        C1.addPreference("B5", 0.5);

        // Perform the matching
        performFuzzyMatching(new Entity[]{A1, A2, A3, A4, A5}, new Entity[]{B1, B2, B3, B4, B5}, new Entity[]{C1, C2, C3, C4, C5});
    }

    public static void performFuzzyMatching(Entity[] setA, Entity[] setB, Entity[] setC) {
        // Map to store A-B matches
        Map<String, String> ABMatches = new HashMap<>();

        // Phase 1: Match A and B based on fuzzy preferences
        for (Entity a : setA) {
            String bestMatchB = null;
            double highestPreference = -1;

            for (Entity b : setB) {
                double preferenceAForB = a.getPreference(b.name);
                double preferenceBForA = b.getPreference(a.name);

                // Calculate fuzzy matching score (average of both preferences)
                double matchScore = (preferenceAForB + preferenceBForA) / 2;

                if (matchScore > highestPreference) {
                    highestPreference = matchScore;
                    bestMatchB = b.name;
                }
            }

            if (bestMatchB != null) {
                ABMatches.put(a.name, bestMatchB);
            }
        }

        // Print the A-B matches
        System.out.println("Phase 1 - Matches between A and B:");
        ABMatches.forEach((a, b) -> System.out.println(a + " is matched with " + b));

        // Phase 2: Match A-B pairs with C based on fuzzy preferences
        Map<String, String> ABCMatches = new HashMap<>();

        for (Map.Entry<String, String> abPair : ABMatches.entrySet()) {
            String a = abPair.getKey();
            String b = abPair.getValue();
            String bestMatchC = null;
            double highestPreference = -1;

            for (Entity c : setC) {
                double preferenceAForC = getEntityByName(setA, a).getPreference(c.name);
                double preferenceBForC = getEntityByName(setB, b).getPreference(c.name);
                double preferenceCForA = c.getPreference(a);
                double preferenceCForB = c.getPreference(b);

                // Calculate fuzzy matching score for A-B pair with C (average of A's, B's, and C's preferences)
                double matchScore = (preferenceAForC + preferenceBForC + preferenceCForA + preferenceCForB) / 4;

                if (matchScore > highestPreference) {
                    highestPreference = matchScore;
                    bestMatchC = c.name;
                }
            }

            if (bestMatchC != null) {
                ABCMatches.put(a + "-" + b, bestMatchC);
            }
        }

        // Print the final matches A-B-C
        System.out.println("Phase 2 - Matches between A-B pairs and C:");
        ABCMatches.forEach((ab, c) -> System.out.println(ab + " is matched with " + c));
    }

    // Helper method to get an entity by name
    public static Entity getEntityByName(Entity[] entities, String name) {
        for (Entity entity : entities) {
            if (entity.name.equals(name)) {
                return entity;
            }
        }
        return null;
    }
}
