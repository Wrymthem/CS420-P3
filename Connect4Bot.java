import java.util.*;

class Connect4Bot {
    private Board board;
    private ArrayList <Board> possibleBoards;
    private static int nodesChecked =0;
    private static Long start;
    private static Hashtable<Integer, Board> memo;

    Connect4Bot(Board board) {
	this.board = board;
	this.possibleBoards = new ArrayList <Board> ();
	memo = new Hashtable<Integer, Board>();
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
    	Board temp=null;
    	Board previous=null;
        start = System.currentTimeMillis();
    	
    	for(int i=1;i<64-this.board.getTurn();i++){
    		previous = temp;
	    	if ((System.currentTimeMillis()- start)> 5000){
					break;
			}
	        temp = alphaBeta(this.board, i , Integer.MIN_VALUE, Integer.MAX_VALUE, true);
	        System.out.println("Depth:" +i);
	    }
    	Hashtable<Integer, Board> t = new Hashtable<Integer, Board>(); 
    	t= memo;
    	System.out.println("Table Size: " +t.size());
    	this.board = max(previous,temp);
    	System.out.println(this.board.getValue());
    	System.out.println(nodesChecked);
    	nodesChecked =0;
    	return this.board.getLastMove();
    	
    	
    }
    
    public static Board max(Board a, Board b){
    	if (a.getValue() > b.getValue())
    		return a;
    	else
    		return b;
    }
    public static int max(int a, int b){
    	if (a > b)
    		return a;
    	else
    		return b;
    }
    public static int min(int a, int b){
    	if (a < b)
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
    	if(depth == 0){
    		next = b;
    		next.setAlpha(b.getValue());
    		next.setBeta(b.getValue());
    		memo.put(next.getHashCode(), next);
    		return next;
    	}
    	if (memo.containsKey(b.getHashCode())&&(memo.get(b.getHashCode()).getTurn()>=depth+1)){
    		alpha = max(alpha, memo.get(b.getHashCode()).getValue());
    		beta = min(beta, memo.get(b.getHashCode()).getValue());
    		if (alpha<=beta)
    			return memo.get(b.getHashCode());
    	}
    	if ( b.checkBoard()|| b.getValue() == Integer.MIN_VALUE || b.getValue() == Integer.MAX_VALUE){ //or some terminal state
    		
    		next = b;
    		memo.put(next.getHashCode(), next);
    		return next;
    	}
    	
    	ArrayList<Board> children = b.getChildren();
    	if (maxPlayer){
    	
    		children.sort(null);
    		next.setValue(Integer.MIN_VALUE);
    		for (Board c : children){
    			nodesChecked++;
    			next = max(next, alphaBeta(c, depth-1, alpha, beta, false ));
    			if (alpha < next.getValue())
    				alpha = next.getValue();
    			if (beta <= alpha)
    				break;
    			if ((System.currentTimeMillis()- start)> 5000){
    				break;
    			}
    		}
    		next.setAlpha(alpha);
    		next.setBeta(beta);
    		memo.put(next.getHashCode(), next);
    		return next;
    	}
    	else{
    		
    		children.sort(null);
    		next.setValue(Integer.MAX_VALUE);
    		for ( Board c: children){
    			nodesChecked++;
    			next = min(next, alphaBeta(c, depth-1, alpha, beta, true));
    			if (beta> next.getValue())
    				beta = next.getValue();
    			if (beta<=alpha)
    				break;
    			if ((System.currentTimeMillis()- start)> 5000){
    				break;
    			}
    		}
    		next.setBeta(beta);
    		next.setAlpha(alpha);
    		memo.put(next.getHashCode(), next);
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
