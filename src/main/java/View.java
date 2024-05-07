import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.StrokeBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class View extends JFrame {
    private ArrayList<Cell> cells;
    private JTextField display;
    private ImageIcon iconO = new ImageIcon("src/main/resources/squareo.png");
    private ImageIcon iconX = new ImageIcon("src/main/resources/squarex.png");
    private ImageIcon blank = new ImageIcon("src/main/resources/square.png");
    public View(){
        setPreferredSize(new Dimension(600, 715));
        setResizable(false);
        setBackground(Color.lightGray);
        this.init();

        setLayout(new FlowLayout());
        pack();
        setVisible(true);
    }

    public void display(ViewModel viewModel){
        this.display.setText(viewModel.getCurrentPlayer().toUpperCase() + "'s Turn");
        viewModel.getCells().forEach(viewModelCell -> {
            ImageIcon icon;
            if(viewModelCell.getValue().equalsIgnoreCase("x")){
                icon = this.iconX;
            } else if (viewModelCell.getValue().equalsIgnoreCase("o")){
                icon = this.iconO;
            } else {
                icon = this.blank;
            }
            cells.stream().filter(cell -> cell.getCellX() == viewModelCell.getX() && cell.getCellY() == viewModelCell.getY())
                    .toList()
                    .getFirst()
                    .setIcon(icon);
        });

        if(viewModel.getState().equals("win")){
            display.setText(viewModel.getCurrentPlayer().toUpperCase() + " Wins!");
            for(Point winningCell : viewModel.getWinningCells()){
                this.cells.stream()
                        .filter(c -> c.getCellX() == winningCell.getX() && c.getCellY() == winningCell.getY())
                        .toList()
                        .getFirst()
                        .showWin();
            }
        }
    }
    private void init(){
        this.cells = initCells();
        JPanel gameBoard = initGameBoard();
        JPanel controls = initControls();

        cells.forEach(gameBoard::add);

        this.add(controls);
        this.add(gameBoard);
    }

    private JPanel initGameBoard(){
        JPanel gameBoard = new JPanel();

        gameBoard.setLayout(new GridLayout(3,3));
        gameBoard.setBackground(Color.BLACK);
        gameBoard.setBorder(new BevelBorder(BevelBorder.LOWERED,Color.LIGHT_GRAY,Color.LIGHT_GRAY));

        return gameBoard;
    }

    private JPanel initControls(){
        JPanel controls = new JPanel();
        controls.setPreferredSize(new Dimension(600, 75));
        controls.setLayout(new GridLayout(1,3));
        controls.setBorder(new BevelBorder(BevelBorder.RAISED, Color.lightGray, Color.DARK_GRAY));

        //create reset button
        JButton resetBtn = new JButton("Reset");
        resetBtn.setPreferredSize(new Dimension(100,75));
        resetBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cells.forEach(c -> c.stylizeCell());
                Controller.reset();

            }
        });
        this.display = new JTextField(10);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.lightGray);
        display.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.lightGray, Color.DARK_GRAY));
        display.setFont(new Font("Helvetica", Font.PLAIN, 25));
        display.setHorizontalAlignment(JTextField.CENTER);

        controls.add(resetBtn);
        controls.add(Box.createHorizontalGlue());
        controls.add(display);

        return controls;
    }

    private ArrayList<Cell> initCells(){
        ArrayList<Cell> cells = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                cells.add(new Cell(j,i));
            }
        }
        return cells;
    }

    private class Cell extends JButton{
        private Point cell;

        Cell(){
            this(0,0);
        }
        Cell(int x, int y){
            this.cell = new Point(x,y);
            this.stylizeCell();
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    Cell targetCell = new Cell();
                    Object obj = e.getSource();
                    if (obj instanceof Cell) {
                        targetCell = (Cell)obj;
                    }
                    int x = targetCell.getCellX();
                    int y = targetCell.getCellY();

                    Controller.handleClick(x,y);
                }
            });

        }

        private void stylizeCell(){
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(200,200));
            setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
            setVisible(true);
        }

        public int getCellX(){
            return (int) cell.getX();
        }

        public int getCellY(){
            return (int) cell.getY();
        }

        public void showWin(){
            setBorder(new LineBorder(Color.YELLOW,4));
        }
    }


}
