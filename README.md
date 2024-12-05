Budget Calculator Application
Overview
The Budget Calculator Application is a Java-based desktop program that helps users manage their income and spending, providing insights into their financial status. The application is built using Swing, a GUI framework in Java, to create a simple and interactive interface. Key features include:

Input and calculation of income from multiple sources (e.g., wages, loans, savings).
Input and calculation of spending on different categories (e.g., food, rent, transport).
Calculation and display of the leftover amount (income - spending).
Undo functionality to revert to previous states.
Features
Income Management:

Users can input their income from three sources: wages, loans, and savings.
Each source can be specified with a time duration (per week, per month, or per year), which is normalized to weekly amounts for calculations.
Spending Management:

Users can input spending in three categories: food, rent, and transport.
Similar to income, spending can also be specified with a time duration and is normalized to weekly amounts.
Calculation of Leftover:

The leftover amount is calculated as Total Income - Total Spending and displayed to the user.
If the leftover amount is negative, it is highlighted in red; otherwise, it is displayed in black.
Undo Functionality:

Users can undo changes made to the input fields, reverting to previously saved states.
Supports multiple undo operations.
Program Structure
The program is organized into the following modules:

1. BudgetBase (Main Panel)
This is the central class that manages the main user interface and integrates all other components.
Responsibilities:
Lays out the income, spending, and calculation panels using a GridBagLayout.
Manages buttons for actions: Calculate, Undo, and Exit.
Handles saving and undoing the application state via the UndoBase class.
Key Methods:
saveCurrentState(UndoBase undoBase): Saves the current state of all input fields.
undoLastChange(UndoBase undoBase): Reverts to the previous state using UndoBase.
initListeners(): Adds action listeners for buttons to perform calculations, undo, and exit.
2. IncomeBase (Income Panel)
A sub-panel for managing income-related input fields.
Components:
Text fields for inputting wages, loans, and savings.
Dropdowns for selecting the duration of income (per week, per month, or per year).
A read-only field for displaying the calculated total income.
Key Methods:
calculateTotalIncome(): Calculates total income based on the normalized weekly values.
adjustDuration(double value, String duration): Converts the input value to its weekly equivalent.
getTextFieldValue(JTextField field): Retrieves and validates numerical values from text fields.
3. SpendingBase (Spending Panel)
A sub-panel for managing spending-related input fields.
Components:
Text fields for inputting spending on food, rent, and transport.
Dropdowns for selecting the duration of spending (per week, per month, or per year).
A read-only field for displaying the calculated total spending.
Key Methods:
calculateTotalSpend(): Calculates total spending based on the normalized weekly values.
adjustDuration(double value, String duration): Converts the input value to its weekly equivalent.
getTextFieldValue(JTextField field): Retrieves and validates numerical values from text fields.
4. CalculationBase (Calculation Panel)
A sub-panel for displaying the leftover amount.
Components:
A JLabel that displays the leftover amount, styled dynamically based on whether the value is positive or negative.
Key Methods:
updateLeftover(double totalIncome, double totalSpend): Calculates and updates the leftover amount.
5. UndoBase (Undo Management)
A utility class that manages the undo functionality using a stack to store BudgetState objects.
Key Methods:
saveState(BudgetState state): Pushes the current state onto the stack.
undo(): Pops the last saved state from the stack.
canUndo(): Checks if undo operations are available.
6. BudgetState (State Management)
A data class that represents the state of all input fields at a specific point in time.
Attributes:
wages, loans, savings: Income-related values.
food, rent, transport: Spending-related values.
7. BudgetBaseTests (Unit Tests)
A test suite using JUnit to validate the functionality of the program.
Test Cases:
shouldTotal(): Verifies the correctness of total income calculation.
shouldUndoLastChange(): Ensures undo functionality works as expected.
shouldUndoMultipleChanges(): Verifies multiple undo operations.
shouldNotUndoIfNoState(): Ensures the program behaves correctly when no undo states are available.
