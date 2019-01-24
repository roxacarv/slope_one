import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InputData {

	protected static List<Recipe> items = Arrays.asList(new Recipe("Frango Assado"), new Recipe("Carne de Alface"), new Recipe("Maracujá com Limão"), new Recipe("Galinho de Bico Torto"), new Recipe("Jerimum com Abóbora"));

	public static Map<User, HashMap<Recipe, Double>> initializeData(int numberOfUsers) {
        Map<User, HashMap<Recipe, Double>> data = new HashMap<>();
        HashMap<Recipe, Double> newUser;
        Set<Recipe> newRecommendationSet;
        for (int i = 0; i < numberOfUsers; i++) {
            newUser = new HashMap<Recipe, Double>();
            newRecommendationSet = new HashSet<>();
            for (int j = 0; j < 3; j++) {
                newRecommendationSet.add(items.get((int) (Math.random() * 5)));
            }
            for (Recipe item : newRecommendationSet) {
                newUser.put(item, Math.random());
            }
            data.put(new User("User " + i), newUser);
        }
        return data;
    }
	
}
