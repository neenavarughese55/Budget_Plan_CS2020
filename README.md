## Budget Calculator Application
### Implementation
### BudgetBase.java File

#### Implementation
The BudgetBase class serves as the central panel for the Budget Calculator application. It manages the integration of the income, spending, and calculation panels, as well as the primary control buttons (Calculate, Undo, and Exit).

#### saveCurrentState Method
The saveCurrentState method is responsible for capturing the current state of the user’s input fields (income and spending values). This state is stored using the UndoBase class, allowing the application to revert to this state later if the Undo button is pressed. The method retrieves values from all relevant text fields and stores them in a BudgetState object.

#### undoLastChange Method
The undoLastChange method enables the undo functionality by reverting the input fields to the last saved state. It retrieves the last state from the UndoBase stack and updates the corresponding fields in the IncomeBase and SpendingBase panels. If no previous state exists, the method does nothing, ensuring no errors occur.

#### initListeners Method
This method initialises the action listeners for the control buttons:

1. Calculate: Calls the saveCurrentState method, calculates the total income and spending, and updates the leftover amount in the CalculationBase panel.
2. Undo: Calls the undoLastChange method to revert to the previous state.
3. Exit: Terminates the application when pressed.

## IncomeBase.java File
### Implementation
The IncomeBase panel captures and calculates the user’s income details. Users can input income from wages, loans, and savings, selecting durations (Per Week, Per Month, Per Year) for each source.

### calculateTotalIncome Method
The calculateTotalIncome method computes the total weekly income by:

1. Retrieving values from the input fields.
2. Converting each value to a weekly equivalent using the adjustDuration method.
3. Summing the converted values and updating the read-only total income field.

### addListeners Method
This method adds focus and action listeners to:

1. Recalculate the total income when any field loses focus.
2. Dynamically update the total income if a duration dropdown is changed.

### Error Handling
If a non-numeric value is entered, the method displays an error message and ignores the invalid input, preventing disruptions in calculations.

### SpendingBase.java File
#### Implementation
The SpendingBase panel captures and calculates the user’s spending details in three categories: food, rent, and transport. Similar to the IncomeBase, users select durations for each category, and values are normalised to weekly amounts.

#### calculateTotalSpend Method
The calculateTotalSpend method:

1. Retrieves and validates spending values from the input fields.
2. Converts each value to a weekly equivalent using the adjustDuration method.
3. Updates the total spending field and returns the calculated value.

#### addListeners Method
Adds focus and action listeners to ensure that changes in input fields or dropdown selections dynamically update the total spending value.

### CalculationBase.java File
#### Implementation
The CalculationBase panel displays the leftover funds (Total Income - Total Spending). The display dynamically changes color to indicate financial health:

Black: Positive or zero leftover.
Red: Negative leftover.

#### updateLeftover Method
This method calculates the leftover amount and updates the label with the formatted value, applying the appropriate styling based on whether the result is positive, zero, or negative.

### UndoBase.java File
#### Implementation
The UndoBase class manages the undo functionality by maintaining a stack of BudgetState objects. Each BudgetState represents a snapshot of the application’s state at a specific time.

#### saveState Method
Adds a new BudgetState to the stack, preserving the current values of all input fields.

#### undo Method
Pops the last saved state from the stack, allowing the application to revert to it. If the stack is empty, the method does nothing.

### BudgetState.java File
#### Implementation
The BudgetState class encapsulates the state of the budget at a specific moment. It stores:

- Income values: wages, loans, and savings.
- Spending values: food, rent, and transport.
This class is used by UndoBase to manage undo operations.

### BudgetBaseTests.java File
#### JUnit Testing
The BudgetBaseTests class includes unit tests to verify the functionality of the Budget Calculator:

1. Income Calculation Test:

     - Verifies that the calculateTotalIncome method correctly computes the sum of income sources normalised to weekly amounts.

2. Spending Calculation Test:

     - Ensures that the calculateTotalSpend method computes spending correctly.

3. Undo Functionality Test:

     - Tests that the undoLastChange method reverts input fields to their previous values.

4. Multiple Undo Test:

     - Verifies that multiple undo operations are correctly handled, restoring the application to progressively earlier states.

5. Edge Case Test:

     - Ensures the application handles empty or invalid inputs gracefully without crashing.

### Future Enhancements
Add more income and spending categories for detailed budgeting.
Enable saving and loading of budget data for persistent tracking.
Introduce visual charts to represent income and spending trends.
