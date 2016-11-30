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

    public int[] move() {
    	this.board = alphaBeta(this.board, 3 , Integer.MIN_VALUE, Integer.MAX_VALUE, true);
    	return this.board.getLastMove();
    }
    
    public static Board max(Board a, Board b){
    	if (a.getValue() > b.getValue())
    		return a;
    	else
    		return b;
    }
    
    public static Board min(Board a, Board b){
    	if(a.getValue()< b.getValue())
    		return a;
    	else
    		return b;
    }

    public static Board alphaBeta(Board b , int depth, int alphaIn, int betaIn, boolean maxPlayer){
    	Board next = new Board();
    	int alpha = alphaIn;
    	int beta = betaIn;
    	ArrayList<Board> children = b.getChildren();
    	
    	if (depth == 0 || b.checkBoard()){ //or some terminal state
    		next = b;
    		return next;
    	}
    	if (maxPlayer){
    		next.setValue(Integer.MIN_VALUE);
    		for (Board c : children){
    			next = max(next, alphaBeta(c, depth-1, alpha,beta, false ));
    			if (alpha < next.getValue())
    				alpha = next.getValue();
    			if (beta <= alpha)
    				break;
    		}
    		return next;
    	}
    	else{
    		next.setValue(Integer.MAX_VALUE);
    		for ( Board c: children){
    			next = min(next, alphaBeta(c, depth-1, alpha, beta, true));
    			if (beta> next.getValue())
    				beta = next.getValue();
    			if (beta<=alpha)
    				break;
    		}
    		return next;
    	}
    	
    	
    
    	
    	
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
