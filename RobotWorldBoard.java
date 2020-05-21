import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferStrategy;

public class RobotWorldBoard extends Canvas
   
{
   private final int free = 0;
   private final int goodones = 1;
   private final int bad = 2;
   private final int portal = 3;
   private final int junk = 4;

   private int numRows;
   private int numCols;
   private Robot goodRobot;
   private Robot [] evilRobots;
   private Robot [] junkPiles;
   private int AliveEvilRobots;
   private int clickX;
   private int clickY;
   private int [] [] board;

   
   public RobotWorldBoard(int rows,int columns, int junks, int evils) 
   {
   
      newGame(rows, columns, junks, evils);
      addMouseListener( new MouseKeeper() );
   } 
       
   public void newGame ( int rows, int columns, int junks, int evils) 
   {
      int row;
      int column;
      numRows = rows;
      numCols = columns;
      board = new int [numRows][numCols];
      for (int r = 0; r < board.length; r++ ) 
            
      {
         for (int c = 0; c < board[0].length; c++ ) 
                  
         {
            board[r][c] = free;
                     
         } 
                  
      } 
   
            
      board[0][numCols-1] = portal;
      board[0][0] = portal;
      board[numRows-1][numCols-1] = portal;
      board[numRows-1][0] = portal;
      do 
      {
         row = (int) (Math.random() * numRows);
         column = (int) (Math.random() * numCols);
      } 
            while (board[row][column] != free);
      goodRobot = new Robot( this, "RobotImage.png", row, column);
      board[row][column] = goodones;
      evilRobots = new Robot [evils];
      for (int r = 0; r < evils; r++) 
      {
         do 
         {
            row = (int) (Math.random() * numRows);
            column = (int) (Math.random() * numCols);
         } 
                  while (board[row][column] != free);
         evilRobots[r] = new Robot( this, "EvilRobot.png", row, column);
      } 
      AliveEvilRobots = evils;
      junkPiles = new Robot [junks];
      for (int r = 0; r < junks; r++) 
      {
         do 
         {
            row = (int) (Math.random() * numRows);
            column = (int) (Math.random() * numCols);
         } 
                  while (board[row][column] != free);
         junkPiles[r] = new Robot( this, "EvilRobot.png", row, column);
         board[row][column] = junk;
      } 
      repaint();
   } 

   private void printBoard() 
   {
      System.out.println("");
      for (int r = 0; r < board.length; r++ ) 
      {
         System.out.print("row " + r + ": ");
         for (int c = 0; c < board[0].length; c++ ) 
         {
            System.out.print(" " + board[r][c]);
         } 
         System.out.println("");
      } 
   } 
 
   public void paint(Graphics g) 
   {
      drawGrid(g);
      makescrap(g);
      MakeGoodRob(g);
      MakeEvilRob(g);
   } 

   private void drawGrid(Graphics g) 
   {
      int width = getWidth();
      int height = getHeight();
      int rowHeight = height / numRows;
      int columnWidth = width / numCols;
           
      for (int c = 0; c <= board[0].length; c++ ) 
      {
         g.drawLine(c * columnWidth, 0, c * columnWidth, height);
      } 
      for (int r = 0; r <= board.length; r++ ) 
      {
         g.drawLine(0, r * rowHeight, width, r * rowHeight);
      } 
   
   } 
 
   private void MakeGoodRob(Graphics g) 
   {
      int width = getWidth();
      int height = getHeight();
      int rowHeight = height / numRows;
      int columnWidth = width / numCols;
     
      goodRobot.drawRobot(g, columnWidth, rowHeight, this);
   
   } 
 
   private void MakeEvilRob(Graphics g) 
   {
      int width = getWidth();
      int height = getHeight();
      int rowHeight = height / numRows;
      int columnWidth = width / numCols;
       
      for (int r = 0; r < evilRobots.length; r++ ) 
      {
         evilRobots[r].drawRobot(g, columnWidth, rowHeight, this);
      } 
   
   } 
 
