package FuzzyLogic;
import java.util.HashMap;
import java.util.Map;

public class Entity {
    String name;
    Map<String, Double> preferences;  // Fuzzy preference scores

    public Entity(String name) {
        this.name = name;
        this.preferences = new HashMap<>();
    }

    // Add preference score for an entity
    public void addPreference(String entityName, double score) {
        preferences.put(entityName, score);
    }

    // Get preference score for an entity
    public double getPreference(String entityName) {
        return preferences.getOrDefault(entityName, 0.0);
    }

    @Override
    public String toString() {
        return name;
    }
}