import java.awt.*;
import javax.swing.*;
import javax.swing.*;
import java.awt.event.*;




public class GUI extends JPanel implements ActionListener {
    
    public static final int GRID_SIZE = 9;    // Size of the board
    public static final int SUBGRID_SIZE = 3; // Size of the sub-grid


    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int CANVAS_WIDTH  = CELL_SIZE * GRID_SIZE;
    public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE;

    public static final Color OPEN_CELL_BGCOLOR = Color.YELLOW;
    public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);  // RGB
    public static final Color OPEN_CELL_TEXT_NO = Color.RED;
    public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
    public static final Color CLOSED_CELL_TEXT = Color.BLACK;
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

    public static JPanel levelPanel = new JPanel(); 

    private Board game;
    public Cell[][] cellArr = new Cell[9][9];

    


    public GUI(){
        setBackground(Color.cyan);
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        game = new Board(20);
        game.generateBoard();
        game.copy();
        

        CellInputListener listener = new CellInputListener();

        

        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                Cell c = new Cell(game.gameBoard[row][col]);
                cellArr[row][col] = c;
                add(cellArr[row][col]);

                if (game.gameBoard[row][col] == 0){
                    cellArr[row][col].setText("");
                    cellArr[row][col].setEditable(true);
                    cellArr[row][col].setBackground(OPEN_CELL_BGCOLOR);
                    cellArr[row][col].addActionListener(listener);
                }
                else{
                    cellArr[row][col].setText(game.gameBoard[row][col] + "");
                    cellArr[row][col].setEditable(false);
                    cellArr[row][col].setBackground(CLOSED_CELL_BGCOLOR);
                    cellArr[row][col].setForeground(CLOSED_CELL_TEXT);
                }
                cellArr[row][col].setHorizontalAlignment(JTextField.CENTER);
                cellArr[row][col].setFont(FONT_NUMBERS);
            }//inner for loop 
        }//outer for loop
    
    }//GUI()

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("New Board")){
            game.clearBoard();
            game.generateBoard();
            game.copy();

            

            
            for (int row = 0; row < GRID_SIZE; ++row) {
                for (int col = 0; col < GRID_SIZE; ++col) {
                    cellArr[row][col].setValue(game.gameBoard[row][col]);
                    if (game.gameBoard[row][col] == 0){
                        cellArr[row][col].setText("");
                        cellArr[row][col].setEditable(true);
                        cellArr[row][col].setBackground(OPEN_CELL_BGCOLOR);
                    }
                    else{
                        cellArr[row][col].setText(game.gameBoard[row][col] + "");
                        cellArr[row][col].setEditable(false);
                        cellArr[row][col].setBackground(CLOSED_CELL_BGCOLOR);
                        cellArr[row][col].setForeground(CLOSED_CELL_TEXT);
                    }
                    cellArr[row][col].setHorizontalAlignment(JTextField.CENTER);
                    cellArr[row][col].setFont(FONT_NUMBERS);
                }//inner for loop 
            }//outer for loop


            repaint();

            

        }

        else if (e.getActionCommand().equals("Check Your Solution")){
            
            game.solveBoard(game.solution, game.solution.length);
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    if (cellArr[i][j].getValue() != game.solution[i][j]){
                        cellArr[i][j].setBackground(Color.red);
                    }
                    else{
                        cellArr[i][j].setEditable(false);
                        cellArr[i][j].setBackground(CLOSED_CELL_BGCOLOR);
                        cellArr[i][j].setForeground(CLOSED_CELL_TEXT);
                    }
                }
            }
            
            repaint();
        }

        else if (e.getActionCommand().equals("Solve Board")){
            
            game.solveBoard(game.gameBoard, game.gameBoard.length);
            for (int row = 0; row < GRID_SIZE; ++row) {
                for (int col = 0; col < GRID_SIZE; ++col) {
                    
                    cellArr[row][col].setText(game.gameBoard[row][col] + "");
                    cellArr[row][col].setEditable(false);
                    cellArr[row][col].setBackground(CLOSED_CELL_BGCOLOR);
                    cellArr[row][col].setForeground(CLOSED_CELL_TEXT);
                    cellArr[row][col].setHorizontalAlignment(JTextField.CENTER);
                    cellArr[row][col].setFont(FONT_NUMBERS);

                }//inner for loop 
            }//outer for loop
            
            repaint();
        }
        
        
        
    }
      
    public static void main(String[] args){
        JFrame outerFrame = new JFrame();
        outerFrame.setSize(610, 630);
        outerFrame.setBackground(Color.lightGray);
        outerFrame.setTitle("Sudoku");

        outerFrame.setLayout(new BorderLayout());
        GUI gui = new GUI();
        outerFrame.add(gui, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton newBoard = new JButton("New Board");
        JButton checkBoard = new JButton("Check Your Solution");
        JButton solveBoard = new JButton("Solve Board");
        buttonPanel.add(newBoard);
        buttonPanel.add(checkBoard);
        buttonPanel.add(solveBoard);
        outerFrame.add(buttonPanel, BorderLayout.SOUTH);

        newBoard.addActionListener(gui);
        checkBoard.addActionListener(gui);
        solveBoard.addActionListener(gui);

        



        

        outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        outerFrame.setVisible(true);


        
        
    }//main



    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
        g.drawLine(5, 3, 5, 565);
        g.drawLine(205, 3, 205, 565);
        g.drawLine(405, 3, 405, 565);
        g.drawLine(605, 3, 605, 565);
        g.drawLine(5, 3, 605, 3);
        g.drawLine(5, 190, 605, 190);
        g.drawLine(5, 378, 605, 378);
        g.drawLine(5, 565, 605, 565);
        
    }//paintComponent

    private class CellInputListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            int cellRow = -1;
            int cellColumn = -1;
            Cell source = (Cell)e.getSource();
            boolean found = false;
            for (int row = 0; row < 9 && !found; row++){
                for (int column = 0; column < 9 && !found; column++){
                    if(cellArr[row][column] == source){
                        cellRow = row;
                        cellColumn = column;
                        found = true;
                    }
                }
            }
            
            String input = cellArr[cellRow][cellColumn].getText();
            int num = Integer.parseInt(input);
            cellArr[cellRow][cellColumn].setValue(num);
            cellArr[cellRow][cellColumn].setText(num + "");

            
           



            repaint();

        }
    }
    

}//GUI class
