#include <iostream>


using namespace std;


bool findUnassignedCell(int grid[9][9], int &row, int &column)
{
    for (row = 0; row < 9; row++){
        for (column = 0; column < 9; column++){
            if (grid[row][column] == 0){
                return true;
            }
        }
    }
    return false;
    
}//findUnassignedCell

//checks to see if the number already exists in the specified row
//returns true if the number doesn't exist in the row
//returns false if the number does exist in the row
bool checkRow(int grid[9][9], int row, int number)
{
    for (int i = 0; i < 9; i++){
        if (grid[row][i] == number) 
            return false;
    }
    return true;
}

//checks to see if the number already exists in the specified column
//returns true if the number doesn't exist in the column
//returns false if the number does exist in the column
bool checkColumn(int grid[9][9], int column, int number)
{
    for (int i = 0; i < 9; i++){
        if (grid[i][column] == number)
            return false;
    }
    return true;
}

//checks to see if the number already exists in the specified subgrid
//returns true if the number doesn't exist in the subgrid
//returns false if the number does exist in the subgrid
bool checkSubGrid(int grid[9][9], int row, int column, int number)
{
    int gridStartRow = row - (row % 3);
    int gridStartColumn = column - (column % 3);
    for (int i = gridStartRow; i < gridStartRow + 3; i++){
        for (int j = gridStartColumn; j < gridStartColumn + 3; j++){
            if (grid[i][j] == number)
                return false;
        }
    }
    return true;
}

void printGrid(int grid[9][9])  
{  
    for (int row = 0; row < 9; row++)  
    {  
    for (int column = 0; column < 9; column++) 
            cout << grid[row][column] << " ";  
        cout << endl; 
    }  
} 

bool solveGame(int grid[9][9])
{
    int row, column;
    if (!findUnassignedCell(grid, row, column))
        return true;
    
    for (int number = 1; number <= 9; number++){
        if (checkRow(grid, row, number) && checkColumn(grid, column, number) &&
            checkSubGrid(grid, row, column, number)){
                grid[row][column] = number;
                if (solveGame(grid))
                    return true;
                grid[row][column] = 0;

        }
    }
    return false;
}


int main()
{

    int grid[9][9] = {{3, 0, 6, 5, 0, 8, 4, 0, 0},  
                      {5, 2, 0, 0, 0, 0, 0, 0, 0},  
                      {0, 8, 7, 0, 0, 0, 0, 3, 1},  
                      {0, 0, 3, 0, 1, 0, 0, 8, 0},  
                      {9, 0, 0, 8, 6, 3, 0, 0, 5},  
                      {0, 5, 0, 0, 9, 0, 6, 0, 0},  
                      {1, 3, 0, 0, 0, 0, 2, 5, 0},  
                      {0, 0, 0, 0, 0, 0, 0, 7, 4},  
                      {0, 0, 5, 2, 0, 6, 3, 0, 0}};  
    
   
    
    if (solveGame(grid))
        printGrid(grid);
    
    else
       cout << "The game has no solution" << endl;
    
    
    return 0;

}



