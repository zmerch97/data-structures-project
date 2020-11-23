public class Coordinates {
    public int x;
    public int y;

    public Coordinates(final int pX, final int pY) {
        this.x = pX;
        this.y = pY;
    }
    public boolean equals(final Object pObject) {
        // ensure that we're comparing Coordinates objects:
        if (pObject instanceof Coordinates) {
            final Coordinates pOtherCoords = (Coordinates) pObject;
            return this.x == pOtherCoords.x && this.y == pOtherCoords.y;
        } else {
            return false;
        }
    }
    public int hashCode() {
        return this.x * 101 + this.y;
    }

    public String toString() {
        // printable representation of Coordinates
        return Integer.toString(this.x) + ", " + Integer.toString(this.y);
    }

}