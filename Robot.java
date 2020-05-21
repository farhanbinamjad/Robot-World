import java.awt.*;


public class Robot 
{
   private RobotWorldBoard board; 
   private Image robotImage;
   private int row;
   private int column;
   private boolean working;
   
   
   public Robot(RobotWorldBoard board, String imageFile, int r, int c) 
   {
      robotImage = Toolkit.getDefaultToolkit().getImage( imageFile );
      this.board = board;
      row = r;
      column = c;
      working = true;
   } 

   public int getRow( ) 
   {
      return row;
   } 

   public int getColumn() 
   {
      return column;
   } 

   public void move (int newRow, int newColumn ) 
   
   {
      if (working) 
      {
         row = newRow;
         column = newColumn;
      } 
   } 

   public void drawRobot( Graphics g, int width, int height, Canvas observer ) 
   {
      if (working) 
      {
         g.drawImage( robotImage, column*width+2, row*height+2, width-4, height-4,
            observer );
      } 
      else 
      {
         g.setColor(Color.red);
         g.fillRect(column*width+2,row*height+2,
            width-4, height-4);
      
      }
   } 

   public void makescrap() 
   {
      working = false;
   } 

   public boolean isWorking () 
   {
      return working;
   }

} 