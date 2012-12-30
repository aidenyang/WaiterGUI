import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;
/**
 * MainView, GUI that emulates a touchscreen used by waiters in restaurants
 * @author Alison Yang
 *
 */
public class MainView extends JFrame 
{
	private Menu myMenu;
	private String receipt = "";
	final JTextArea receiptBox = new JTextArea();
	private double total = 0.00;
	JTextArea runningTotal = new JTextArea();

	/**
	 * constructor for GUI
	 */
	public MainView()
	{
		initialize();
		setPreferredSize(new Dimension(1000,1000));
		setTitle("Menu GUI");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}

	/**
	 * Initializes the main panel with its components
	 */
	public void initialize()
	{
		JPanel mainPanel = (JPanel)getContentPane();
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, getFoodPanel(), getReceiptPanel());  // places components in split panel
		splitPane.setDividerLocation(420); // sets where divider is located
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(splitPane, BorderLayout.CENTER);
	}

/**
 * returns food buttons
 * @return a scroll panel containing the buttons with food names and prices
 */
	public JScrollPane getFoodPanel()
	{
		runningTotal.setEditable(false);
		final JPanel foodPanel = new JPanel(); 
		foodPanel.setLayout(new GridLayout(0, 2, 5, 5));
		initMenu(); // gets instance of the menu
		ArrayList<Food> menuItems = myMenu.getMenu(); 
		for (final Food f: menuItems)
		{
			JButton button = new JButton(f.getName() + "--" + f.getPrice());  // creates food buttons
			button.setPreferredSize(new Dimension(270,85));
			button.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {  // when button pressed

					receipt  = receipt + f.getName() + "--" + f.getPrice() + "\n";  // receipt string with new order
					receiptBox.setText(receipt);
					DecimalFormat format = new DecimalFormat("#.##");  // formats the number
					total = total + Double.parseDouble(f.getPrice());  // adds to running total
					total =  Double.valueOf(format.format(total));  
					runningTotal.setText("Total : " + Double.toString(total));  // sets new running total
					repaint();
				}
			});
			foodPanel.add(button);
		}
		JScrollPane foodScroll = new JScrollPane(foodPanel);  // scrollpane with food buttons

		return foodScroll;  // return scroll pane 
	}
	/**
	 * returns receipt panel
	 * @return   a scrollpane with the receipt panel
	 */
	public JScrollPane getReceiptPanel()
	{
		JPanel receiptPanel = new JPanel();
		receiptPanel.setBackground(Color.PINK);
		receiptPanel.setLayout(new BorderLayout());
		JLabel label = new JLabel("Receipt");   // header
		JPanel bottom = new JPanel(new BorderLayout());
		JButton clearButton = new JButton("Clear Order");  
		clearButton.setPreferredSize(new Dimension(270,85));
		clearButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				receipt = "";  // reset the receipt
				total = 0;  // reset the running total
				runningTotal.setText("Total : 0.00");   // shows 0 dollars running total
				receiptBox.setText(receipt);
				repaint();
			}

		});
		JButton orderButton = new JButton("Place Order");
		orderButton.setPreferredSize(new Dimension(270,85));
		orderButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try{
					java.util.Date date= new java.util.Date();   // start appending to file
					String data = receipt + "\n" + "Total : " + total + "\n" + date.toString() + "\n"  + "\n";

					File file = new File("orders.txt");

					//if file doesnt exists, then create it
					if(!file.exists()){
						file.createNewFile();
					}

					//true = append file
					FileWriter fileWriter = new FileWriter(file.getName(),true);
					BufferedWriter bufferWritter = new BufferedWriter(fileWriter);
					bufferWritter.write(data);
					bufferWritter.close();  // finish apppending to file
					JOptionPane.showMessageDialog(receiptBox, "Order placed, thank you.");   
					

				}catch(IOException a){
					a.printStackTrace();
				}

			}
		});
		bottom.add(runningTotal, BorderLayout.NORTH);
		bottom.add(orderButton, BorderLayout.CENTER);
		bottom.add(clearButton, BorderLayout.SOUTH);
		receiptPanel.add(bottom, BorderLayout.SOUTH);
		receiptPanel.add(label, BorderLayout.NORTH);
		JScrollPane orderScroll = new JScrollPane(receiptBox);
		receiptPanel.add(orderScroll, BorderLayout.CENTER);
		JScrollPane receiptScroll  = new JScrollPane(receiptPanel);
		return receiptScroll;
	}

	/**
	 * instance of menu
	 */
	public void initMenu()
	{
		myMenu = Menu.getInstance();
	}
}


