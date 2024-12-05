package Budget.Main;

// Swing imports
import javax.swing.*;
import java.awt.*;

// Imports to the other files
import Budget.UI.IncomeBase;
import Budget.UI.SpendingBase;
import Budget.UI.CalculationBase;
import Budget.UI.UndoBase;
import Budget.UI.BudgetState;

// class definition for the main Budget application
public class BudgetBase extends JPanel { // based on Swing JPanel
    public UndoBase undoBase = new UndoBase(); // Handles multiple undo operations

    // widgets which may have listeners and/or values
    public JButton calculateButton; // Calculate button
    public JButton exitButton; // Exit button
    public JButton undoButton; // Undo button

    // Panels for specific functionalities
    public IncomeBase incomeBase; // Handles income input and calculatio
    public SpendingBase spendingBase; // Handles spending input and calculation
    public CalculationBase calculationBase; // Displays leftover calculation

    // constructor - create UI (dont need to change this)
    public BudgetBase(JFrame frame) {
        setLayout(new GridBagLayout()); // use GridBag layout
        GridBagConstraints gbc = new GridBagConstraints(); // Constraints for layout positioning

        // Instantiate child panels
        incomeBase = new IncomeBase(frame);
        spendingBase = new SpendingBase(frame);
        calculationBase = new CalculationBase(frame, incomeBase, spendingBase);

        // Add the IncomeBase panel to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(incomeBase, gbc);

        // Add the SpendingBase panel to the layout
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(spendingBase, gbc);

        // Add the CalculationBase panel to the layout
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(calculationBase, gbc);

        // Add buttons to the layout
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 3;

        // Calculate button
        calculateButton = new JButton("Calculate");
        gbc.insets = new Insets(10, 5, 10, 5); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(calculateButton, gbc);

        // Exit button
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        exitButton = new JButton("Exit");
        gbc.insets = new Insets(10, 5, 10, 5); // Add padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(exitButton, gbc);

        // Undo button
        gbc.gridx = 6;
        gbc.gridwidth = 1;
        undoButton = new JButton("Undo");
        gbc.insets = new Insets(10, 5, 10, 5); // Add padding to the button
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(undoButton, gbc);

        // Initialise listeners for the buttons
        initListeners();
    }

    // Saves the current state of the application to enable undo functionality
    public void saveCurrentState(UndoBase undoBase) {
        // Retrieve current values from the income and spending fields
        double wages = incomeBase.getTextFieldValue(incomeBase.wagesField);
        double loans = incomeBase.getTextFieldValue(incomeBase.loansField);
        double savings = incomeBase.getTextFieldValue(incomeBase.savingsField);

        double food = spendingBase.getTextFieldValue(spendingBase.foodField);
        double rent = spendingBase.getTextFieldValue(spendingBase.rentField);
        double transport = spendingBase.getTextFieldValue(spendingBase.transportField);

        // Create a BudgetState object to capture the current state
        BudgetState state = new BudgetState(wages, loans, savings, food, rent, transport);
        this.undoBase.saveState(state);
    }

    // Undo the last change made by the user, reverting to the previous state
    public void undoLastChange(UndoBase undoBase) {

        // Retrieve the last saved state
        BudgetState state = undoBase.undo();
        if (state != null) {
            // Restore values to the fields from the saved state
            incomeBase.wagesField.setText(String.valueOf(state.wages));
            incomeBase.loansField.setText(String.valueOf(state.loans));
            incomeBase.savingsField.setText(String.valueOf(state.savings));

            spendingBase.foodField.setText(String.valueOf(state.food));
            spendingBase.rentField.setText(String.valueOf(state.rent));
            spendingBase.transportField.setText(String.valueOf(state.transport));

            double totalIncome = incomeBase.calculateTotalIncome();
            double totalSpending = spendingBase.calculateTotalSpend();
            calculationBase.updateLeftover(totalIncome, totalSpending);
        }
    }

    // Set up action listeners for the buttons
    public void initListeners() {

        // Listener for the Calculate button
        calculateButton.addActionListener(e -> {
            saveCurrentState(undoBase); // Save the current state
            double totalIncome = incomeBase.calculateTotalIncome(); // Calculate total income
            double totalSpending = spendingBase.calculateTotalSpend(); // Calculate total spending
            calculationBase.updateLeftover(totalIncome, totalSpending); // Update leftover amount
        });

        // Listener for the Exit button
        exitButton.addActionListener(e -> System.exit(0));

        // Listen for the Undo button
        undoButton.addActionListener(e -> undoLastChange(undoBase));
    }

    // Sets up and displays the GUI
    public static void createAndShowGUI() {
        // Create and set up the main window
        JFrame frame = new JFrame("Budget Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the content pane with the Budgetbase panel
        frame.setContentPane(new BudgetBase(frame));
        frame.pack(); // Adjust the frame size to fit its content
        frame.setVisible(true); // Display the frame
    }

    // standard main class to set up Swing UI
    public static void main(String[] args) {
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(BudgetBase::createAndShowGUI);
    }
}
