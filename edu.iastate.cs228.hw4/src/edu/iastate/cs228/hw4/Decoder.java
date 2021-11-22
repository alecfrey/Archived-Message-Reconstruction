package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Message decoder main method.
 * Reads in encoded message and creates a MsgTree, then prints character codes, the message, and the statistics.
 * 
 * Main method throws FileNotFoundException if user inputed file is not found.
 * 
 * @author Alec Frey
 *
 */
public class Decoder {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter filename to decode:");
		String fileName = s.nextLine();
		s.close();

		File f = new File(fileName);
		
		Scanner scanFile = new Scanner(f);
		Scanner scanChecker = new Scanner(f);
		String encodedMessage = "";
		
		while (scanChecker.nextLine().contains("^")) {			
			encodedMessage += scanFile.nextLine();
			encodedMessage += "\n";
		}
		scanChecker.close();
				
		String archivedMessage = scanFile.nextLine();
		scanFile.close();

		MsgTree tree = new MsgTree(encodedMessage);
		
		System.out.println("character code");
		System.out.println("-------------------------");
		MsgTree.printCodes(tree, "");
		
		System.out.println();
		System.out.println("MESSAGE:");
		tree.decode(tree, archivedMessage);
		
		System.out.println();
		System.out.println();
		System.out.println("STATISTICS:");
		
		double avgNumOfBits = (1.0 * archivedMessage.length()) / tree.getNumCharacters();
		System.out.printf("Avg bits/char:        %.1f", avgNumOfBits);
		System.out.println();
		System.out.println("Total characters:     " + tree.getNumCharacters());
		
		double spaceSavingsCalculation = (1.0 - (avgNumOfBits / 16.0)) * 100.0;
		System.out.printf("Space savings:        %.1f", spaceSavingsCalculation);
		System.out.println("%");
	}
}