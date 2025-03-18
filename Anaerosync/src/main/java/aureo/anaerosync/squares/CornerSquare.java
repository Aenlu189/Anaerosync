package aureo.anaerosync.squares;

public class CornerSquare extends Square {
    private String cornerType;

    public CornerSquare(int position, String cornerType) {
        super(position, SquareType.CORNER_SQUARE);
        this.cornerType = cornerType;
    }

    public String getCornerType() {
        return cornerType;
    }

    public void setCornerType(String cornerType) {
        this.cornerType = cornerType;
    }
}
