/**
 * A class that models a real world electronic, from an energy companies point of view.
 * The characteristics defined for a electronic in this class are:
 * 1)Name
 * 2)Energy Usage
 * 3)Income from electronic
 * 4)Amount
 * 
 * @author Marcus Sosa
 */
class electronic {

    /** Object Charateristics */
    private String name = "";
    private double energyUsage = 0.0;
    private double income = 0.0;
    private int amount = 0;

    /**
     * Constructs a electronic Object.
     * 
     * @param nameParam is a String that represents the electronic's name.
     * @param energyUsageParam is a double that represents how much energy an electronic uses.
     * @param incomeParam is a double that represents how much income is gained from giving energy to said object.
     * @param amountParam is an integer representing the amount of said object is being lent this energy.
     */
    public electronic(String nameParam, double energyUsageParam, double incomeParam, Integer amountParam) {
        //Initializes instance variables.
        this.name = nameParam;
        this.energyUsage = energyUsageParam;
        this.income = incomeParam;
        this.amount = amountParam;
    }

    /** Accessor methods */
    public String getName() {
        return this.name;
    }
    public double getEnergyUsage() {
        return this.energyUsage;
    }
    public double getIncome() {
        return this.income;
    }
    public Integer getAmount() {
        return this.amount;
    }

    /**Mutator Methods */
    public void setName(String newName) {
        this.name = newName;
    }
    public void setEnergyUsage(double newEnergyUsage) {
        this.energyUsage = newEnergyUsage;
    }
    public void setIncome(double newIncome) {
        this.income = newIncome;
    }
    public void setAmount(int newAmount) {
        this.amount = newAmount;
    }

    /**
     * Returns a printable string of the instance variables in one line
     * Each instance variable is formatted in columns
     * For example, an electronic object new electronic("Computer", 24.44,10.05,2)
     *   Computer      24.44    10.05   2
     * 
     * @return a formatted string with name, energy usage, income, and amount on one line, in columns.
     */
    @Override
    public String toString() {
        return String.format("%-10s  %-6.2f  %-6.2f  %-7d", this.name, this.energyUsage, this.income, this.amount);
    }
}