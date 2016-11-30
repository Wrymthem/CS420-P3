import java.util.*;

class Connect4Bot {
    private Board board;
    private ArrayList <Board> possibleBoards;

    Connect4Bot(Board board) {
	this.board = board;
	this.possibleBoards = new ArrayList <Board> ();
    }

    public void getChildren() {
	for (int i = 0; i < this.board.getBoard().length; i++) {
	    for (int j = 0; j < this.board.getBoard().length; j++) {
		if (!this.board.occupied(i, j)) {
		    this.possibleBoards.add(new Board(this.board, i, j));
		}
	    }
	}
    }

    public Board move() {
	getChildren();
	Board minBoard = getMin();
    }

    public void updateBoard(Board board) {
	this.board = board;
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
