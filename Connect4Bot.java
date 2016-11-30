import java.util.*;

class Connect4Bot {
    private Board board;
    private ArrayList <Board> possibleBoards;

    Connect4Bot(Board board) {
	this.board = board;
	this.possibleBoards = new ArrayList <Board> ();
    }

    public void getChildren() {
	for (int i = 0; i < this.board.length; i++) {
	    for (int j = 0; j < this.board.length; j++) {
		if (!this.board.occupied(i, j)) {
		    possibleBoards.add(new Board(this.board, i, j));
		}
	    }
	}
    }

    public void updateBoard(Board board) {
	this.board = board;
    }
    
    public int[] move() {
	updateWeights();

	ArrayList <int[]> moves = new ArrayList <int[]> ();
	int max = Integer.MIN_VALUE;

	for (int i = 0; i < weights.length; i++) {
	    for (int j = 0; j < weights.length; j++) {
		if (weights[i][j] > max) {
		    max = weights[i][j];
		    moves.clear();
		    int [] temp = {i, j};
		    moves.add(temp);
		}
		if (weights[i][j] == max) {
		    int [] temp = {i, j};
		    moves.add(temp);
		}
	    }
	}

	Random r = new Random();

	return moves.get(r.nextInt(moves.size()));
    }

    public void updateWeights() {
	for (int i = 0; i < weights.length; i++) {
	    for (int j = 0; j < weights.length; j++) {
		if (this.board.getBoard()[i][j] == 0)
		    weights[i][j] = 5;
		else
		    weights[i][j] = Integer.MIN_VALUE;
	    }
	} 
    }
    
    public int [][] deepCopy(int [][] board) {
	int [][] temp = new int[board.length][board.length];
	for (int i = 0; i < board.length; i++)
	    for (int j = 0; j < board.length; j++)
		temp[i][j] = board[i][j];
	return temp;
    }

    /*
    public static void main (String [] args) {
	Board board = new Board();

	Connect4Bot c4b = new Connect4Bot(board);

	int player = 1;

	while (!board.checkBoard()) {
	    c4b.updateBoard(board);
	    board.move(c4b.move(), player);
	    if (player == 1)
		player = 2;
	    else
		player = 1;
	}
	
	if (player == 1)
	    player = 2;
	else
	    player = 1;

	board.printBoard();
	System.out.println();
	System.out.println("Player " + player + " wins!");
    }
    */
}