   private void makescrap(Graphics g) 
   {
      int width = getWidth();
      int height = getHeight();
      int rowHeight = height / numRows;
      int columnWidth = width / numCols;
      g.setColor (Color.pink);
            
      for (int r = 0; r < junkPiles.length; r++ ) 
      {
         
         g.fillRect(junkPiles[r].getColumn()*columnWidth+2,
                  junkPiles[r].getRow()*rowHeight+2,
                  columnWidth-4, rowHeight-4);
      } 
   
   } 
   
 
   private class MouseKeeper extends MouseAdapter 
   {
      public void mousePressed( MouseEvent e ) 
      {
         if (!goodRobot.isWorking()) 
         {
            return;
         }
         int width = getWidth();
         int height = getHeight();
      
         int rowHeight = height / numRows;
         int columnWidth = width / numCols;
      
         clickX = e.getX();
         clickY= e.getY();
      
         int row = clickY/rowHeight;
         int column = clickX/columnWidth;
                  

         if (Math.abs(goodRobot.getRow()-row) <= 1 && 
                  Math.abs(goodRobot.getColumn()-column) <= 1) 
         {
            if (board[row][column] == junk) 
            {
               goodRobot.move(row, column);
            
               goodRobot.makescrap();
               repaint();
               return;
            } 
                        
            if ((row == 0 && column == 0) || (row == 0) && (column == numCols-1) ||
                        (row == numRows-1 && column == 0) || (row == numRows-1) && (column ==
                        numCols-1)) 
            {
               do    
               {
                  row = (int) (Math.random() * numRows);
                  column = (int) (Math.random() * numCols);
               } 
                              
                              while (board[row][column] != free);
            
            } 
            board[row][column] = free;
            goodRobot.move( row, column );
            board[row][column] = goodones;
            moveEvilRobots(row, column);
         } 
      
         repaint();
      } 
   
   
      private void moveEvilRobots( int goodRobotRow, int goodRobotColumn ) 
      {  
            

         for (int r = 0; r < evilRobots.length; r++ ) 
         {
            int nextEvilRobotRow;
            int nextEvilRobotColumn;
            if (evilRobots[r].isWorking()) 
            {
               if (goodRobotColumn < evilRobots[r].getColumn()) 
               {
                  nextEvilRobotColumn = evilRobots[r].getColumn()-1;
               } 
               else if (goodRobotColumn > evilRobots[r].getColumn()) 
               {
                  nextEvilRobotColumn = evilRobots[r].getColumn()+1;
               } 
               else 
               {
                  nextEvilRobotColumn = goodRobotColumn;
               } 
            
               if (goodRobotRow < evilRobots[r].getRow()) 
               {
                  nextEvilRobotRow = evilRobots[r].getRow()-1;
               } 
               else if (goodRobotRow > evilRobots[r].getRow()) 
               {
                  nextEvilRobotRow = evilRobots[r].getRow()+1;
               } 
               else 
               {
                  nextEvilRobotRow = goodRobotRow;
               } 
               board[evilRobots[r].getRow()][evilRobots[r].getColumn()] = free;
               board[nextEvilRobotRow][nextEvilRobotColumn] = bad;
               evilRobots[r].move(nextEvilRobotRow,
                        nextEvilRobotColumn);
               if (nextEvilRobotRow == goodRobotRow &&
                        nextEvilRobotColumn == goodRobotColumn) 
               {
                  goodRobot.makescrap();
               
               } 
            } 
         } 
            
         checkForCollisions();
      
      } 
      
      private void checkForCollisions() 
      {
         for (int r = 0; r < evilRobots.length; r++ ) 
         {
            for (int test = r+1; test < evilRobots.length; test++ ) 
            {
               if (evilRobots[r].getRow() == evilRobots[test].getRow() &&
                        evilRobots[r].getColumn() == evilRobots[test].getColumn()) 
               {
                  if (evilRobots[r].isWorking()) 
                  {
                     evilRobots[r].makescrap();
                     board[evilRobots[r].getRow()][evilRobots[r].getColumn()] = junk;
                     AliveEvilRobots--;
                  } 
                  if (evilRobots[test].isWorking()) 
                  {
                     evilRobots[test].makescrap();
                     board[evilRobots[test].getRow()][evilRobots[test].getColumn()] = junk;
                     AliveEvilRobots--;
                  } 
               } 
            } 
         } 
         for (int r = 0; r < evilRobots.length; r++ ) 
         {
            for (int j = 0; j < evilRobots.length; j++ ) 
            {
               if (evilRobots[r].getRow() == junkPiles[j].getRow() &&
                        evilRobots[r].getColumn() == junkPiles[j].getColumn()) 
               {
                  board[evilRobots[r].getRow()][evilRobots[r].getColumn()] = junk;
                  evilRobots[r].makescrap();
                  AliveEvilRobots--;
               } 
            } 
         } 
      } 
   }
}