import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * MainFrame class
 * This class contains the Graphical User Interface for the Word Ladder Application
 * 
 * @version 2.0
 * @author Sebastian Szczepaniak
 *
 */
public class MainFrame extends JFrame implements ActionListener{
	
	private Generation generation;
	private JPanel mainPanel;
	JButton generate, discovery, run;
	JTextField genWord, genNumber, disInWord, disFinWord;
	private JLabel genWordLabel, genNumberLabel, disInWordLabel, disFinWordLabel;
	
	/**
	 * Constructor of the class Generation. It sets the details of the frame and what will be in there.
	 * @param gen Model class with all the methods needed to run the application.
	 */
	public MainFrame(Generation gen){
		generation = gen;
		mainPanel = new JPanel();
		initialisePanel();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(mainPanel);
		this.setSize(340,260);
		this.setResizable(false);
		this.setLocation(200, 200);
		this.setTitle("Word Ladder");
		this.setVisible(true);
	}
	
	/**
	 * The method which adds and locates all the components to the JPanel.
	 */
	private void initialisePanel(){
		//Buttons Generate and Discovery
		generate = new JButton("Generate");
		generate.setPreferredSize(new Dimension(150,70));
		generate.addActionListener(this);
		mainPanel.add(generate);
		discovery = new JButton("Discovery");
		discovery.setPreferredSize(new Dimension(150,70));
		discovery.addActionListener(this);
		mainPanel.add(discovery);
		
		// Labels and TextFields
		genWordLabel = new JLabel("Enter Word:");
		genWordLabel.setPreferredSize(new Dimension(150,15));
		mainPanel.add(genWordLabel);
		
		disInWordLabel = new JLabel("Initial Word:");
		disInWordLabel.setPreferredSize(new Dimension(150,15));
		mainPanel.add(disInWordLabel);
		
		genWord = new JTextField();
		genWord.setText("");
		genWord.setPreferredSize(new Dimension(150,30));
		genWord.addActionListener(this);
		genWord.setEnabled(false);
		mainPanel.add(genWord);
		
		disInWord = new JTextField();
		disInWord.setText("");
		disInWord.setPreferredSize(new Dimension(150,30));
		disInWord.addActionListener(this);
		disInWord.setEnabled(false);
		mainPanel.add(disInWord);
		
		genNumberLabel = new JLabel("Number of words:");
		genNumberLabel.setPreferredSize(new Dimension(150,15));
		mainPanel.add(genNumberLabel);
		disFinWordLabel = new JLabel("Final Word:");
		disFinWordLabel.setPreferredSize(new Dimension(150,15));
		mainPanel.add(disFinWordLabel);
		
		genNumber = new JTextField();
		genNumber.setText("");
		genNumber.setPreferredSize(new Dimension(150,30));
		genNumber.addActionListener(this);
		genNumber.setEnabled(false);
		mainPanel.add(genNumber);
		
		disFinWord = new JTextField();
		disFinWord.setText("");
		disFinWord.setPreferredSize(new Dimension(150,30));
		disFinWord.addActionListener(this);
		disFinWord.setEnabled(false);
		mainPanel.add(disFinWord);
		
		//Run Button
		run = new JButton("Run!");
		run.setPreferredSize(new Dimension(300,20));
		run.setEnabled(false);
		run.addActionListener(this);
		mainPanel.add(run);
	}
	
	/**
	 * Method which is handling the users action made in GUI.
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equals("Generate")){
			disInWord.setEnabled(false);
			disFinWord.setEnabled(false);
			genWord.setEnabled(true);
			genNumber.setEnabled(true);
			run.setEnabled(true);
		}
		if(actionCommand.equals("Discovery")){
			genWord.setEnabled(false);
			genNumber.setEnabled(false);
			disInWord.setEnabled(true);
			disFinWord.setEnabled(true);
			run.setEnabled(true);
		}
		if(actionCommand.equals("Run!")){
			if(genWord.isEnabled()){
				if(!(genWord.getText().equals("") || genNumber.equals(""))){
					String initialWord = genWord.getText();
					String number = genNumber.getText();
					int num = 0;
					try{
						num = Integer.parseInt(number);
					}catch(NumberFormatException ex){
						System.err.println("You should put an Integer as a number of words!");
						JOptionPane.showMessageDialog(null,"You should put an Integer as a number of words!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null,generation.runGeneration(initialWord, num), "Generation", JOptionPane.PLAIN_MESSAGE);
				}else
					JOptionPane.showMessageDialog(null,"Both fields must be filled to run the generation!", "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				if(!(disInWord.getText().equals("") || disFinWord.equals(""))){
					String initialWord = disInWord.getText();
					String finalWord = disFinWord.getText();
					if(initialWord.length()==finalWord.length()){
						JOptionPane.showMessageDialog(null,generation.runFindPath(initialWord, finalWord), "Discovery", JOptionPane.PLAIN_MESSAGE);
					}else{
						System.err.println("You should type the words of the same length!");
						JOptionPane.showMessageDialog(null,"You should type the words of the same length!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}else
					JOptionPane.showMessageDialog(null,"Both fields must be filled to discover the path!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}
