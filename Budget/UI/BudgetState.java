package Budget.UI;

// Class to encapsulate the state of the budget at a give point in time
public class BudgetState {
    public double wages; // wages amount
    public double loans; // loans amount
    public double savings; // savings amount
    public double food; // food amount
    public double rent; // rent amount
    public double transport; // transport amount

    // Constructor to initialise a BudgetState instance with given values
    public BudgetState(double wages, double loans, double savings, double food, double rent, double transport) {
        this.wages = wages;
        this.loans = loans;
        this.savings = savings;
        this.food = food;
        this.rent = rent;
        this.transport = transport;
    }
}