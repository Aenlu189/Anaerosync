package aureo.anaerosync.squares;

public abstract class Square {
    protected int position;
    protected SquareType type;

    public Square(int position, SquareType type) {
        this.position = position;
        this.type = type;
    }
}
