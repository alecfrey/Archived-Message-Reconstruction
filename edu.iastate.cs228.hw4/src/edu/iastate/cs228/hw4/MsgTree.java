package edu.iastate.cs228.hw4;

/**
 * Public class that constructs a tree to assist in decoding the given message in the .arch files.
 * 
 * Decoder class contains the main method.
 * 
 * @author Alec Frey
 *
 */
public class MsgTree {
	
	/**
	 * Public Node character.
	 */
	public char payloadChar;
	
	/**
	 * Public MsgTree object to hold left side of tree.
	 */
	public MsgTree left;
	
	/**
	 * Public MsgTree object to hold right side of tree.
	 */
	public MsgTree right;
	
	/**
	 * Private integer that tracks the location within the tree string. 
	 */
	private static int staticCharIdx = 0;
	
	/**
	 * Private integer that tracks number of characters in decoded message.
	 */
	private static int numChar = 0;
	
	/**
	 * Constructor used for building the tree from a string.
	 * 
	 * @param encodingString
	 */
	public MsgTree(String encodingString) {
		if (encodingString.charAt(staticCharIdx) == '^') {
			staticCharIdx++;
			left = new MsgTree(encodingString);
			right = new MsgTree(encodingString);
			
		} else {
			payloadChar = encodingString.charAt(staticCharIdx);
			staticCharIdx++;
		}
	}
	
	/**
	 * Constructor used for building the tree from a single node with null children.
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.left = null;
		this.right = null;
		this.payloadChar = payloadChar;
	}
	
	/**
	 * Method to print the characters and their binary codes.
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code) {
		if (root.payloadChar != '^' && root.payloadChar != '\0') {
			if (root.payloadChar == '\n') {
				String newLine = "\\n";
				System.out.println("   " + newLine + "     " + code);
			} else {
				System.out.println("   " + root.payloadChar + "      " + code);
			}
	    }
		
		if (root.left != null) {
			printCodes(root.left, code + "0");
		}
		
		if (root.right != null) {
			printCodes(root.right, code + "1");
		}
	}
	
	/**
	 * Decodes message and prints after the printCodes method.
	 * @param codes
	 * @param msg
	 */
	public void decode(MsgTree codes, String msg) {
		MsgTree tempTree = codes;
		int msgLength = msg.length();
		
		int i = 0;
		while (i < msgLength) {
			while(tempTree.left != null && tempTree.right != null) {
				if (msg.charAt(i) == '0' ) {
					tempTree = tempTree.left;
				} else if (msg.charAt(i) == '1') {
					tempTree = tempTree.right;
				}
				i++;
			}
			System.out.print(tempTree.payloadChar);
			tempTree = codes;
			numChar++;
		}
	}
	
	/**
	 * Returns number of characters in decoded message.
	 * Used for statistics.
	 * 
	 * @return numChar
	 */
	public int getNumCharacters() {
		return numChar;
	}
}
