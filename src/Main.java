// Class to represent a node in the Morse code tree
class MorseNode {
    char letter;  // The letter stored in this node
    MorseNode left;  // Left child for '.'
    MorseNode right;  // Right child for '-'

    // Constructor to create a new node with a given letter
    public MorseNode(char letter) {
        this.letter = letter;  // Assign the letter
        this.left = null;  // Set left child to null
        this.right = null;  // Set right child to null
    }
}

// Class to represent the Morse code tree
class MorseTree {
    MorseNode root;  // The root of the Morse code tree

    // Constructor to create an empty Morse code tree
    public MorseTree() {
        root = new MorseNode(' ');  // Start with an empty root
    }

    // Method to insert a letter into the tree using its Morse code
    public void insert(char letter, String morseCode) {
        MorseNode current = root;  // Start at the root

        // Loop through each symbol in the Morse code ('.' or '-')
        for (int i = 0; i < morseCode.length(); i++) {
            char symbol = morseCode.charAt(i);

            // If the symbol is a dot, move left
            if (symbol == '.') {
                if (current.left == null) {
                    current.left = new MorseNode(' ');  // Create an empty node if needed
                }
                current = current.left;  // Move to the left child
            }
            // If the symbol is a dash, move right
            else if (symbol == '-') {
                if (current.right == null) {
                    current.right = new MorseNode(' ');  // Create an empty node if needed
                }
                current = current.right;  // Move to the right child
            }
        }

        // Assign the letter to the final node
        current.letter = letter;
    }

    // Method to decode a Morse code string to a letter
    public char decode(String morseCode) {
        MorseNode current = root;  // Start at the root

        // Loop through each symbol in the Morse code
        for (int i = 0; i < morseCode.length(); i++) {
            char symbol = morseCode.charAt(i);

            // If it's a dot, move left
            if (symbol == '.') {
                current = current.left;
            }
            // If it's a dash, move right
            else if (symbol == '-') {
                current = current.right;
            }

            // If the current node is null, the code is invalid
            if (current == null) {
                return '?';  // Return a '?' for invalid codes
            }
        }
        return current.letter;  // Return the decoded letter
    }

    // Method to load characters and their Morse codes into the tree
    public void loadFromArrays(char[] letters, String[] morseCodes) {
        for (int i = 0; i < letters.length; i++) {
            insert(letters[i], morseCodes[i]);  // Insert each letter and its code
        }
    }

    // Method to print the Morse code tree (in order)
    public void printInOrder() {
        printInOrder(root, "");  // Start from the root
    }

    // Helper method to print the tree using in-order traversal
    private void printInOrder(MorseNode node, String morseCode) {
        if (node == null) {
            return;  // Stop if the node is null
        }

        // Print the left subtree
        printInOrder(node.left, morseCode + ".");

        // Print the current letter if it's not a blank space
        if (node.letter != ' ') {
            System.out.println(node.letter);
        }

        // Print the right subtree
        printInOrder(node.right, morseCode + "-");
    }

    // Method to reverse and decode an encoded message
    public String reverseAndDecode(String encodedMessage) {
        // Split the message into words using '/' as the separator
        String[] words = encodedMessage.split("/");

        StringBuilder decodedMessage = new StringBuilder();

        // Loop through each word
        for (String word : words) {
            // Split the word into Morse code characters using spaces
            String[] morseChars = word.trim().split(" ");

            // Decode each character and add it to the message
            for (String morseChar : morseChars) {
                if (!morseChar.isEmpty()) {
                    char decodedChar = decode(morseChar);  // Decode the Morse code
                    decodedMessage.append(decodedChar);  // Add the letter to the message
                }
            }

            // Add a space between words
            decodedMessage.append(" ");
        }

        return decodedMessage.toString().trim();  // Return the final decoded message
    }
}

// Main class to test the Morse code tree
public class Main {
    public static void main(String[] args) {
        //letters array
        char[] letters = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };

        // Array of Morse codes for each letter
        String[] morseCodes = {
                ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---",
                "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-",
                "..-", "...-", ".--", "-..-", "-.--", "--.."
        };

        // Create a MorseTree object
        MorseTree morseTree = new MorseTree();

        // Load the letters and their Morse codes into the tree
        morseTree.loadFromArrays(letters, morseCodes);

        // Example of an encoded message
        String encodedMessage = "..-. .. .-. ... - / ... --- .-.. ...- . / - .... . / .--. .-. --- -... .-.. . -- ";

        // Print the original encoded message
        System.out.println("Original Encoded Message: " + encodedMessage);

        // Decode the message
        String decodedMessage = morseTree.reverseAndDecode(encodedMessage);

        // Print the decoded message
        System.out.println("Decoded Message: " + decodedMessage);

        // Reverse the decoded message and print it
        String reversedDecodedMessage = new StringBuilder(decodedMessage).reverse().toString();
        System.out.println("Reversed Decoded Message: " + reversedDecodedMessage);

        // Print the Morse code dictionary
        System.out.println("\nMorse Code Dictionary (In-Order Traversal):");
        morseTree.printInOrder();
    }
}
