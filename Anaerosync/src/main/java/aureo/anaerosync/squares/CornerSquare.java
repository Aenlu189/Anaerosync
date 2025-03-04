package aureo.anaerosync.squares;

public class CornerSquare extends Square {

    private String squareName;
    private String squareCard; // path of image for this square's card

    public CornerSquare(int position, String squareName, String squareCard) {
        super(position, SquareType.CORNER_SQUARE);
        this.squareCard = squareCard;
        this.squareName = squareName;
    }

    public String getSquareName() {
        return squareName;
    }

    public String getSquareCard() {
        return squareCard;
    }
}
