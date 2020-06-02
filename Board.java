import java.lang.*;


//Board class represents the sudoku grid
//It has two int arrays - one for the game board and one for the solution
//Int k is the number of missing values 
public class Board{
    
    public int[][] gameBoard;
    public int[][] solution;

    public int K; 

    
    
    //intializes the board to a 9 by 9 grid 
    public Board(int K){
        
        this.K = K;
        gameBoard = new int[9][9];
        solution = new int[9][9];
        
          
    }

    //checks if num is used in the 3-by-3 subgrid 
    public Boolean unUsedInBox(int rowStart, int columnStart, int num){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (gameBoard[rowStart+i][columnStart+j] == num)
                    return false;
            }
        }
        return true;
    }

    //checks if num is in the specified row 
    public Boolean unUsedInRow(int i, int num){
        for (int j = 0; j < 9; j++){
            if (gameBoard[i][j] == num){
                return false;
            }
        }
        return true;
    }

    //checks if num is in the specified column
    public Boolean unUsedInColumn(int j, int num){
        for (int i = 0; i < 9; i++){
            if (gameBoard[i][j] == num){
                return false;
            }
        }
        return true;
    }

    //checks if num is safe to enter in the cell
    public Boolean isSafe(int i, int j, int num){
        return (unUsedInRow(i, num) && unUsedInColumn(j, num) && 
                unUsedInBox(i - i % 3, j - j % 3, num));
    }

    //generates a random number from 1 to 9 to fill the board 
    public int randomGenerator(int num){
        return (int) Math.floor((Math.random() * num+1));
    }

    //fills a 3 by 3 subgrid
    public void fillSubGrid(int row, int column){
        int num;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                do{
                    num = randomGenerator(9);
                }
                while(!unUsedInBox(row, column, num));
                
                gameBoard[row+i][column+j] = num;
                //solution[row+1][column+j] = num;
            }
        }
    }

    

    //fills the diagonal 3 subgrids (subgrid 0, 4, 7)
    public void fillDiagonal(){
        for (int i = 0; i < 9; i = i +3){
            fillSubGrid(i, i);
        }
    }

    //fills the remaining 6 subgrids 
    public Boolean fillRemaining(int i, int j){
        if (j >= 9 && i < 8){
            i = i+1;
            j = 0;
        }
        if (i >= 9 && j >= 9)
            return true;
        if (i < 3){
            if (j < 3)
                j = 3;
        }
        else if (i < 6){
            if (j == (int) (i/3)*3)
                j = j + 3;
        }
        else{
            if (j == 6){
                i = i + 1;
                j = 0;
                if (i >= 9)
                    return true;
            }
        }

        for (int num = 1; num <= 9; num++){
            if(isSafe(i, j, num)){
                gameBoard[i][j] = num;
                solution[i][j] = num;
                if(fillRemaining(i, j+1)){
                    return true;
                }
                gameBoard[i][j] = 0;
                //solution[i][j] = 0;
            }
        }
        return false;

    }

    //removes K digits from the board to create empty spaces 
    public void removeKDigits(){
        int count = K;
        while(count != 0){
            int cellID = randomGenerator(80);
            int i = cellID / 9;
            int j = cellID % 9;
            if (j != 0){
                j = j - 1;
            }
            if(gameBoard[i][j] != 0){
                count--;
                gameBoard[i][j] = 0;
            }
        }
    }


    //fills the board 
    public void fillBoard(){
        fillDiagonal();
        fillRemaining(0, 3);
        removeKDigits();
    }

    //generates the board 
    public void generateBoard(){
        
        fillBoard();
        
    }


   
    
    public static boolean isSafe(int[][] solution,  int row, int col,  int num)  
    { 
        //checking to see if the num already exists in the row 
        for (int d = 0; d < solution.length; d++)  
        { 
            // if the number we are trying to  
            // place is already present in  
            // that row, return false; 
            if (solution[row][d] == num)  
            { 
                return false; 
            } 
        } 
        
        //checking to see if the num already exists in the column
        for (int r = 0; r < solution.length; r++) 
        { 
            // if the number we are trying to 
            // place is already present in 
            // that column, return false; 
    
            if (solution[r][col] == num) 
            { 
                return false; 
            } 
        } 
    
        //checking to see if the num already exists in the subgrid (3 by 3 box)
        int sqrt = (int) Math.sqrt(solution.length); 
        int boxRowStart = row - row % sqrt; 
        int boxColStart = col - col % sqrt; 
    
        for (int r = boxRowStart; r < boxRowStart + sqrt; r++)  
        { 
            for (int d = boxColStart;  d < boxColStart + sqrt; d++)  
            { 
                if (solution[r][d] == num)  
                { 
                    return false; 
                } 
            } 
        } 
    
            // if there is no clash, it's safe 
        return true; 
    } 

    //solves the board using backtracking 
    public Boolean solveBoard(int[][] sol, int n){
        int row = -1; 
        int col = -1; 
        boolean isEmpty = true; 
        for (int i = 0; i < n; i++) 
        { 
            for (int j = 0; j < n; j++)  
            { 
                if (sol[i][j] == 0)  
                { 
                    row = i; 
                    col = j; 
                  
                // we still have some remaining 
                // missing values in Sudoku 
                    isEmpty = false;  
                    break; 
                } 
            } 
            if (!isEmpty) 
            { 
                break; 
            } 
        } 
  
        // no empty space left, we are done 
        if (isEmpty)  
        { 
            return true; 
        } 
  
        // else for each-row backtrack 
        for (int num = 1; num <= n; num++) 
        { 
            if (isSafe(sol, row, col, num)) 
            { 
                 
                sol[row][col] = num;
                if (solveBoard(sol, n))  
                { 
                    for (int i = 0; i < 9; i++){
                        for (int j = 0; j < 9; j++){
                            this.solution[i][j] = sol[i][j];
                        }
                    }
                    return true; 
                }  
                else
                { 
                     // replace it 
                    sol[row][col] = 0;
                } 
            } 
        } 
        return false; 
    } 

    public void copy(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                solution[i][j] = gameBoard[i][j];
            }
        }
    }

    public void clearBoard(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                gameBoard[i][j] = 0;
                solution[i][j] = 0;
            }
        }
    }
    

    public static void main(String [] args){
        
        


    }


}