//the imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Calculate calculations.
 *
 * @author Berk Cihat Yildizcan
 * @version 1.0
 */
public class Calculator implements ActionListener { //handle button clicks
    private JFrame frame; // initializes the frame
    private JTextField output; // creates the text field
    private JButton[] numButtons = new JButton[10]; // numeric buttons
    private JButton[] functionButtons = new JButton[6]; // function buttons
    private JButton addButton, minusButton, multiButton, divButton;
    private JButton equalButton, acButton;
    private JPanel panel; //creates a panel that orders the buttons in a grid
    boolean lastKeyPressed = false; 

    Font font = new Font("Arial", Font.BOLD,30); // font for button text
    Font fontOP = new Font("Arial", Font.BOLD, 110); // font for the big zero
    double number1=0,number2=0,outcome=0;
    char operator;

    public Calculator(){ 
        frame = new JFrame("MyCalculator"); // names the window of frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allows you to press the x button on the window
        frame.setPreferredSize(new Dimension(400,500)); // sets the size of the window
        
        //calls the methods below
        createOutput();
        createFunctionButtons();
        createNumButtons();
        createPanel();
        addToFrame();

        frame.pack(); //automatically re-shapes the buttons and textfield to the window changing sizes
        frame.setVisible(true); // the frame is made visible
    }

    private void createOutput() {
        output = new JTextField(); //creates the textfield
        output.setBounds(3,3,380,140); //initializes the textfield size and location
        output.setFont(fontOP); //sets the font of the text
        output.setEditable(false); //set to not editable
        output.setHorizontalAlignment(SwingConstants.RIGHT); //the text is displayed to the right
        output.setText("0"); // sets the inital text as 0
    }

    private void createFunctionButtons() {
        //creates the function buttons and adds them to the function buttons
        addButton = new JButton("+"); 
        addButton.setForeground(Color.BLUE);

        minusButton = new JButton("-");
        minusButton.setForeground(Color.BLUE);

        multiButton = new JButton("x");
        multiButton.setForeground(Color.BLUE);

        divButton = new JButton("÷");
        divButton.setForeground(Color.BLUE);

        equalButton = new JButton("=");
        equalButton.setForeground(Color.RED);

        acButton = new JButton("AC");
        acButton.setForeground(Color.YELLOW);

        functionButtons[0] = addButton;
        functionButtons[1] = minusButton;
        functionButtons[2] = multiButton;
        functionButtons[3] = divButton;
        functionButtons[4] = equalButton;
        functionButtons[5] = acButton;
        
        //for loop through each of the buttons
        // to add things to it
        for(int i=0;i<6;i++){
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(font);
            functionButtons[i].setFocusable(false);
            functionButtons[i].setBackground(Color.WHITE);
        }
    }

    private void createNumButtons() {
        //creates the buttons numbers by how many there are
        // and adds things to the buttons at each loop so that it gets added to it
        for(int i=0;i<10;i++){
            numButtons[i] = new JButton(String.valueOf(i));
            numButtons[i].addActionListener(this);
            numButtons[i].setFont(font);
            numButtons[i].setFocusable(false);
            numButtons[i].setBackground(Color.WHITE);
        }
    }

    private void createPanel() {
        //creates panel for the buttons to be displayed
        panel = new JPanel();
        panel.setBounds(3,145,380,315); // adds size to the panel
        panel.setLayout(new GridLayout(4,4, 0, 0)); // structures it 4 by 4
        panel.setBackground(Color.GRAY); //makes the background color gray
        
        //adds the number buttons and operator buttons to the panel
        panel.add(numButtons[7]);
        panel.add(numButtons[8]);
        panel.add(numButtons[9]);
        panel.add(divButton);
        panel.add(numButtons[4]);
        panel.add(numButtons[5]);
        panel.add(numButtons[6]);
        panel.add(multiButton);
        panel.add(numButtons[1]);
        panel.add(numButtons[2]);
        panel.add(numButtons[3]);
        panel.add(minusButton);
        panel.add(acButton);
        panel.add(numButtons[0]);
        panel.add(equalButton);
        panel.add(addButton);
    }

    private void addToFrame() {
        //adds the panel and output to the frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(output, BorderLayout.NORTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //where buttons display its expected outcome on the output 
        JButton clickedButton = (JButton)e.getSource();

        // get text of button
        String text = clickedButton.getText();
        
        //sets output as nothing if after pressing the buttons
        // so that initial 0 does not get added and disappears after pressing on
        // a number
        if(output.getText().equals("0")){
            output.setText("");
        }
        
        if(text.matches(".*\\d.*")){ //checks if text of clicked button contains any digits
            if(lastKeyPressed == true){ //if true, check if last key pressed was operator key
                output.setText(text); //set to current digit, replacing any other digit entered
                lastKeyPressed = false; //last key set to false, last key pressed was an operator key
            } else{
                output.setText(output.getText() + text);
                //if not operator key, current input text is appended to the exisitng text in output field
            }
        }

        if(text.matches("[x|÷|+|-]")){ //if the text matches the given operator key
            output.setText(text); // set the operator to the output
            operator = text.charAt(0); //assigns first character of operator key to operator
            lastKeyPressed = true; // sets last key pressed to true
                            // means last key pressed was operator key
        }
        if(!text.matches("[x|÷|+|-]")){ // if text does not match the operator
            lastKeyPressed = false; // sets last key to false
        }
        if(text.matches("=")) { //if text mathes equal sign
            //initializes number1 and number 2 
            // converts the text in the output field into a numerical value
            number1 = Double.parseDouble(output.getText());
            number2 = Double.parseDouble(output.getText());
            switch(operator){ // switch statement
                case 'x': // if operator is x
                    outcome = number1 * number2; //multiply it with each other
                    break; // break
                case '÷': // if operator is ÷
                    outcome = number1/number2; //divide it by each other
                    break; // break
                case '+': // if operator is +
                    outcome = number1+number2; //add it with each other
                    break; // break
                case '-': //if operator is -
                    outcome = number1-number2; //minus it by each other
                    break; //break
            }
            //sets the result to the text field so its visible
            output.setText(String.valueOf(outcome)); 
            number1 = outcome; //value is updated to the outcome
        }

        if(text.matches("AC")){ //if text matches AC 
            output.setText("0"); //set the output as 0, to refresh it
        } 
    }


    public static void main(String[] args) {
        //creates new instance of Calculator class and assigns it to the variable cal
        Calculator cal = new Calculator();
    }
}
