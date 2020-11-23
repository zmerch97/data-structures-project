import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import java.util.Random;

// ---------------------------------------------------------------------------
// FloodFillGame is the main class - this is where the game starts
// ---------------------------------------------------------------------------
public class FloodFillGame {
    public int size = 14;
    // public int numOfCoords;
    public int[][] colorAtCoordinates;
    public int currentStep;
    public int maxNumberOfSteps;

    public GameLogic theGameLogic;

    private boolean userInterfaceEnabled;
    private static Random theRandomGenerator = new Random();
    private GameJFrame theGameFrame;


    // ---------------------------------------------------------------------------
    // constructor, this is where everything necessary is set up...
    // ---------------------------------------------------------------------------
    public FloodFillGame(final boolean pUserInterfaceEnabled) {

        // have to use a try statement,
        //   because the UnsupportedLookAndFeelException may be thrown
        //     if the requested look-and-feel management classes
        //     are not present on the user's system
        //     (unlikely, as we're using cross-platform look-and-feel).
        try {
            // ask Java to try and behave as (close as it can to)
            //   the user's system graphical user interface:
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName()
            );

            this.userInterfaceEnabled = pUserInterfaceEnabled;
            this.initData();
            this.theGameLogic = new GameLogic(this);
            if (this.userInterfaceEnabled) {
                this.theGameFrame = new GameJFrame(this);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.exit(-1269);
        }
        //System.out.printlncolorAtCoordinates);
    } // end of FloodFillGame()

    public void oneMove(final int colorIndex) {

        this.theGameLogic.floodFill(colorIndex);

        System.out.println(
                this.theGameLogic.floodList.size()
        );

        for (final Coordinates coordinates:this.theGameLogic.floodList) {
            this.colorAtCoordinates[coordinates.y][coordinates.x] = colorIndex;
            if (this.userInterfaceEnabled) {
                this.theGameFrame.setColor(coordinates, colorIndex);
            }
        }

        this.currentStep += 1;
        this.theGameFrame.updateMenuEntryForNumberOfSteps();
        this.theGameFrame.repaint();

        if (this.currentStep == this.maxNumberOfSteps) {
            this.gameIsLost();
        } else if (this.theGameLogic.floodList.size() >= this.size * this.size) {
            System.out.println("here");
            this.gameIsWon();
        }

    } // end of oneMove()

    public int getColor(final Coordinates coordinates) {
        // color at given coordinates:
        return this.colorAtCoordinates[coordinates.y][coordinates.x];
    }

    public void resize(final int pSize) {
        this.size = pSize;
        this.restart();
    }

    public void gameIsWon() {
        JOptionPane.showMessageDialog(theGameFrame,
                "Won in " +
                        Integer.toString(this.currentStep) +
                        " moves!");
        this.theGameFrame.dispose();
        System.exit(0);
    }
    public void gameIsLost() {
        JOptionPane.showMessageDialog(theGameFrame,
                "Sorry, " +
                        Integer.toString(this.currentStep) +
                        " moves is too many...\n \n ...you lost this round.");
        this.theGameFrame.dispose();
        System.exit(0);
    }

    // ---------------------------------------------------------------------------
    // C343 Homework 06 TODO:
    //    devise a "better than random" game strategy!
    //
    // ---------------------------------------------------------------------------
    public void autoPlay() {
        //
        // set up a timer that will play the game, one move at a time:
        //
        javax.swing.Timer timer = new Timer(
                Constants.TIMER_WAIT,
                e -> this.oneMove(maxColor())
        );
        timer.start();
    } // end of autoPlay()
    // ---------------------------------------------------------------------------
    //my method created for finding the color that appears the max number of times around a particular coordinate
    public int maxColor(){
        int[] colors = new int[6];                                             //array that keeps a count of the colors that appear. indices are eaxh color
        System.out.println("Floodlist size " + theGameLogic.floodList.size());

        //first, the colors array is populated with all the numbers that represent the counts (colors)
        //the for loop goes through linked list, checking if the adjecent cells are present, and then
        //it keeps a count of the number of times a specific color appears.
        for (Coordinates i : this.theGameLogic.floodList) {
            if (!theGameLogic.floodList.contains(theGameLogic.toTheRight(i))
                    && theGameLogic.onBoard(theGameLogic.toTheRight(i))) {     //to make sure it's on the table
                int r = getColor(theGameLogic.toTheRight(i));
                colors[r]++;
                //System.out.println("The string " + r);
            }
            if (!theGameLogic.floodList.contains(theGameLogic.toTheLeft(i))
                    && theGameLogic.onBoard(theGameLogic.toTheLeft(i))){       //to make sure it's on the table
                int l = getColor(theGameLogic.toTheLeft(i));
                colors[l]++;
                //System.out.println("The string " + l);
            }
            if (!theGameLogic.floodList.contains(theGameLogic.above(i))
                    && theGameLogic.onBoard(theGameLogic.above(i))){               //to make sure it's on the table
                int u = getColor(theGameLogic.above(i));
                colors[u]++;
            }
            if (!theGameLogic.floodList.contains(theGameLogic.below(i))
                    && theGameLogic.onBoard(theGameLogic.below(i))){               //to make sure it's on the table
                int d = getColor(theGameLogic.below(i));
                colors[d]++;
                //System.out.println("The string " + d);
            }
            //System.out.println(i);
            //return findMax(colors);
        }
        return findMax(colors);     //The color that appears the most is the one that is selected to flood the table.
    }
    public int findMax(int[] A){    //helper function to find the color that occurs maximum number of times.
        int maxNum = A[0];
        int maxInd = 0;
        for (int i =   1; i < A.length; i++){
            if (A[i] > maxNum && i !=getColor(new Coordinates(0,0))){
                maxNum = A[i];
                maxInd = i;
            }
            //System.out.println(maxNum);
        }
        return maxInd;
    }

    public void restart() {
        if (this.userInterfaceEnabled)  {
            this.theGameFrame.dispose();
        }
        this.initData();
        this.theGameLogic = new GameLogic(this);
        if (this.userInterfaceEnabled) {
            this.theGameFrame = new GameJFrame(this);
        }
    }

    private void initData() {
        this.currentStep = 0;
        this.maxNumberOfSteps = FloodFillGame.generateMaxNumOfMoves(this.size);
        // we don't really need this:
        // this.numOfCoords = this.size * this.size;
        this.generateColorsAtCoordinates();
    }

    private void generateColorsAtCoordinates() {
        this.colorAtCoordinates = new int[this.size][this.size];
        for (int x=0; x<this.size; x+=1) {
            for (int y = 0; y < this.size; y += 1) {
                this.colorAtCoordinates[x][y] = FloodFillGame.randInt();
            }
        }
    }

    private static int generateMaxNumOfMoves(final int size) {
        // what's the maximum number of moves allowed in the game?
        return size * 25 / 1;
    }

    private static int randInt() {
        // return a random integer between 0 (included) and 6 (excluded):
        return FloodFillGame.theRandomGenerator.nextInt(6);
    }

}