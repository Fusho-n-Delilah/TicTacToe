import java.awt.Point;
import java.util.ArrayList;
public class ViewModel {
    private ArrayList<Cell> cells;
    private String state;
    private String currentPlayer;
    private Point[] winningCells;

    public ViewModel(){
        this.state = "running";
        this.cells = new ArrayList<>();
        initializeCells();
    }
    public void initializeCells(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j<3; j++){
                this.cells.add(new Cell(j, i, ""));
            }
        }
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setCurrentPlayer(String currentPlayer){
        this.currentPlayer = currentPlayer;
    }
    public String getCurrentPlayer(){
        return this.currentPlayer;
    }
    public ArrayList<Cell> getCells() {
        return cells;
    }
    public void setWinningCells(Point[] winningCells){
        this.winningCells = winningCells;
    }
    public Point[] getWinningCells(){
        return this.winningCells;
    }
    public void setValue(int x, int y, String letter){
        cells.stream().filter(cell -> (int) cell.getX() == x && (int) cell.getY() == y).toList().forEach(cell -> cell.setValue(letter));
    }
    public static class Cell extends Point {
        private int x;
        private int y;
        private String value;

        public Cell (){
            this(0,0, "");
        }
        public Cell(int x, int y, String value){
            this.x = x;
            this.y = y;
            this.value = value;
        }
        @Override
        public double getX() {
            return x;
        }
        @Override
        public double getY(){
            return y;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }
    }
}
