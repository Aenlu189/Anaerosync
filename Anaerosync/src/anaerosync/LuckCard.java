public class LuckCard {
    private String chanceName;
    private String chanceDescription;
    private int modifiedMoney;
    private int modifiedEnergy;
    private int modifiedWorkforce;
    private int modifiedPosition;

    public LuckCard(String chanceName, String chanceDescription, int modifiedMoney, int modifiedEnergy, int modifiedWorkforce, int modifiedPosition) {
        this.chanceName = chanceName;
        this.chanceDescription = chanceDescription;
        this.modifiedMoney = modifiedMoney;
        this.modifiedEnergy = modifiedEnergy;
        this.modifiedWorkforce = modifiedWorkforce;
        this.modifiedPosition = modifiedPosition;
    }

    public String getLuckName() {
        return chanceName;
    }

    public void setLuckName(String chanceName) {
        this.chanceName = chanceName;
    }

    public String getLuckDescription() {
        return chanceDescription;
    }

    public void setLuckDescription(String chanceDescription) {
        this.chanceDescription = chanceDescription;
    }

    public int getLuckMoney() {
        return modifiedMoney;
    }

    public void setLuckMoney(int modifiedMoney) {
        this.modifiedMoney = modifiedMoney;
    }

    public int getLuckEnergy() {
        return modifiedEnergy;
    }

    public void setLuckEnergy(int modifiedEnergy) {
        this.modifiedEnergy = modifiedEnergy;
    }

    public int getLuckWorkforce() {
        return modifiedWorkforce;
    }

    public void setLuckWorkforce(int modifiedWorkforce) {
        this.modifiedWorkforce = modifiedWorkforce;
    }

    public int getLuckPosition() {
        return modifiedPosition;
    }

    public void setLuckPosition(int modifiedPosition) {
        this.modifiedPosition = modifiedPosition;
    }

    @Override
    public String toString() {
        return "LuckCard{" +
                "chanceName='" + chanceName + '\'' +
                ", chanceDescription='" + chanceDescription + '\'' +
                ", modifiedMoney=" + modifiedMoney +
                ", modifiedEnergy=" + modifiedEnergy +
                ", modifiedWorkforce=" + modifiedWorkforce +
                ", modifiedPosition=" + modifiedPosition +
                '}';
    }
}
