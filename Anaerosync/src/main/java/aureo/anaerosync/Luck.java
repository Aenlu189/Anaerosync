package aureo.anaerosync;

public class Luck {
    private int id;
    private String luckName;
    private int luckMoney;
    private int luckTime;
    private int luckTrustNeeded;
    private int luckWorkForce;
    private boolean isCompleted;
    private Player owner;
    private String luckCard;

    public Luck(int id, String luckName, int luckMoney, int luckTime, int luckTrustNeeded, int luckWorkforce, boolean isCompleted) {
        this.id = id;
        this.luckName = luckName;
        this.luckMoney = luckMoney;
        this.luckTime = luckTime;
        this.luckTrustNeeded = luckTrustNeeded;
        this.luckWorkForce = luckWorkforce;
        this.isCompleted = false;
    }

    public int getId() {
        return id;
    }

    public String getLuckName(){
        return luckName;
    }

    public int getLuckMoney(){
        return luckMoney;
    }

    public int getLuckTime(){
        return luckTime;
    }

    public int getLuckTrustNeeded(){
        return luckTrustNeeded;
    }

    public int getLuckWorkForce(){
        return luckWorkForce;
    }

    public boolean isCompleted(){
        return isCompleted;
    }

    public Player getOwner(){
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getLuckCard() {
        return luckCard;
    }

    public void setLuckCard(String luckCard) {
        this.luckCard = luckCard;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", luckName='" + luckName + '\'' +
                ", luckMoney=" + luckMoney +
                ", luckTime=" + luckTime +
                ", luckTrustNeeded=" + luckTrustNeeded +
                ", luckWorkForce=" + luckWorkForce +
                ", isCompleted=" + isCompleted +
                ", owner=" + (owner != null ? owner.getName() : "none") +
                '}';
    }
}
