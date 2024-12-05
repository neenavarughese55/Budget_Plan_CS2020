package Budget.UI;

public class BudgetState {
    public double wages;
    public double loans;
    public double savings;
    public double food;
    public double rent;
    public double transport;

    public BudgetState(double wages, double loans, double savings, double food, double rent, double transport) {
        this.wages = wages;
        this.loans = loans;
        this.savings = savings;
        this.food = food;
        this.rent = rent;
        this.transport = transport;
    }
}