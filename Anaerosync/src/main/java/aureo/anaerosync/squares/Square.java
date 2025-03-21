package aureo.anaerosync.squares;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class Square {
    protected int position;
    protected SquareType type;

    public Square(int position, SquareType type) {
        this.position = position;
        this.type = type;
    }

    public SquareType getType() {
        return type;
    }
}
