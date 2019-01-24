import java.util.HashMap;
import java.util.Map;

public class Application {

	public static void main(String[] args) {
		// this is my data base
		Map<String, Map<String, Double>> data = new HashMap<>();
		String[] mAllItems;
		// items
		String item_candy = "candy";
		String item_dog = "dog";
		String item_cat = "cat";
		String item_war = "war";
		String item_food = "strange food";

		mAllItems = new String[] { item_candy, item_dog, item_cat, item_war, item_food };

		// I'm going to fill it in
		HashMap<String, Double> user1 = new HashMap<>();
		HashMap<String, Double> user2 = new HashMap<>();
		HashMap<String, Double> user3 = new HashMap<>();
		HashMap<String, Double> user4 = new HashMap<>();
		user1.put(item_candy, 1.0);
		user1.put(item_dog, 0.0);
		user1.put(item_war, 0.0);
		data.put("Bob", user1);
		user2.put(item_candy, 1.0);
		user2.put(item_cat, 1.0);
		user2.put(item_war, 0.0);
		data.put("Jane", user2);
		user3.put(item_candy, 1.0);
		user3.put(item_dog, 1.0);
		user3.put(item_cat, 0.0);
		user3.put(item_war, 1.0);
		data.put("Jo", user3);
		user4.put(item_candy, 0.0);
		user4.put(item_war, 1.0);
		user4.put(item_food, 0.0);
		data.put("StrangeJo", user4);

		// next, I create my predictor engine
		SlopeOne so = new SlopeOne(data, mAllItems);
		System.out.println("Here's the data I have accumulated...");
		so.printData();
		// then, I'm going to test it out...
		HashMap<String, Double> user = new HashMap<>();
		System.out.println("Ok, now we predict...");
		user.put(item_food, 1.0);
		System.out.println("Inputting...");
		SlopeOne.print(user);
		System.out.println("Getting...");
		SlopeOne.print(so.predict(user));
		//
		user.put(item_war, 1.0);
		System.out.println("Inputting...");
		SlopeOne.print(user);
		System.out.println("Getting...");
		SlopeOne.print(so.predict(user));
	}

}
