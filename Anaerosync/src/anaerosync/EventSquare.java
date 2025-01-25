public class EventSquare {
    private String name;
    private int id;
    private SquareType squareType;
    private int diceMultiplier;
    private int modifiedMoney;
    private int modifiedEnergy;
    private int modifiedWorkforce;

    public EventSquare(int id, SquareType squareType, String name, int diceMultiplier, int modifiedMoney, int modifiedEnergy, int modifiedWorkforce) {
        this.id = id;
        this.squareType = squareType;
        this.name = name;
        this.diceMultiplier = diceMultiplier;
        this.modifiedMoney = modifiedMoney;
        this.modifiedEnergy = modifiedEnergy;
        this.modifiedWorkforce = modifiedWorkforce;
    }

    public void landOnSquare(Player player) {

    }
}
