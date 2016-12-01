import java.util.*;

class Connect4Bot {
    private Board board;
    private ArrayList <Board> possibleBoards;
    //private static int nodesChecked =0;
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
    
        start = System.currentTimeMillis();
    	
    	for(int i=1;i<2;i++){	
	    	/*if ((System.currentTimeMillis()- start)> 5000){
				break;
			}*/
	    	if (this.board.getPlayer() == 1)
	    		temp = alphaBeta(this.board, i , Integer.MIN_VALUE, Integer.MAX_VALUE, true);
	    	else
	    		temp = alphaBeta(this.board, i , Integer.MIN_VALUE, Integer.MAX_VALUE, false);
			System.out.println("Depth:" +i);
			if (i == 12) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	
    	/* TESTING
    	Hashtable<Integer, Board> t = new Hashtable<Integer, Board>(); 
    	t= memo;
    	System.out.println("Table Size: " +t.size());
    	System.out.println(this.board.getValue());
    	System.out.println(nodesChecked);
    	nodesChecked =0;
    	*/
    	this.board = temp;
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
    	ArrayList<Board> children = b.getChildren();
    	
    	 //sorting the children helps a lot
    	if (memo.containsKey(b.getHashCode())&&(memo.get(b.getHashCode()).getTurn()>=depth+1)){
    		alpha = max(alpha, memo.get(b.getHashCode()).getValue());
    		beta = min(alpha, memo.get(b.getHashCode()).getValue());
    		if (alpha>=beta)
    			return memo.get(b.getHashCode());
    	}
    	
    	if (depth == 0 || b.checkBoard()){ //or some terminal state
    		next = b;
    		next.setAlpha(alpha);
    		next.setBeta(beta);
    		memo.put(next.getHashCode(), next);
    		return next;
    	}
    	
    	if (maxPlayer){
    		children.sort(null);
    		next.setValue(Integer.MIN_VALUE);
    		for (Board c : children){
    			//nodesChecked++;
    			next = max(next, alphaBeta(c, depth-1, alpha, beta, false ));
    			if (alpha < next.getAlpha())
    				alpha = next.getAlpha();
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
    		
    		Collections.sort(children, new Comparator<Board>(){

				@Override
				public int compare(Board a, Board b) {
					if (a.getValue()> b.getValue())
			    		return 1;
			    	else if (a.getValue()<b.getValue())
			    		return -1;
			    	else
			    		return 0;
				}
    			
    		});
    		next.setValue(Integer.MAX_VALUE);
    		for ( Board c: children){
    			//nodesChecked++;
    			next = min(next, alphaBeta(c, depth-1, alpha, beta, true));
    			if (beta> next.getBeta())
    				beta = next.getBeta();
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
}
