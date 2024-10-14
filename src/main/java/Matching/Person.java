package Matching;

import java.util.List;

public class Person {
    String name;
    List<String> preferencesForB;
    List<String> preferencesForC;
    String currentMatchB;
    String currentMatchC;
    int preferenceIndexB;
    int preferenceIndexC;

    public Person(String name, List<String> preferencesForB, List<String> preferencesForC) {
        this.name = name;
        this.preferencesForB = preferencesForB;
        this.preferencesForC = preferencesForC;
        this.currentMatchB = null;
        this.currentMatchC = null;
        this.preferenceIndexB = 0;
        this.preferenceIndexC = 0;
    }

    // Lấy danh sách ưu tiên cho B với padding
    public String getNextPreferenceForB(int padding) {
        if (preferenceIndexB < preferencesForB.size()) {
            return preferencesForB.get(preferenceIndexB++);
        }
        return null;
    }

    // Lấy danh sách ưu tiên cho C với padding
    public String getNextPreferenceForC(int padding) {
        if (preferenceIndexC < preferencesForC.size()) {
            return preferencesForC.get(preferenceIndexC++);
        }
        return null;
    }

    public boolean prefersForB(String newPerson) {
        return preferencesForB.indexOf(newPerson) < preferencesForB.indexOf(currentMatchB);
    }

    public boolean prefersForC(String newPerson) {
        return preferencesForC.indexOf(newPerson) < preferencesForC.indexOf(currentMatchC);
    }

    public void setMatchForB(String match) {
        this.currentMatchB = match;
    }

    public void setMatchForC(String match) {
        this.currentMatchC = match;
    }
}
