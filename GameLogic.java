import java.util.LinkedList;
import java.util.List;

// ---------------------------------------------------------------------------
// GameLogic implements the "Flood Fill" logic behind each move in the game
// ---------------------------------------------------------------------------
public class GameLogic {

    private FloodFillGame theGame;

    // floodList is declared as a LinkedList here,
    // alas declared as public (bad practice!),
    // because it is used in the FloodFillGame class...
    // ... you may make it private
    //     and provide methods for accessing it instead:
    // (not compulsory, but would make the code "neater")

    // C343 Homework 06 TODO:
    //    you may have to work with the floodList in your floodFill() method:
    public List<Coordinates> floodList = new LinkedList<Coordinates>();

    // ---------------------------------------------------------------------------
    // constructor for the game logic
    // ---------------------------------------------------------------------------
    public GameLogic(FloodFillGame pGame) {
        theGame = pGame;

        // when the game begins,
        //   the tile at the left/top corner
        //   is the only one considered "filled"
        floodList.add(
                new Coordinates(0, 0)
        );
        System.out.println(floodList);
    } // end of GameLogic()



    // ---------------------------------------------------------------------------
    // C343 Homework 06 TODO:
    //    implement the floodFill() function!
    //
    // ---------------------------------------------------------------------------
    public void floodFill(int colorToFlood) {       //Utilizing the fact that floodList automatically updates when its provided a coordinate.
        int index = 0;
        while (index < floodList.size()) {          //iterate through floodList
            Coordinates pos = floodList.get(index); //get coordinate position at index

            //if statements to check if the upper, lower, right of, and left of indexed coordinate (adjacent cell) is equal to the input parameter,
            //the adjacent cell is on the board,
            //and if the coordinates are not already in floodList
            //When the condition is satisfied for a coordinate, that specific coordinate is added to floodList
            Coordinates right = toTheRight(pos);    //get right cell coordinate
            if (onBoard(right) && colorAtCoordinates(right) == colorToFlood && !floodList.contains(right)) {        //check right cell
                floodList.add(right);                                                                               //add right cell
            }
            Coordinates left = toTheLeft(pos);      //get left cell coordinate
            if (onBoard(left) && colorAtCoordinates(left) == colorToFlood && !floodList.contains(left)) {           //check left cell
                floodList.add(left);                                                                                //add left cell
            }
            Coordinates up = above(pos);            //get upper cell coordinate
            if (onBoard(up) && colorAtCoordinates(up) == colorToFlood && !floodList.contains(up)) {                 //check cell above
            floodList.add(up);                                                                                      //add cell aboev
            }
            Coordinates down = below(pos);          //get lower cell coordinate
            if (onBoard(down) && colorAtCoordinates(down) == colorToFlood && !floodList.contains(down) ) {          //check cell below
            floodList.add(down);                                                                                    //add cell below
            }
        index++;                                    //increment index
            //System.out.println(floodList);
        }


        // C343 Homework 06 TODO:
        //    implement the floodFill() function!
        //
    } // end of floodFill()
    // ---------------------------------------------------------------------------



    // ---------------------------------------------------------------------------
    // onBoard() returns true, if coords specify a tile *on* the game board
    // ---------------------------------------------------------------------------
    public boolean onBoard(final Coordinates coords) {
        final int x = coords.x;
        final int y = coords.y;
        final int size = this.theGame.size;
        return x > -1 && x < size && y > -1 && y < size;
    } // end of onBoard()

    // ---------------------------------------------------------------------------
    // return Coordinates of the tile above the tile at coords
    // ---------------------------------------------------------------------------
    public Coordinates above(final Coordinates coords) {
        return new Coordinates(coords.x, coords.y-1);
    }

    // ---------------------------------------------------------------------------
    // return Coordinates of the tile below the tile at coords
    // ---------------------------------------------------------------------------
    public Coordinates below(final Coordinates coords) {
        // instantiate new Coordinates object:
        return new Coordinates(coords.x, coords.y+1);
    }

    // ---------------------------------------------------------------------------
    // return Coordinates of the tile to the left of the tile at coords
    // ---------------------------------------------------------------------------
    public Coordinates toTheLeft(final Coordinates coords) {
        // instantiate new Coordinates object:
        return new Coordinates(coords.x-1, coords.y);
    }

    // ---------------------------------------------------------------------------
    // return Coordinates of the tile to the toTheRight of the tile at coords
    // ---------------------------------------------------------------------------
    public Coordinates toTheRight(final Coordinates coords) {
        // instantiate new Coordinates object:
        return new Coordinates(coords.x + 1, coords.y);
    }

    // ---------------------------------------------------------------------------
    // get the color of the tile at coords
    // ---------------------------------------------------------------------------
    public int colorAtCoordinates(final Coordinates coords) {
        // ask theGame object, which color at coords:
        return this.theGame.getColor(coords);
    }

} 