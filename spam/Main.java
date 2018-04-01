package spam;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
import java.util.concurrent.TimeUnit;
import java.util.*;

class Main
{
		static Training rs = new Training("dataset/hamMessages", "dataset/spamMessages");
		static HashMap<String, Word> prints;
		static GUISpam gui;
		public static void main(String[] args) {
		
		gui = new GUISpam();
	}


	public static void train(String path){
		prints=rs.addTrainingSetFrom("dataset/"+path);
		gui.log.append("Training done.\n");
	}

	public static void test(String path){
		// String testfilepath = gui.testfile.getName();
		rs.computeProbability();
		System.out.println(prints+" "+rs.getTotalHamCount()+" "+rs.getTotalSpamCount());
		Testing t = new Testing(path, rs.readset);
		t.test();
		boolean S = t.isSpam();
		gui.showResult(S,rs.readset);
	}

	public static void resetm(){
		rs.reset(rs.readset);
	}
}