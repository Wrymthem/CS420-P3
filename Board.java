import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;

class Board implements Comparable<Board>{

    private char [][] board;
    private final String [] COL = {"A", "B", "C", "D",
	    "E", "F", "G", "H"};
    
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
	this.evaluate();
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

  

    public void initializeBoard(){
    	for(int i=0; i<board.length;i++){
    		for (int j = 0;j< board.length;j++){
    			board[i][j] = '-';
    		}
    	}
    	this.hash();	
    }
    
    public void setBoard(char [][] board) {
	this.board = board;
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
    public void evaluate(){
    	this.value = 0;
    	//check rows for 4s for X (max player)
    	for (int i =0; i<board.length;i++){
    		for(int j = 0;j<board.length; j++){
    			//////////////////////////////////////////////////// ROWS ////////////////////////////////////////////////
    			if(j<board.length-1){
    				if((board[i][j] =='O')&&(board[i][j+1]=='O'))
    					value-=10;
    				if((board[i][j] =='X')&&(board[i][j+1]=='X'))
    					value+=10;
    			}
    			if(j<board.length-2){
    				if((board[i][j] =='O')&&(board[i][j+1]=='O')&&(board[i][j+2]=='O'))
    					value-=25;
    				if((board[i][j] =='X')&&(board[i][j+1]=='X')&&(board[i][j+2]=='X'))
    					value+=25;
    				if((board[i][j] =='-')&&(board[i][j+1]=='O')&&(board[i][j+2]=='-'))
    					value-=3;
    				if((board[i][j] =='-')&&(board[i][j+1]=='X')&&(board[i][j+2]=='-'))
    					value+=3;
    				if((board[i][j] =='0')&&(board[i][j+1]=='-')&&(board[i][j+2]=='0'))
    					value-=10;
    				if((board[i][j] =='X')&&(board[i][j+1]=='-')&&(board[i][j+2]=='X'))
    					value+=10;
    			}	
    			if(j<board.length-3){
    				//for X
        			if((board[i][j] !='O')&&(board[i][j+1]!='O')&&(board[i][j+2]!='O')&&(board[i][j+3]!='O')){
        				value +=1;
        			}
        			//for O
        			if((board[i][j] !='X')&&(board[i][j+1]!='X')&&(board[i][j+2]!='X')&&(board[i][j+3]!='X')){
        				value -=1;
        			}	
        			if((board[i][j] =='O')&&(board[i][j+1]=='O')&&(board[i][j+2]=='O')&&(board[i][j+3]=='O')){
        				value = Integer.MIN_VALUE;
        				break;
        			}
        			//for O
        			if((board[i][j] =='X')&&(board[i][j+1]=='X')&&(board[i][j+2]=='X')&&(board[i][j+3]=='X')){
        				value =Integer.MAX_VALUE;
        				break;
        			}
        			if((board[i][j] =='-')&&(board[i][j+1]=='O')&&(board[i][j+2]=='O')&&(board[i][j+3]=='-')){
        				value -=12;
        				
        			}
        			//for O
        			if((board[i][j] =='-')&&(board[i][j+1]=='X')&&(board[i][j+2]=='X')&&(board[i][j+3]=='-')){
        				value +=12;
        				
        			}
    			}
    			if(j<board.length-4){
    				if((board[i][j] =='-')&&(board[i][j+1]=='O')&&(board[i][j+2]=='O')&&(board[i][j+3]=='O')&&(board[i][j+4]=='-')){
        				value -=100;
        				
        			}
        			//for O
        			if((board[i][j] =='-')&&(board[i][j+1]=='X')&&(board[i][j+2]=='X')&&(board[i][j+3]=='X')&&(board[i][j+4]=='-')){
        				value += 100;
        				
        			}
    			}
    			//////////////////////////////////////////// COLS ///////////////////////////////////////////////
    			if(i<board.length-1){
    				if((board[i][j] =='O')&&(board[i+1][j]=='O'))
    					value-=5;
    				if((board[i][j] =='X')&&(board[i+1][j]=='X'))
    					value+=5;
    			}
    			
    			
    			if(i<board.length-2){
    				if((board[i][j] =='O')&&(board[i+1][j]=='O')&&(board[i+2][j]=='O'))
    					value-=17;
    				if((board[i][j] =='X')&&(board[i+1][j]=='X')&&(board[i+2][j]=='X'))
    					value+=17;
    				if((board[i][j] =='-')&&(board[i+1][j]=='O')&&(board[i+2][j]=='-'))
    					value-=1;
    				if((board[i][j] =='-')&&(board[i+1][j]=='X')&&(board[i+2][j]=='-'))
    					value+=1;
    				if((board[i][j] =='O')&&(board[i+1][j]=='-')&&(board[i+2][j]=='O'))
    					value-=10;
    				if((board[i][j] =='X')&&(board[i+1][j]=='-')&&(board[i+2][j]=='X'))
    					value+=10;
    			}
    			
    			if(i<board.length-3){
	    			//for X
	    			if((board[i][j] !='O')&&(board[i+1][j]!='O')&&(board[i+1][j]!='O')&&(board[i+3][j]!='O')){
	    				value +=1;
	    			}
	    			//for O
	    			if((board[i][j] !='X')&&(board[i+1][j]!='X')&&(board[i+2][j]!='X')&&(board[i+3][j]!='X')){
	    				value -=1;
	    			}
	    			if((board[i][j] =='O')&&(board[i+1][j]=='O')&&(board[i+1][j]=='O')&&(board[i+3][j]=='O')){
	    				value =Integer.MIN_VALUE;
	    				break;
	    			}
	    			//for O
	    			if((board[i][j] =='X')&&(board[i+1][j]=='X')&&(board[i+2][j]!='X')&&(board[i+3][j]=='X')){
	    				value =Integer.MAX_VALUE;
	    				break;
	    			}
	    			if((board[i][j] =='-')&&(board[i+1][j]=='O')&&(board[i+1][j]=='O')&&(board[i+3][j]=='-')){
	    				value -=12;
	    				
	    			}
	    			//for O
	    			if((board[i][j] =='-')&&(board[i+1][j]=='X')&&(board[i+2][j]!='X')&&(board[i+3][j]=='-')){
	    				value +=12;
	    				
	    			}
    			}
    			if(i<board.length-4){
    				if((board[i][j] =='-')&&(board[i+1][j]=='O')&&(board[i+1][j]=='O')&&(board[i+3][j]=='0')&&(board[i+4][j]=='-')){
    					value -=100;
        				
	    			}
	    			//for O
	    			if((board[i][j] =='-')&&(board[i+1][j]=='X')&&(board[i+2][j]!='X')&&(board[i+3][j]=='X')&&(board[i+4][j]=='-')){
	    				value +=100;
	    				
	    			}
    				
    			}
    		}
    	}
    	alpha = value;
    	beta = value;
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
    
    public int getHashCode(){
    	return hash;
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
}
