package Budget.UI;

import java.util.Stack;

public class UndoBase {

    private final Stack<BudgetState> stateStack = new Stack<>();

    // To save the current state in the stack
    public void saveState(BudgetState state) {
        stateStack.push(state);
    }

    // To undo the last state in the stack
    public BudgetState undo() {
        if (!stateStack.isEmpty()) {
            return stateStack.pop();
        }
        return null; // No state to undo
    }

    // To whether undo is possible
    public boolean canUndo() {
        return !stateStack.isEmpty();
    }

}
