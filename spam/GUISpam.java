package spam;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import java.util.concurrent.TimeUnit;
import java.util.*;



class GUISpam extends JPanel implements ActionListener{

	
	JButton evaluate,openTestFile, reset,openTrain,trainb;
	JFileChooser chooseSpam, chooseHam, chooseTestFile, chooseTrain;
	static JTextArea log;
	File filetrain,testfile;
	Boolean isSpam;
	static JFrame frame;

	public  GUISpam() {

		
		isSpam=false;
		log = new JTextArea(5,41);
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);
   

    

		JFrame frame = new JFrame("Spam Filter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(1000,600);	
		frame.setContentPane(new JLabel(new ImageIcon("resource/spam.jpg")));
		frame.setVisible(true);

		try{
		TimeUnit.SECONDS.sleep(2);
		}
		catch(Exception e){
			System.out.println("Exception: "+e);
		}

		frame.setBackground(new Color(70, 136, 160));		

		JLabel displaymsg = new JLabel("Browse a folder to train: ");

		JPanel trainContentPanel = new JPanel();
		//frame.setContentPane(trainContentPanel);
		// trainContentPanel.setSize(new Dimension(100, 100));
		trainContentPanel.setLocation(270,200);
		trainContentPanel.setBorder(BorderFactory.createLineBorder(Color.black));


		JPanel openTrainpanel = new JPanel();
		
		// openTrainpanel.setBounds(500,300,55,55);

		openTrain = new JButton("Browse");
		// openTrain.setBounds(500,300,1,1); 
		
		openTrain.addActionListener(this);
		openTrainpanel.add(displaymsg);
		openTrainpanel.add(openTrain);
		openTrainpanel.setBackground(new Color(150, 156, 158));

		trainContentPanel.add(openTrainpanel);
		trainb = new JButton("Train");
		trainb.addActionListener(this);
		trainContentPanel.add(trainb);

		JPanel Outputlog = new JPanel();
		Outputlog.setBorder(BorderFactory.createLineBorder(Color.black));
		Outputlog.setSize(new Dimension(500,200));
		Outputlog.setLocation(trainContentPanel.getX(),trainContentPanel.getY()+50);
		Outputlog.add(logScrollPane);
		// trainContentPanel.add(trainb);
		
		
		

		
		JPanel testContentPanel = new JPanel();
		testContentPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		// testContentPanel.setSize(new Dimension(300,200));
		testContentPanel.setLocation(Outputlog.getX()+100,Outputlog.getY()+200);

		JLabel displaytmsg = new JLabel("Browse a file to evaluate: ");


		testContentPanel.add(displaytmsg);		
		openTestFile= new JButton("Browse");
		openTestFile.addActionListener(this);
		testContentPanel.add(openTestFile);

		reset = new JButton("Reset");
		reset.addActionListener(this);
		evaluate=new JButton("Evaluate");
		evaluate.addActionListener(this);
		testContentPanel.add(evaluate);
		testContentPanel.add(reset);



		JPanel mainPanel = new JPanel();
		mainPanel.setSize(new Dimension(480, 200));
		mainPanel.setLocation(trainContentPanel.getX()-20,trainContentPanel.getY()-10);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		trainContentPanel.setBackground(new Color(137, 131, 142));
		Outputlog.setBackground(new Color(137, 131, 142));
		testContentPanel.setBackground(new Color(137, 131, 142));
		mainPanel.add(trainContentPanel,BorderLayout.PAGE_START);
		mainPanel.add(Outputlog,BorderLayout.CENTER);
		mainPanel.add(testContentPanel,BorderLayout.PAGE_END);
		mainPanel.setBackground(new Color(62,57,68));
		frame.add(mainPanel);
		frame.setVisible(true);
		// this.chooseSpam = new JFileChooser()
		// frame.add(background);
		//frame.add(displaymsg);

		
	}
	
	public void actionPerformed(ActionEvent e) {

		chooseTrain = new JFileChooser();
		chooseTestFile = new JFileChooser();
		chooseTrain.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
 
        //Handle open button action.
        if (e.getSource() == openTrain) {
            int returnVal = chooseTrain.showOpenDialog(GUISpam.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                filetrain = chooseTrain.getSelectedFile();
                //This is where a real application would open the file.
                log.append("Opening: " + filetrain.getName() + "." + "\n");
            } else {
                log.append("Open command cancelled by user." + "\n");
            }

            
        }    // log.setCaretPosition(log.getDocument().getLength());
        
        if(e.getSource() == trainb){

        	// log.append("Training done.\n");	
        	Main m = new Main();
        	if(filetrain!=null){
            	m.train(filetrain.getName());
            } else 
            {
            	log.append("No folder browsed to train.\n");
        		JOptionPane.showMessageDialog(frame,"No folder browsed to train!!","Error",JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if(e.getSource() == openTestFile){
        	int returnVal = chooseTestFile.showOpenDialog(GUISpam.this);
        	if(returnVal==JFileChooser.APPROVE_OPTION){
        		testfile=chooseTestFile.getSelectedFile();
        		log.append("Opening: "+testfile.getAbsolutePath()+".\n");
        	}else {
        		log.append("Training not done." + "\n");
        	}
        	
        }

        if(e.getSource()==reset){
        	log.append("reset\n");
        	Main m = new Main();
        	m.resetm();
        	log.append("Reset on Training data done.\n");
        }

        if(e.getSource()==evaluate){
        	Main m = new Main();
        	if(testfile!=null)
        	{
        		m.test(testfile.getAbsolutePath());
        	} else
        	{
        		log.append("No file browsed to evaluate.\n");
        		JOptionPane.showMessageDialog(frame,"No file browsed to evaluate!!","Error",JOptionPane.INFORMATION_MESSAGE);
        	}


        }

 
         
    }

    public static void showGUI(){
    	GUISpam gui = new GUISpam();
    }

    public static void showResult(boolean S,HashMap<String, Word> readset)
    {
    	Evaluator e = new Evaluator(readset);
    	e.evaluate();
    	String result=e.printResult();
    	if(readset.isEmpty()){
    		log.append("No Trained data available\n");
	    	JOptionPane.showMessageDialog(frame,"No Trained data available!!","OUTPUT",JOptionPane.INFORMATION_MESSAGE);
    	} else 
    	{
	    	if(S)
	    	{
	    		// frame.setBackground(new Color(255,0,0));
	    		log.append("File tested is found to be spam.\nEvaluation Report:\n"+result+"\n");
	    		JOptionPane.showMessageDialog(frame,"File tested is SPAM\nEvaluation Report:\n"+result,"OUTPUT",JOptionPane.INFORMATION_MESSAGE);
	    		// frame.setVisible(true);
	    	} else 
	    	{
	    		// this.frame.setBackground(new Color(0,255,0));
	    		log.append("File Tested is found to be ham.\n");
	    		JOptionPane.showMessageDialog(frame,"File tested is HAM\nEvaluation Report:\n"+result,"OUTPUT",JOptionPane.INFORMATION_MESSAGE);
	    		// this.frame.setVisible(true);
	    	}
	    }

    }
   
}



