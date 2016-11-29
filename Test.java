import java.util.*;

class Test {
    public static void main (String [] args) {
	int first = Integer.parseInt(args[0]);
	
	Board board = new Board();
	Connect4Bot c4b = new Connect4Bot(board);

	System.out.println("Welcome to Connect 4");

	int player = 1;

	while (!board.checkBoard()) {
	    if (first == 1) {
	    
		System.out.println();
		board.printBoard();

		System.out.println();
		System.out.println("Player " + player + "'s turn");

		int [] input = new int [2];

		if (player == 1) { 
		    input = c4b.move();
		}
		else {
		    boolean flag = false;
		    Scanner kb = new Scanner(System.in);

		    while (!flag) {
			System.out.print("Please enter move: ");
			String move = kb.nextLine().trim().toUpperCase();
			if (move.length() != 2) {
			    System.out.println("Incorrect input");
			    System.out.println();
			    continue;
			}
			switch (move.charAt(0)) {
			    case 'A':
				input[0] = 0;
				flag = true;
				break;
			    case 'B':
				input[0] = 1;
				flag = true;
				break;
			    case 'C':
				input[0] = 2;
				flag = true;
				break;
			    case 'D':
				input[0] = 3;
				flag = true;
				break;
			    case 'E':
				input[0] = 4;
				flag = true;
				break;
			    case 'F':
				input[0] = 5;
				flag = true;
				break;
			    case 'G':
				input[0] = 6;
				flag = true;
				break;
			    case 'H':
				input[0] = 7;
				flag = true;
				break;
			    default:
				System.out.println("Incorrect input");
				System.out.println();
				flag = false;
				break;
			}
		    
			switch (move.charAt(1)) {
			    case '1':
				input[1] = 0;
				flag = true;
				break;
			    case '2':
				input[1] = 1;
				flag = true;
				break;
			    case '3':
				input[1] = 2;
				flag = true;
				break;
			    case '4':
				input[1] = 3;
				flag = true;
				break;
			    case '5':
				input[1] = 4;
				flag = true;
				break;
			    case '6':
				input[1] = 5;
				flag = true;
				break;
			    case '7':
				input[1] = 6;
				flag = true;
				break;
			    case '8':
				input[1] = 7;
				flag = true;
				break;
			    default:
				System.out.println("Incorrect input");
				System.out.println();
				flag = false;
				break;
			}
		    }
		}
		if (board.occupied(input))
		    System.out.println("Space taken, please try again");
		else {
		    board.move(input, player);
		    if (player == 1)
			player = 2;
		    else
			player = 1;
		}
	    }
	    else {
	    
		System.out.println();
		board.printBoard();

		System.out.println();
		System.out.println("Player " + player + "'s turn");

		int [] input = new int [2];

		if (player == 2) { 
		    input = c4b.move();
		}
		else {
		    boolean flag = false;
		    Scanner kb = new Scanner(System.in);

		    while (!flag) {
			System.out.print("Please enter move: ");
			String move = kb.nextLine().trim().toUpperCase();
			if (move.length() != 2) {
			    System.out.println("Incorrect input");
			    System.out.println();
			    continue;
			}
			switch (move.charAt(0)) {
			    case 'A':
				input[0] = 0;
				flag = true;
				break;
			    case 'B':
				input[0] = 1;
				flag = true;
				break;
			    case 'C':
				input[0] = 2;
				flag = true;
				break;
			    case 'D':
				input[0] = 3;
				flag = true;
				break;
			    case 'E':
				input[0] = 4;
				flag = true;
				break;
			    case 'F':
				input[0] = 5;
				flag = true;
				break;
			    case 'G':
				input[0] = 6;
				flag = true;
				break;
			    case 'H':
				input[0] = 7;
				flag = true;
				break;
			    default:
				System.out.println("Incorrect input");
				System.out.println();
				flag = false;
				break;
			}
		    
			switch (move.charAt(1)) {
			    case '1':
				input[1] = 0;
				flag = true;
				break;
			    case '2':
				input[1] = 1;
				flag = true;
				break;
			    case '3':
				input[1] = 2;
				flag = true;
				break;
			    case '4':
				input[1] = 3;
				flag = true;
				break;
			    case '5':
				input[1] = 4;
				flag = true;
				break;
			    case '6':
				input[1] = 5;
				flag = true;
				break;
			    case '7':
				input[1] = 6;
				flag = true;
				break;
			    case '8':
				input[1] = 7;
				flag = true;
				break;
			    default:
				System.out.println("Incorrect input");
				System.out.println();
				flag = false;
				break;
			}
		    }
		}
		if (board.occupied(input))
		    System.out.println("Space taken, please try again");
		else {
		    board.move(input, player);
		    if (player == 1)
			player = 2;
		    else
			player = 1;
		}
	    }
	}

	if (player == 1)
	    player = 2;
	else
	    player = 1;

	System.out.println();
	board.printBoard();
	System.out.println();
	System.out.println("Player " + player + " wins!");
    }
}
