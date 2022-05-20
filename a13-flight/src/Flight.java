/**
 * Stores and lists customer names and airline points in a 2D array.
 * 
 * modified     20220520
 * date         20220518
 * @filename    Flight.java
 * @author      Alvin Chan, Evan Shizas
 * @version     1.0.0
 * @see         A13 - Flight
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import java.util.*;

public class Flight extends JFrame {

	private JPanel contentPane;
	private JTextField firstNameIn;
	private JTextField lastNameIn;
	private JTextField dialogBox;
	private JTextArea listCustomerPointsBox;
	private JScrollPane scrollListCustomerPointsBox;
	private JSpinner loyaltyIDNumber;
	private JTextField week1In;
	private JTextField week2In;
	private JTextField week3In;
	private JTextField week4In;
	private JButton add;
	private JButton remove;
	private JButton reset;
	private JButton update;
	private JButton sort;

	final int MAX_ID_RANGE = 400000000, MAX_ARRAY_A = 1000, MAX_ARRAY_B = 8; // MAX_ARRAY_A & MAX_ARRAY_B -> 2D Array dimensions.

	String[][] customerNamesPoints = new String[MAX_ARRAY_A][MAX_ARRAY_B], replacementArray = new String[MAX_ARRAY_A][MAX_ARRAY_B];

	// customerCount acts as an array pointer and counter.
	int customerCount = 0, errorCode = 0, pointsEntries = 0, customerPointsEntries = 0, customerPosition = 0, customerPoints = 0, totalPoints = 0;

	boolean removePress = false, updatePress = false, sortPress = false, allowDebug = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Flight frame = new Flight();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Flight() { // GUI Constructor
		setBackground(Color.WHITE);
		setResizable(false);
		setTitle("A13 - Flight");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 687);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel title = new JLabel("Airline Points");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(Color.BLUE);
		title.setFont(new Font("Tahoma", Font.BOLD, 30));
		title.setBackground(Color.WHITE);
		title.setBounds(0, 0, 488, 41);
		contentPane.add(title);

		JLabel firstName = new JLabel("First Name:");
		firstName.setBounds(10, 55, 85, 22);
		contentPane.add(firstName);

		JLabel lastName = new JLabel("Last Name:");
		lastName.setBounds(250, 55, 80, 22);
		contentPane.add(lastName);

		JLabel week1 = new JLabel("Week 1:");
		week1.setBounds(10, 119, 55, 22);
		contentPane.add(week1);

		JLabel week2 = new JLabel("Week 2:");
		week2.setBounds(10, 152, 55, 22);
		contentPane.add(week2);

		JLabel week3 = new JLabel("Week 3:");
		week3.setBounds(10, 185, 55, 22);
		contentPane.add(week3);

		JLabel week4 = new JLabel("Week 4:");
		week4.setBounds(10, 218, 55, 22);
		contentPane.add(week4);

		JLabel enterBelowLbl = new JLabel("Enter Points Below:");
		enterBelowLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		enterBelowLbl.setBounds(10, 92, 178, 20);
		contentPane.add(enterBelowLbl);

		JLabel uniqueID = new JLabel("Loyalty #:");
		uniqueID.setHorizontalAlignment(SwingConstants.LEFT);
		uniqueID.setBackground(Color.WHITE);
		uniqueID.setBounds(250, 88, 65, 20);
		contentPane.add(uniqueID);

		week1In = new JTextField();
		week1In.setHorizontalAlignment(SwingConstants.RIGHT);
		week1In.setColumns(10);
		week1In.setBounds(68, 119, 65, 22);
		contentPane.add(week1In);

		week2In = new JTextField();
		week2In.setHorizontalAlignment(SwingConstants.RIGHT);
		week2In.setColumns(10);
		week2In.setBounds(68, 152, 65, 22);
		contentPane.add(week2In);

		week3In = new JTextField();
		week3In.setHorizontalAlignment(SwingConstants.RIGHT);
		week3In.setColumns(10);
		week3In.setBounds(68, 185, 65, 22);
		contentPane.add(week3In);

		week4In = new JTextField();
		week4In.setHorizontalAlignment(SwingConstants.RIGHT);
		week4In.setColumns(10);
		week4In.setBounds(68, 218, 65, 22);
		contentPane.add(week4In);

		firstNameIn = new JTextField();
		firstNameIn.setBounds(88, 55, 154, 22);
		contentPane.add(firstNameIn);
		firstNameIn.setColumns(10);

		lastNameIn = new JTextField();
		lastNameIn.setBounds(324, 55, 154, 22);
		contentPane.add(lastNameIn);
		lastNameIn.setColumns(10);

		loyaltyIDNumber = new JSpinner();
		loyaltyIDNumber.setModel(new SpinnerNumberModel(0, 0, MAX_ID_RANGE, 1));
		loyaltyIDNumber.setForeground(Color.WHITE);
		loyaltyIDNumber.setBackground(Color.WHITE);
		loyaltyIDNumber.setBounds(324, 88, 154, 20);
		contentPane.add(loyaltyIDNumber);

		add = new JButton("Add");
		add.setBounds(250, 119, 110, 23);
		contentPane.add(add);
		add.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addActionPerformed(evt);
			}
		});

		remove = new JButton("Remove");
		remove.setEnabled(false);
		remove.setBounds(368, 119, 110, 23);
		contentPane.add(remove);
		remove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removeActionPerformed(evt);
			}
		});

		update = new JButton("Update");
		update.setEnabled(false);
		update.setBounds(250, 152, 228, 23);
		contentPane.add(update);
		update.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updateActionPerformed(evt);
			}
		});

		sort = new JButton("Sort");
		sort.setEnabled(false);
		sort.setBounds(250, 185, 228, 23);
		contentPane.add(sort);
		sort.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sortActionPerformed(evt);
			}
		});

		reset = new JButton("Reset");
		reset.setBounds(250, 218, 228, 23);
		contentPane.add(reset);
		reset.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				resetActionPerformed(evt);
			}
		});

		dialogBox = new JTextField();
		dialogBox.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, 12));
		dialogBox.setEditable(false);
		dialogBox.setBackground(Color.WHITE);
		dialogBox.setBounds(10, 253, 468, 22);
		contentPane.add(dialogBox);
		dialogBox.setColumns(10);

		listCustomerPointsBox = new JTextArea();
		listCustomerPointsBox.setLineWrap(true);
		listCustomerPointsBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		listCustomerPointsBox.setEditable(false);
		contentPane.add(listCustomerPointsBox);

		scrollListCustomerPointsBox = new JScrollPane(listCustomerPointsBox);
		scrollListCustomerPointsBox.setBounds(10, 286, 468, 354);
		scrollListCustomerPointsBox.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollListCustomerPointsBox);

		arrayInitialize();
	}

	private void resetActionPerformed(java.awt.event.ActionEvent evt) {
		int confirmOption = JOptionPane.showConfirmDialog (null, "All data will be lost!\n\nWish to continue?","RESET WARNING", JOptionPane.YES_NO_OPTION);

		if (confirmOption == JOptionPane.YES_OPTION) {
			customerNamesPoints = new String[MAX_ARRAY_A][MAX_ARRAY_B];
			customerCount = 0; 
			errorCode = 0; 
			pointsEntries = 0;
			customerPointsEntries = 0;
			customerPoints = 0;
			totalPoints = 0;
			
			dialogBox.setText("");
			listCustomerPointsBox.setText("");
			firstNameIn.setText("");
			lastNameIn.setText("");
			week1In.setText("");
			week2In.setText("");
			week3In.setText("");
			week4In.setText("");
			loyaltyIDNumber.setModel(new SpinnerNumberModel(0, 0, MAX_ID_RANGE, 1));

			add.setEnabled(true);
			remove.setEnabled(false);
			update.setEnabled(false);
			sort.setEnabled(false);
			firstNameIn.setEnabled(true);
			lastNameIn.setEnabled(true);
			week1In.setEnabled(true);
			week2In.setEnabled(true);
			week3In.setEnabled(true);
			week4In.setEnabled(true);

			sortPress = false;

			arrayInitialize();
		}

		debugConsole();
	}

	private void addActionPerformed(java.awt.event.ActionEvent evt) {
		if (!validInput()) {
			errorResponse();
		}

		else {		
			dialogBox.setText("SUCCESSFULLY ADDED CUSTOMER AND POINTS!");

			if (customerCount == MAX_ARRAY_A - 1)
				add.setEnabled(false);
			if (customerCount == 1)
				sort.setEnabled(true);
			if (customerCount == 0) {
				remove.setEnabled(true);
				update.setEnabled(true);
			}	
			
			customerNamesPoints[customerCount][0] = firstNameIn.getText() + " " + lastNameIn.getText();
			customerNamesPoints[customerCount][1] = week1In.getText();
			customerNamesPoints[customerCount][2] = week2In.getText();
			customerNamesPoints[customerCount][3] = week3In.getText();
			customerNamesPoints[customerCount][4] = week4In.getText();
			customerNamesPoints[customerCount][6] = loyaltyIDNumber.getValue().toString();
			customerNamesPoints[customerCount][7] = lastNameIn.getText();

			customerCount++;
		
			customerPointsCalculation();
			totalPointsCalculation();
			
			loyaltyIDNumber.setModel(new SpinnerNumberModel(0, 0, MAX_ID_RANGE, 1));
		}

		debugConsole();
	}

	private void removeActionPerformed(java.awt.event.ActionEvent evt) {
		removePress = true;

		if (!validInput()) {
			errorResponse();
		}

		else {		
			replacementArray = new String[MAX_ARRAY_A][MAX_ARRAY_B];
			arrayInitialize();

			boolean matchID = false;

			String studentRemove = loyaltyIDNumber.getValue().toString();

			if (studentRemove.equals("0")) { // Removes the most recent entry if no specific loyalty ID is selected.
				dialogBox.setText("REMOVING MOST RECENT ENTRY...");

				if (sortPress)
					dialogBox.setText("REMOVING MOST RECENT CUSTOMER...");

				customerCount--;

				// Resets last entry to default value.
				for (int i = 0; i < customerNamesPoints[customerCount].length; i++) {
					customerNamesPoints[customerCount][i] = "*";
				}		

				// Creates new 2D array containing all existing entries.
				for (int i = 0; i < customerNamesPoints.length; i++) {
					for (int j = 0; j < customerNamesPoints[i].length; j++) {
						if (customerNamesPoints[i][j] != "*") {
							replacementArray[i][j] = customerNamesPoints[i][j];
						}
					}
				}

				// Copies 2D array.
				customerNamesPoints = replacementArray;
			}

			else { // Performed if a specific customer number is selected in "Loyalty #:".
				dialogBox.setText("REMOVING MATCHING LOYALTY ID...");

				// Finds entry with the same ID. Removes values associated with entry.
				for (int i = 0; i < MAX_ARRAY_A; i++) {
					if (customerNamesPoints[i][6].equals(studentRemove)) {
						for (int j = 0; j < MAX_ARRAY_B; j++) {
							customerNamesPoints[i][j] = "*";
						}

						matchID = true;
					}
				}

				if (!matchID) {
					errorCode = 8;
					errorResponse();
				}

				// Loop to remove and resort 2D array values, leaving no empty array positions between 2 filled.
				for (int i = 0; i < MAX_ARRAY_B; i++) {
					for (int j = 0; j < MAX_ARRAY_A; j++) {
						if (customerNamesPoints[j][i].equals("*")) {
							for (int k = j+1; k < MAX_ARRAY_A; k++) {
								if (!customerNamesPoints[k][i].equals("*")) {
									customerNamesPoints[j][i] = customerNamesPoints[k][i];
									customerNamesPoints[k][i] = "*";
									break;
								}
							}
						}
					}
				}

				// Reconfigures customerCount to the correct number of entries.
				for (int i = 0; i < MAX_ARRAY_A; i++) {
					if (customerNamesPoints[i][0].equals("*")) {
						customerCount = i;
						break;
					}
				}
			}

			if (customerCount < MAX_ARRAY_A)
				add.setEnabled(true);
			if (customerCount == 1)
				sort.setEnabled(false);
			if (customerCount == 0) {
				remove.setEnabled(false);
				update.setEnabled(false);
				sortPress = false;
			}

			week1In.setText("");
			week2In.setText("");
			week3In.setText("");
			week4In.setText("");
		}

		if (customerCount == 0)
			listCustomerPointsBox.setText("");
		else
			totalPointsCalculation();
		
		loyaltyIDNumber.setModel(new SpinnerNumberModel(0, 0, MAX_ID_RANGE, 1));

		debugConsole();
	}

	private void updateActionPerformed(java.awt.event.ActionEvent evt) {
		updatePress = true;

		if (!validInput()) {
			errorResponse();
		}

		else {	
			boolean matchID = false;

			int matchPosition = 0;

			dialogBox.setText("UPDATING CUSTOMER ENTRY...");

			for (int i = 0; i < MAX_ARRAY_A; i++) {
				if (customerNamesPoints[i][6].equals(loyaltyIDNumber.getValue().toString())) {
					matchPosition = i;
					matchID = true;
					break;
				}
			}

			if (!matchID) {
				errorCode = 8;
				errorResponse();
			}

			else {
				customerNamesPoints[matchPosition][1] = week1In.getText();
				customerNamesPoints[matchPosition][2] = week2In.getText();
				customerNamesPoints[matchPosition][3] = week3In.getText();
				customerNamesPoints[matchPosition][4] = week4In.getText();
				
				customerPointsCalculation();
				totalPointsCalculation();
			}
		}

		updatePress = false;
		
		loyaltyIDNumber.setModel(new SpinnerNumberModel(0, 0, MAX_ID_RANGE, 1));

		debugConsole();
	}

	private void sortActionPerformed(java.awt.event.ActionEvent evt) {
		sortPress = true;

		ArrayList<String> lastNameList = new ArrayList<String>();

		String[][] sortArray = new String[MAX_ARRAY_A + 1][MAX_ARRAY_B];

		dialogBox.setText("SORTING ENTRIES BY LAST NAME...");

		// For-loop to add last names and customer IDs to lastNameList.
		for (int i = 0; i < MAX_ARRAY_A; i++) {
			if (!customerNamesPoints[i][6].equals("*")) {
				lastNameList.add(customerNamesPoints[i][7] + customerNamesPoints[i][6]);
			}
		}

		// Sorts by alphabetical order.
		Collections.sort(lastNameList);

		// For-loop to initialize sortArray[][].
		for (int i = 0; i < MAX_ARRAY_A; i++) {
			for (int j = 0; j < MAX_ARRAY_B; j++) {
				sortArray[i][j] = customerNamesPoints[i][j];
			}
		}

		// For-loop to reorganize array, relative to sorted lastNameList.
		for (int i = 0; i < lastNameList.size(); i++) {			
			for (int j = 0; j < MAX_ARRAY_A; j++) {
				if (lastNameList.get(i).equals(sortArray[j][7] + sortArray[j][6])) {
					for (int k = 0; k < MAX_ARRAY_B; k++) {
						sortArray[MAX_ARRAY_A][k] = sortArray[i][k];
						customerNamesPoints[i][k] = sortArray[j][k];
					}
				}
			}
		}
		
		loyaltyIDNumber.setModel(new SpinnerNumberModel(0, 0, MAX_ID_RANGE, 1));

		arrayWriteOut();

		debugConsole();
	}

	public void customerPointsCalculation() {
		customerPoints = 0;
		customerPointsEntries = 0;
		
		for (int i = 0; i < MAX_ARRAY_A; i++) {
			if (customerNamesPoints[i][6].equals(loyaltyIDNumber.getValue().toString())) {
				customerPosition = i;
				break;
			}
		}

		// Loop to add values of most recent/selected customer entry.
		for (int j = 1; j < 5; j++) {
			if (!customerNamesPoints[customerPosition][j].equals("") && !customerNamesPoints[customerPosition][j].equals("*") && !customerNamesPoints[customerPosition][j].equals(null)) {
				customerPoints += Integer.parseInt(customerNamesPoints[customerPosition][j]);
				customerPointsEntries++;
			}
		}

		if (customerPointsEntries == 0) // If no entries exist for customer.
			customerPoints = -1;

		customerNamesPoints[customerPosition][5] = Integer.toString(customerPoints);
	}

	public void totalPointsCalculation() {
		totalPoints = 0;
		pointsEntries = 0;

		// Loop to sum all points entries and count how many exist.
		for (int i = 0; i < MAX_ARRAY_A; i++) {				
			for (int j = 1; j < 5; j++) {
				if (!customerNamesPoints[i][j].equals("") && !customerNamesPoints[i][j].equals("*") && !customerNamesPoints[i][j].equals(null)) {
					totalPoints += Integer.parseInt(customerNamesPoints[i][j]);
					pointsEntries++;
				}
			}
		}

		// If no entries exist.
		if (pointsEntries == 0)
			totalPoints = -1; // Value produces a different GUI result than normal.

		arrayWriteOut();
	}

	public boolean validInput() { // Input validation
		try {
			boolean skip1 = false, skip2 = false, skip3 = false, skip4 = false, uniqueRandomID = false;

			errorCode = 7; // If try{} fails at parsing String values.

			String firstNameTest = firstNameIn.getText().toLowerCase().replace(" ", ""), lastNameTest = lastNameIn.getText().toLowerCase().replace(" ", "");

			errorCode = 6; // If try{} fails at parsing double values.

			double testIn1 = 0, testIn2 = 0, testIn3 = 0, testIn4 = 0;
			
			int testID = 0;

			/* Next 4 if-else statements allow for non-filled points text-fields to exist.
			   They are counted as N/A and have no value. */

			if (week1In.getText().equals("")) {
				skip1 = true;
			}

			else {
				skip1 = false;
				testIn1 = Double.parseDouble(week1In.getText());
			}

			if (week2In.getText().equals("")) {
				skip2 = true;
			}

			else {
				skip2 = false;
				testIn2 = Double.parseDouble(week2In.getText());
			}

			if (week3In.getText().equals("")) {
				skip3 = true;
			}

			else {
				skip3 = false;
				testIn3 = Double.parseDouble(week3In.getText());
			}

			if (week4In.getText().equals("")) {
				skip4 = true;
			}

			else {
				skip4 = false;
				testIn4 = Double.parseDouble(week4In.getText());
			}

			// Prevents there being no first name in entry.
			if (firstNameTest.equals("") && !removePress && !updatePress || firstNameTest.equals(null) && !removePress && !updatePress) {
				errorCode = 5;
				return false;
			}

			// Checks each character to verify that there are only letters.
			for (int i = 0; i < firstNameTest.length(); i++) {
				if (firstNameTest.charAt(i) < 97 || firstNameTest.charAt(i) > 122) {
					errorCode = 4;
					return false;
				}
			}

			// Checks each character to verify that there are only letters.
			for (int i = 0; i < lastNameTest.length(); i++) {
				if (lastNameTest.charAt(i) < 97 || lastNameTest.charAt(i) > 122 && !lastNameTest.equals("")) {
					errorCode = 3;
					return false;
				}
			}
			
			// Creates a unique ID if "0" is inputed.
			if (loyaltyIDNumber.getValue().toString().equals("0") && !removePress && !updatePress) {
				while (!uniqueRandomID) {
					uniqueRandomID = true;
					
					testID = (int)((Math.random() * MAX_ID_RANGE) + 1);
					
					for (int i = 0; i < MAX_ARRAY_A; i++) {
						if (customerNamesPoints[i][6].equals("*"))
							break;
						
						else if (testID == Integer.parseInt(customerNamesPoints[i][6])) {
							uniqueRandomID = false;
							break;
						}
					}
				}
				
				loyaltyIDNumber.setValue(testID);
			}
			
			// Checks unique ID with other IDs.
			else if (!removePress && !updatePress) {
				testID = Integer.parseInt(loyaltyIDNumber.getValue().toString());
				
				for (int i = 0; i < MAX_ARRAY_A; i++) {
					if (customerNamesPoints[i][6].equals("*"))
						break;
					
					else if (testID == Integer.parseInt(customerNamesPoints[i][6])) {
						errorCode = 2;
						loyaltyIDNumber.setValue(0);
						return false;
					}
				}
			}

			/* Next if-else statements check if values are real numbers.
			   If not between 0 & 2147483646 points, produces an error statement in dialogBox. */

			if (testIn1 > 2147483646 || testIn1 < 0 && !skip1) {
				errorCode = 1;
				return false;
			}

			else if (testIn2 > 2147483646 || testIn2 < 0 && !skip2) {
				errorCode = 1;
				return false;
			}

			else if (testIn3 > 2147483646 || testIn3 < 0 && !skip3) {
				errorCode = 1;
				return false;
			}

			else if (testIn4 > 2147483646 || testIn4 < 0 && !skip4) {
				errorCode = 1;
				return false;
			}

			else if ((int) (testIn1) != (testIn1) || (int) (testIn2) != (testIn2) || (int) (testIn3) != (testIn3) || (int) (testIn4) != (testIn4)) {
				errorCode = 9;
				return false;
			}

			else {
				errorCode = 0;
				return true;
			}
		}

		catch (Exception e) {
			return false;
		}
	}

	public void arrayInitialize() { // Default 2D array values
		if (removePress) {
			removePress = false;

			for (int i = 0; i < replacementArray.length; i++) {
				for (int j = 0; j < replacementArray[i].length; j++) {
					replacementArray[i][j] = "*";
				}
			}
		}

		else {
			for (int i = 0; i < customerNamesPoints.length; i++) {
				for (int j = 0; j < customerNamesPoints[i].length; j++) {
					customerNamesPoints[i][j] = "*";
				}
			}
		}
	}

	public void arrayWriteOut() { // Produces text in the GUI from stored 2D array information
		listCustomerPointsBox.setText("");

		for (int i = 0; i < MAX_ARRAY_A; i++) {
			if (!customerNamesPoints[i][0].equals("") && !customerNamesPoints[i][0].equals("*") && !customerNamesPoints[i][0].equals(null)) {
				listCustomerPointsBox.append("Customer Name: " + customerNamesPoints[i][0]);
				listCustomerPointsBox.append("\nLoyalty #: " + customerNamesPoints[i][6]);

				if (customerNamesPoints[i][1].equals("")) // No value exists.
					listCustomerPointsBox.append("\n\nWeek 1: N/A");
				else
					listCustomerPointsBox.append("\n\nWeek 1: " + customerNamesPoints[i][1]);

				if (customerNamesPoints[i][2].equals("")) // No value exists.
					listCustomerPointsBox.append("\nWeek 2: N/A");
				else
					listCustomerPointsBox.append("\nWeek 2: " + customerNamesPoints[i][2]);

				if (customerNamesPoints[i][3].equals("")) // No value exists.
					listCustomerPointsBox.append("\nWeek 3: N/A");
				else
					listCustomerPointsBox.append("\nWeek 3: " + customerNamesPoints[i][3]);

				if (customerNamesPoints[i][4].equals("")) // No value exists.
					listCustomerPointsBox.append("\nWeek 4: N/A");
				else
					listCustomerPointsBox.append("\nWeek 4: " + customerNamesPoints[i][4]);

				if (customerNamesPoints[i][5].equals("-1")) // Produced when no values exist in customer entry.
					listCustomerPointsBox.append("\n\nPoints: N/A");
				else
					listCustomerPointsBox.append("\n\nPoints: " + customerNamesPoints[i][5]);
				
				if (Integer.parseInt(customerNamesPoints[i][5]) <= 5000)
					listCustomerPointsBox.append("\nBonus: N/A");
				else {
					listCustomerPointsBox.append("\nBonus: 1000");
					totalPoints += 1000;
				}
					

				listCustomerPointsBox.append("\n----------------------------------------------------------------------------------------\n");
			}
		}

		if (totalPoints == -1) // Produced when no values exist.
			listCustomerPointsBox.append("Total Points: N/A");
		else
			listCustomerPointsBox.append("Total Points: " + totalPoints);
	}

	public void errorResponse() {
		if (errorCode == 1) {
			dialogBox.setText("ERROR: POINTS CANNOT BE GREATER THAN 2147483646 OR LESS THAN 0!");
		}
		
		else if (errorCode == 2) {
			dialogBox.setText("ERROR: REPEAT CUSTOMER IDs! TRY AGAIN!");
		}

		else if (errorCode == 3) {
			dialogBox.setText("ERROR: CANNOT USE DIGITS OR SPECIAL CHARACTERS IN LAST NAME!");
		}

		else if (errorCode == 4) {
			dialogBox.setText("ERROR: CANNOT USE DIGITS OR SPECIAL CHARACTERS IN FIRST NAME!");
		}

		else if (errorCode == 5) {
			dialogBox.setText("ERROR: MUST HAVE A FIRST NAME! NO DIGITS OR SPECIAL CHARACTERS!");
		}

		else if (errorCode == 6) {
			dialogBox.setText("ERROR: NO LETTERS OR SPECIAL CHARACTERS IN POINTS FIELDS!");
		}

		else if (errorCode == 7) {
			dialogBox.setText("ERROR: INVALID INPUT(S)! TRY AGAIN...");
		}

		else if (errorCode == 8) {
			dialogBox.setText("ERROR: NO MATCHING CUSTOMER ID! TRY AGAIN!");
		}

		else if (errorCode == 9) {
			dialogBox.setText("ERROR: NO DECIMAL NUMBERS IN POINTS ENTRIES! TRY AGAIN!");
		}

		else {
			dialogBox.setText("AN UNEXPECTED ERROR HAS OCCURED! PRESS RESET TO CONTINUE...");
			add.setEnabled(false);
			remove.setEnabled(false);
			update.setEnabled(false);
			sort.setEnabled(false);
			firstNameIn.setEnabled(false);
			lastNameIn.setEnabled(false);
			week1In.setEnabled(false);
			week2In.setEnabled(false);
			week3In.setEnabled(false);
			week4In.setEnabled(false);
		}
	}

	public void debugConsole() { // Runs when allowDebug -> true
		if (allowDebug) {
			System.out.println("customerCount -> " + customerCount);
			System.out.println("errorCode -> " + errorCode);
			System.out.println("customerPointsEntries -> " + customerPointsEntries);
			System.out.println("customerPoints -> " + customerPoints);
			System.out.println("pointsEntries -> " + pointsEntries);
			System.out.println("totalPoints -> " + totalPoints);
			System.out.println("studentIDNumber -> " + loyaltyIDNumber.getValue().toString());
			System.out.println("sortPress -> " + sortPress);
			System.out.println("customerPosition -> " + customerPosition);

			for (int i = 0; i < customerNamesPoints.length; i++) {
				for (int j = 0; j < customerNamesPoints[i].length; j++) {
					System.out.print(customerNamesPoints[i][j] + " // ");
				}
				System.out.println();
			}

			System.out.println("------------------------------------------------------------------------------------------");
		}
	}
}

/**	Developer Notes:
 * 
 * [Error Code Info]
 * 	0 -> no error
 * 	1 -> non-real percentage value(s)
 * 	2 -> repeat loyalty ID
 * 	3 -> digits in lastNameIn
 * 	4 -> digits in firstNameIn
 * 	5 -> no first name in field
 * 	6 -> parsing of non-double characters
 * 	7 -> invalid String inputs
 * 	8 -> no matching loyalty ID
 * 	9 -> decimal values present in points entry
 * 
 * [Array Design]
 *  By default is initialized with 1000x8 values of "*".
 *  When strictly set to "*", value will not be printed.
 *  Array size is changeable through the MAX_ARRAY_A and MAX_ARRAY_B variables.
 *  MAX_ARRAY_A must have a greater value than MAX_ARRAY_B!
 *    
 * [Loyalty Number Info]
 *  Can accept MAX_ID_RANGE different loyalty numbers.
 *  
 */
