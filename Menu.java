import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * represents a menu 
 * @author AlisonYang
 *
 */
public class Menu {
	private static Menu instance;
	private ArrayList<Food> menu;

	/**
	 * constructor for a menu
	 * singleton design pattern
	 */

	public Menu()
	{

		menu = new ArrayList<Food>();
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter the file name of the menu.");  // reading file
		String fileName = in.nextLine();
		File inFile = new File(fileName);
		Scanner scan;
		try {
			scan = new Scanner(inFile);
			while (scan.hasNext())
			{

				String name = scan.nextLine();  
				String price = scan.nextLine();
				Food newItem = new Food(name, price);  // creating food item from scanned info
				menu.add(newItem); // adds to arraylist

			}
		} catch (FileNotFoundException e) {
			System.out.println("Incorrect file name. Program exiting. ");
			System.exit(0);   // exits if the file is not correct

		}
	}


	/**
	 * gets an instance of the menu (part of singleton design pattern)
	 * @return
	 */
	public static Menu getInstance() 
	{
		if(instance == null)
			instance = new Menu();
		return instance;
	}
	
	/**
	 * returns the array list of foods
	 * @return
	 */
	public ArrayList<Food> getMenu() {
		return menu;
	}


}
