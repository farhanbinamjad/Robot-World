import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RobotWorldGame extends Frame 
{
   private final int BoardDimensions = 10;
   private final int Evils = 3;
   private final int Junks = 3;
   private RobotWorldBoard board;
   private Button playbutton;
      
      
   public RobotWorldGame() 
   {
      addWindowListener(new Close());
      setSize( 640, 640 );
      setTitle( "Robot World Game " );
         
      board = new RobotWorldBoard(BoardDimensions, BoardDimensions, Junks, Evils );
      add( "Center", board );
      playbutton = new Button( "New Game" );
      playbutton.addActionListener( new playbuttonListener() );
      add( "South", playbutton );
         
   } 
   
   private class playbuttonListener implements ActionListener 
   {
      public void actionPerformed( ActionEvent e ) 
      
      {
         board.newGame(BoardDimensions, BoardDimensions, Junks, Evils);
      
      }
      
   }
   
   private class Close extends WindowAdapter 
   
   {
      public void closewindow(WindowEvent e) 
      
      {
         System.exit(0);
         
      }
      
   }

}