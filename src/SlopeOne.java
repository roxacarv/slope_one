import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SlopeOne {
	
	private static Map<Recipe, Map<Recipe, Double>> difference = new HashMap<>();
	private static Map<Recipe, Map<Recipe, Integer>> frequence = new HashMap<>();
	private static Map<User, HashMap<Recipe, Double>> inputData;
	private static Map<User, HashMap<Recipe, Double>> outputData = new HashMap<>();
	
	public static void slopeOne(int numberOfUsers) {
		inputData = InputData.initializeData(numberOfUsers);
		System.out.println("Slope One - Before the Prediction\n");
        buildDifferencesMatrix(inputData);
        System.out.println("\nSlope One - With Predictions\n");
        predict(inputData);
	}
	
	private static void buildDifferencesMatrix(Map<User, HashMap<Recipe, Double>> data) {
        for (HashMap<Recipe, Double> user : data.values()) {
            for (Entry<Recipe, Double> e : user.entrySet()) {
                if (!difference.containsKey(e.getKey())) {
                    difference.put(e.getKey(), new HashMap<Recipe, Double>());
                    frequence.put(e.getKey(), new HashMap<Recipe, Integer>());
                }
                for (Entry<Recipe, Double> e2 : user.entrySet()) {
                    int oldCount = 0;
                    if (frequence.get(e.getKey()).containsKey(e2.getKey())) {
                        oldCount = frequence.get(e.getKey()).get(e2.getKey()).intValue();
                    }
                    double oldDiff = 0.0;
                    if (difference.get(e.getKey()).containsKey(e2.getKey())) {
                        oldDiff = difference.get(e.getKey()).get(e2.getKey()).doubleValue();
                    }
                    double observedDiff = e.getValue() - e2.getValue();
                    frequence.get(e.getKey()).put(e2.getKey(), oldCount + 1);
                    difference.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
                }
            }
        }
        
        for (Recipe j : difference.keySet()) {
            for (Recipe i : difference.get(j).keySet()) {
                double oldValue = difference.get(j).get(i).doubleValue();
                int count = frequence.get(j).get(i).intValue();
                difference.get(j).put(i, oldValue / count);
            }
        }
        printData(data);
    }
	
	private static void predict(Map<User, HashMap<Recipe, Double>> data) {
        HashMap<Recipe, Double> uPred = new HashMap<Recipe, Double>();
        HashMap<Recipe, Integer> uFreq = new HashMap<Recipe, Integer>();
        for (Recipe j : difference.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }
        for (Entry<User, HashMap<Recipe, Double>> e : data.entrySet()) {
            for (Recipe j : e.getValue().keySet()) {
                for (Recipe k : difference.keySet()) {
                    try {
                        double predictedValue = difference.get(k).get(j).doubleValue() + e.getValue().get(j).doubleValue();
                        double finalValue = predictedValue * frequence.get(k).get(j).intValue();
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + frequence.get(k).get(j).intValue());
                    } catch (NullPointerException e1) {
                    }
                }
            }
            HashMap<Recipe, Double> clean = new HashMap<Recipe, Double>();
            for (Recipe j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j).doubleValue() / uFreq.get(j).intValue());
                }
            }
            for (Recipe j : InputData.items) {
                if (e.getValue().containsKey(j)) {
                    clean.put(j, e.getValue().get(j));
                } else {
                    clean.put(j, -1.0);
                }
            }
            outputData.put(e.getKey(), clean);
        }
        printData(outputData);
    }

    private static void printData(Map<User, HashMap<Recipe, Double>> data) {
        for (User user : data.keySet()) {
            System.out.println(user.getName() + ":");
            print(data.get(user));
        }
    }
    
    private static void print(HashMap<Recipe, Double> hashMap) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        for (Recipe j : hashMap.keySet()) {
            System.out.println(" " + j.getName() + " --> " + formatter.format(hashMap.get(j).doubleValue()));
        }
    }
}
