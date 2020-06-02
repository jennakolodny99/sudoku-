import java.awt.*;
import javax.swing.*;
import javax.swing.*;
import java.awt.event.*;

//Cell class represents a single cell in the sudoku grid

public class Cell extends JTextField {
    
    private int value;
    
    //default value for a cell is -1
    public Cell(){
        this.value = -1;
          
    }

    public Cell(int value){
        setValue(value);
        
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static void main(String[] args){
        
   }
}