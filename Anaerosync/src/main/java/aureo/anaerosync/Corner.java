package aureo.anaerosync;

/**
 * Represents an Event Square in the game
 */
public class Corner {
    private int id;
    private String eventName;
    private int eventMoney;
    private int eventTime;
    private int eventTrust;
    private String eventCard;
    private boolean isActive;
    private Player activatedBy;

    public Corner(int id, String eventName, int eventMoney, int eventTime, int eventTrust, String eventCard) {
        this.id = id;
        this.eventName = eventName;
        this.eventMoney = eventMoney;
        this.eventTime = eventTime;
        this.eventTrust = eventTrust;
        this.eventCard = eventCard;
        this.isActive = false;
        this.activatedBy = null;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public int getEventMoney() {
        return eventMoney;
    }

    public int getEventTime() {
        return eventTime;
    }

    public int getEventTrust() {
        return eventTrust;
    }

    public String getEventCard() {
        return eventCard;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Player getActivatedBy() {
        return activatedBy;
    }

    public void setActivatedBy(Player activatedBy) {
        this.activatedBy = activatedBy;
    }

    @Override
    public String toString() {
        return "EventSquare{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", eventMoney=" + eventMoney +
                ", eventTime=" + eventTime +
                ", eventTrust=" + eventTrust +
                ", isActive=" + isActive +
                ", activatedBy=" + (activatedBy != null ? activatedBy.getName() : "none") +
                '}';
    }
}
