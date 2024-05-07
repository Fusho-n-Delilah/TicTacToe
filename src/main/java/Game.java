import java.awt.Point;
import java.sql.Array;
import java.util.ArrayList;
public class Game {
    private ArrayList<Cell> cells;
    private Point[] winningCells;

    public Game(){
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
    public void setValue(int x, int y, String letter){
        cells.stream().filter(cell -> (int) cell.getX() == x && (int) cell.getY() == y).toList().getFirst().setValue(letter);
    }

    public String getValue(int x, int y){
       return cells.stream().filter(cell -> (int) cell.getX() == x && (int) cell.getY() == y).toList().getFirst().getValue();
    }

    public boolean isWin(String currentPlayer){
        Cell[][] matrix = new Cell[3][3];
        int index = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                matrix[i][j] = cells.get(index);
                index++;
            }
        }

        //check horizontals
        for(int i = 0; i < 3; i++){
            if(matrix[i][0].getValue().equals(currentPlayer)
                    && matrix[i][1].getValue().equals(currentPlayer)
                    && matrix[i][2].getValue().equals(currentPlayer)
            ){
                this.winningCells = new Point[3];
                this.winningCells[0] = new Point((int)matrix[i][0].getX(), i);
                this.winningCells[1] = new Point((int)matrix[i][1].getX(), i);
                this.winningCells[2] = new Point((int)matrix[i][2].getX(), i);
                return true;
            }
        }

        //check verticals
        for(int i = 0; i < 3; i++){
            if(matrix[0][i].getValue().equals(currentPlayer)
                    && matrix[1][i].getValue().equals(currentPlayer)
                    && matrix[2][i].getValue().equals(currentPlayer)
            ){
                this.winningCells = new Point[3];
                this.winningCells[0] = new Point(i, (int)matrix[0][i].getY());
                this.winningCells[1] = new Point(i, (int)matrix[1][i].getY());
                this.winningCells[2] = new Point(i, (int)matrix[2][i].getY());
                return true;
            }
        }

        //check diagnals
        if(matrix[0][0].getValue().equals(currentPlayer)
                && matrix[1][1].getValue().equals(currentPlayer)
                && matrix[2][2].getValue().equals(currentPlayer)
        ){
            this.winningCells = new Point[3];
            this.winningCells[0] = new Point(0,0);
            this.winningCells[1] = new Point(1,1);
            this.winningCells[2] = new Point(2,2);
            return true;
        }
        if(matrix[0][2].getValue().equals(currentPlayer)
                && matrix[1][1].getValue().equals(currentPlayer)
                && matrix[2][0].getValue().equals(currentPlayer)
        ){
            this.winningCells = new Point[3];
            this.winningCells[0] = new Point(0,2);
            this.winningCells[1] = new Point(1,1);
            this.winningCells[2] = new Point(2,0);
            return true;
        }

        return false;

    }

    public Point[] getWinningCells() {
        return winningCells;
    }

    private static class Cell extends Point {
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
        public String getValue(){
            return this.value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
}

