import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;

class Board implements Comparable<Board>{

    private char [][] board;
    private static final int SIZE = 8;
    private final String [] COL = {"A", "B", "C", "D",
	    "E", "F", "G", "H"};
    private final int[][] WEIGHT = {{2,3,4,5,5,4,3,2},
    								{3,4,5,6,6,5,4,3},
    								{4,5,6,7,7,6,5,4},
    								{5,6,7,8,8,7,6,5},
    								{5,6,7,8,8,7,6,5},
    								{4,5,6,7,7,6,5,4},
    								{3,4,5,6,6,5,4,3},
    								{2,3,4,5,5,4,3,2}};
    
    public static HashSet<Board> memo = new HashSet<Board>();
    private int value;
    int hash;
    private int alpha, beta;
   
    private int turn;
    private int[] lastMove;

    Board() {
		this.board = new char[8][8];
		turn = 1;
    }

    Board(Board board, int row, int col) {
		lastMove = new int[]{row,col};
		this.board = new char[8][8];
		for (int i = 0; i < board.getBoard().length; i++)
		    for (int j = 0; j < board.getBoard().length; j++)
		    	this.board[i][j] = board.getBoard()[i][j];
		this.turn = board.getTurn();
		this.move(row, col);
		this.value = this.evaluate();
    }
    
   
  

    public void initializeBoard(){
    	for(int i=0; i<board.length;i++){
    		for (int j = 0;j< board.length;j++){
    			board[i][j] = '-';
    		}
    	}
    	this.hash();	
    }
    
   

    public void printBoard() {
		for (int i = 0; i < board.length + 1; i++) {
		    for (int j = 0; j < board.length + 1; j++) {
			if (i == 0 && j == 0)
			    System.out.print("   ");
			else if (i == 0)
			    System.out.print(" " + j + " ");
			if (i != 0 && j == 0)
			    System.out.print(" " + COL[i - 1] + " ");
	
			if (i != 0 && j != 0) {
			    System.out.print(" " +board[i-1][j-1] + " ");
			}
		    }
		    System.out.println();
		}
    }
    
    
    
    public ArrayList<Board> getChildren() {
    	ArrayList<Board> children = new ArrayList<Board>();
    	
    	for (int i = 0; i < board.length; i++) {
    	    for (int j = 0; j < board.length; j++) {
    		if (!this.occupied(i, j)) {
    		    children.add(new Board(this, i, j));
    			}
    	    }
    	}
    	
    	return children; 
    }
    
    /**
     * This will evaluate the value of this position for use in the tree evaluation
     * 
     */
    public int evaluate(){
    	int row = this.lastMove[0];
    	int col = this.lastMove[1];
    	
    	int value=0;
    			if(this.turn%2 == 1){
    				if(this.checkBoard())
    					value= Integer.MIN_VALUE;
    				else if (this.turn == 2)
    					value = -WEIGHT[this.lastMove[0]][this.lastMove[1]];
    				else 
    					value = -scoreMove(row,col)*WEIGHT[this.lastMove[0]][this.lastMove[1]];
    			}
    			else{
    				if(this.checkBoard())
    					value= Integer.MAX_VALUE;
    				else if (this.turn == 2)
    					value = WEIGHT[this.lastMove[0]][this.lastMove[1]];
    				else
    					value = scoreMove(row,col)*WEIGHT[this.lastMove[0]][this.lastMove[1]];
    			}
    	alpha = value;
    	beta = value;
    	return value;
    }
    private int scoreMove(int row, int col) {
		int score=0;
		
		
		
		//Check to the right
		if (col<SIZE-1){
			if (board[row][col+1] == board[row][col]){// 2 in a row
					score +=10;
				//check 2 over
				if (col<SIZE-2){
					if (board[row][col+2] == board[row][col]){//3
						score +=50;
						if (board[row][col+3] == board[row][col])//4
							score += 10000;
					}
					if (board[row][col+2] == '-')
						score +=25;
				}
			}
			if (board[row][col+1] == 'O' && this.turn % 2 == 1){
				score += 20;
			}
			else if (board[row][col+1] == 'X' && this.turn % 2 == 0)
				score -= 20;
		}
		//Check to the left
			
		if (col>0){
			if (board[row][col-1] == board[row][col]){
				// 2 in a row
				score +=10;
				if (col>1){
					if (board[row][col-2] == board[row][col]){//3
						score +=50;
						if (board[row][col-3] == board[row][col])//4
							score += 10000;
					}
					if (board[row][col-2] == '-')
						score +=25;
				}
			}
			if (board[row][col-1] == 'O' && this.turn % 2 == 1){
				score += 20;
			}
			else if (board[row][col-1] == 'X' && this.turn % 2 == 0)
				score -= 20;
				
		}
		
		//check down
		if (row<SIZE-1){
			if (board[row+1][col] == board[row][col]){
				// 2 in a row
					score +=10;
				if (row<SIZE-2){
					if (board[row+2][col] == board[row][col]){//3
						score +=50;
						if (board[row+3][col] == board[row][col])//4
							score += 10000;
					}
					if (board[row+2][col] == '-')
						score +=25;
				}
			}
			if (board[row+1][col] == 'O' && this.turn % 2 == 1){
				score += 20;
			}
			else if (board[row+1][col] == 'X' && this.turn % 2 == 0)
				score -= 20;
		}
		
		
		
		//check up
		if (row>0){
			if (board[row-1][col] == board[row][col]){
					// 2 in a row
					score +=10;
				if (row>1){
					if (board[row-2][col] == board[row][col]){//3
						score +=50;
						if (board[row-3][col] == board[row][col])//4
							score += 10000;
					}
					if (board[row-2][col] == '-')
						score +=25;
				}
			}
			if (board[row-1][col] == 'O' && this.turn % 2 == 1){
				score += 20;
			}
			else if (board[row-1][col] == 'X' && this.turn % 2 == 0)
				score -= 20;
		}
		
		
		
		return score;
	}

	
    
