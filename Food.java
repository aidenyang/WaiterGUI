/**
 * represents a food item
 * 
 * @author AlisonYang
 *
 */
public class Food {
	
	private String name;
	private String price;

	/**
	 * constructor for food object
	 * @param name
	 * @param price
	 */
	public Food(String name, String price) {
		this.name = name;
		this.price = price;
	}

	/**
	 * returns name of food
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * returns price of food as a string
	 * @return
	 */
	public String getPrice() {
		return price;
	}

	
	

}
