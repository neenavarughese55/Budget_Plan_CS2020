package Budget.UI;

import java.util.Stack;

// Class to manage undo functionality for the application
public class UndoBase {

    private Stack<BudgetState> stateStack = new Stack<>(); // Stack to store states

    // To save the current state in the stack
    public void saveState(BudgetState state) {
        stateStack.push(state); // Push the state onto the stack
    }

    // To undo the last state in the stack
    public BudgetState undo() {
        if (!stateStack.isEmpty()) {
            return stateStack.pop(); // Retrieve and remove the last saved state
        }
        return null; // No state to undo
    }

    // To check whether undo is possible
    public boolean canUndo() {
        return !stateStack.isEmpty();
    }

}
