import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class soduku extends JFrame implements ActionListener {
    public final static int rows = 9;
    public final static int cols = 9;
    public final static int height = 450;
    public final static int width = 450;

    JTextField[][] square = new JTextField[9][9];
    Integer[][] puzzle = new Integer[9][9];
    Integer[][] solution = new Integer[9][9];

    String [] easy= new String[5];
    String [] easyKeys= new String[5];
    String [] med= new String[5];
    String [] medKeys= new String[5];
    String [] hard= new String[5];
    String [] hardKeys= new String[5];


    ImageIcon icon = new ImageIcon("winner.gif");
    final long start;

    KeyListener listen = new KeyListener()
    {
        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_C) {
                check();
            }
            else if(e.getKeyCode()==KeyEvent.VK_DOWN)
            {

            }
            else if(e.getKeyCode()==KeyEvent.VK_LEFT)
            {

            }
            else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            {
            }
            else if(e.getKeyCode()==KeyEvent.VK_UP)
            {

            }
        }
    };

    soduku(String levels) {

        for(int i=0;i<5;i++)
        {
            easy[i]="easy"+(i+1)+".txt";
        }
        for(int i=0;i<5;i++)
        {
            easyKeys[i]="easy"+(i+1)+"_key.txt";
        }
        for(int i=0;i<5;i++)
        {
            med[i]="med"+(i+1)+".txt";
        }
        for(int i=0;i<5;i++)
        {
            medKeys[i]="med"+(i+1)+"_key.txt";
        }
        for(int i=0;i<5;i++)
        {
            med[i]="hard"+(i+1)+".txt";
        }
        for(int i=0;i<5;i++)
        {
            med[i]="hard"+(i+1)+"_key.txt";
        }


        start = System.currentTimeMillis();

        Random rand=new Random();
        int num=rand.nextInt(5)+1;


        new JFrame("Sudoku Game");
        setSize(width + 50, height + 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        addKeyListener(listen);

        JPanel pane = new JPanel();
        pane.setSize(width, height);
        pane.setLayout(new GridLayout(rows, cols));
        add(pane, BorderLayout.CENTER);

        Font text = new Font("SansSerif", Font.BOLD, 20);
        Font text2 = new Font("SansSerif", Font.PLAIN, 20);

        try {
            Scanner scan = new Scanner(new FileInputStream(levels+num+".txt"));

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    puzzle[i][j] = scan.nextInt();
                }
            }

            Scanner scan2 = new Scanner(new FileInputStream(levels+num+"_key.txt"));

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    solution[i][j] = scan2.nextInt();
                }
            }

        } catch (IOException e) {
            System.out.println("Error!");
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                square[i][j] = new JTextField();
                square[i][j].setHorizontalAlignment(JLabel.CENTER);
                square[i][j].addKeyListener(listen);

                if (puzzle[i][j] == 0) {
                    square[i][j].setEditable(true);
                    square[i][j].setFont(text2);

                } else {
                    square[i][j].setEditable(false);
                    square[i][j].setFont(text);
                    square[i][j].setBackground(Color.GRAY);
                    String hi = puzzle[i][j].toString();
                    square[i][j].setText(hi);
                }

                pane.add(square[i][j]);
            }
        }
        JPanel bottom = new JPanel();
        add(bottom, BorderLayout.SOUTH);

        JButton button = new JButton("Check");
        button.setSize(25, 25);
        button.addActionListener(this);
        bottom.add(button, BorderLayout.EAST);

        JButton quit = new JButton("Give Up");
        button.setSize(25, 25);
        quit.addActionListener(this);
        bottom.add(quit, BorderLayout.WEST);

    }

    public void check() {
        boolean winner = true;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {

                if (square[x][y].isEditable() && (!(square[x][y].getText()).equals("")&&!(square[x][y].getText()).equals("c")) && Integer.parseInt(square[x][y].getText()) == solution[x][y]) {
                    square[x][y].setEditable(false);
                    square[x][y].setForeground(Color.GREEN);
                } else if (square[x][y].isEditable()) {
                    square[x][y].setForeground(Color.BLUE);
                    winner = false;
                }
            }
        }
        if (winner == true) {
            final long stop = System.currentTimeMillis();
            final long time = (stop - start) / 1000;
            String output;

            if (time > 60) {
                output = "You won in " + time / 60;

                if (time < 120) {
                    output = output + " minute!";
                } else {
                    output = output + " minutes!";
                }
            } else {
                output = "You won in " + time;

                if (time == 1) {
                    output = output + " second!";
                } else {
                    output = output + " seconds!";
                }
            }
            System.out.println(output);
            JFrame frame = new JFrame(output);

            frame.setSize(665, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JLabel label = new JLabel();
            label.setSize(250, 250);
            label.setIcon(icon);
            frame.add(label, BorderLayout.NORTH);
            frame.setVisible(true);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Check") {
            check();
        }
        else if (e.getActionCommand() == "Give Up") {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {

                    if (square[x][y].isEditable()) {
                        square[x][y].setText(solution[x][y].toString());
                        square[x][y].setEditable(false);
                        square[x][y].setForeground(Color.GREEN);
                    }
                }
            }
        }
    }

    public static void main(String [] args)
    {
        soduku s=new soduku("easy");
        s.setVisible(true);
    }

}