    public boolean checkBoard() {
		if (this.checkRow())
		    return true;
		if (this.checkColumn())
		    return true;
		return false;
    }

    public boolean checkRow() {
	for (int i = 0; i < board.length; i++) {
	    int count = 0;
	    int check = 0;
	    for (int j = 0; j < board.length; j++) {
			if (count == 0 && board[i][j] != '-') {
			    check = board[i][j];
			    count++;
			}
			else if (board[i][j] == check && check != '-')
			    count++;
			else {
			    count = 0;
			    check = 0;    
			}
			if (count >= 4)
			    return true;
		    }
	}
	return false;
    }

    public boolean checkColumn() {
		for (int j = 0; j < board.length; j++) {
		    int count = 0;
		    int check = 0;
		    for (int i = 0; i < board.length; i++) {
				if (count == 0 && board[i][j] != '-') {
				    check = board[i][j];
				    count++;
				}
				else if (board[i][j] == check && check != '-')
				    count++;
				else {
				    count = 0;
				    check = 0;
				}
				if (count >=4)
				    return true;
			    }
		}
		return false;
	    }
	
	    public boolean occupied(int row, int col) {
		if (this.board[row][col] == '-')
		    return false;
		return true;
    }

    public void move(int row, int col) {
		char move;
	
		if (turn % 2 == 1)
		    move = 'X';
		else
		    move = 'O';
	
		turn++;
	
		this.board[row][col] = move;
		this.hash();
    }

    private void hash() {
		hash = Arrays.deepHashCode(this.board);
	}

	@Override
    public int compareTo(Board b){
    	if (this.getValue()>b.getValue())
    		return -1;
    	else if (this.getValue()<b.getValue())
    		return 1;
    	else
    		return 0;
    	
    }
    
   
    
    @Override
    public boolean equals(Object o){
    	if (o == null)
    		return false;
    	if(!Board.class.isAssignableFrom(o.getClass()))
    		return false;
    	final Board b = (Board) o;
    	for(int i = 0;i<board.length;i++){
    		for(int j =0;j<board.length;j++){
    			if(board[i][j] != b.getBoard()[i][j])
    				return false;
    		}
    	}
    	return true;
    }
    
	public void setValue(int minValue) {
		value = minValue;
	}
	public int getValue(){
		return value;
	}
	public void setAlpha(int alpha){
    	this.alpha = alpha;
    }
    
    public void setBeta(int beta){
    	this.beta = beta;
    }
    
    public int getAlpha(){
    	return alpha;
    }
    
    public int getBeta(){
    	return beta;
    }
    public int getHashCode(){
    	return hash;
    }
    public void setBoard(char [][] board) {
    	this.board = board;
    }
    public int[] getLastMove(){
    	return lastMove;
    }
    public char[][] getBoard() {
    	return this.board;
    }
    public int getTurn() {
    	return this.turn;
    }

    
}
