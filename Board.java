import java.util.ArrayList;

class Board {

    private char [][] board;
    private final String [] COL = {"A", "B", "C", "D",
	    "E", "F", "G", "H"};
    private boolean xWin, oWin;
    private int value;
    private int player;
    private int turn;
    private int[] lastMove;

    Board() {
	xWin = false;
	oWin = false;
	this.board = new char[8][8];
	initializeBoard();
	this.player = player;
	turn = 1;
    }

    Board(Board board, int row, int col) {
	this.xWin = false;
	this.oWin = false;
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

    public void setPlayer(int player) {
	this.player = player;
    }

    public void initializeBoard(){
    	for(int i=0; i<board.length;i++){
    		for (int j = 0;j< board.length;j++){
    			board[i][j] = '-';
    		}
    	}
    		
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
    		for(int j = 0;j<board.length-3; j++){
    			//for X
    			if((board[i][j] !='O')&&(board[i][j+1]!='O')&&(board[i][j+2]!='O')&&(board[i][j+3]!='O')){
    				value -=1;
    			}
    			//for O
    			if((board[i][j] !='X')&&(board[i][j+1]!='X')&&(board[i][j+2]!='X')&&(board[i][j+3]!='X')){
    				value +=1;
    			}	
    			//Check open twos
    			if((board[i][j] =='-')&&(board[i][j+1]=='X')&&(board[i][j+2]=='X')&&(board[i][j+3]=='-')){
    				value +=150;
    			}
    			//for min
    			if((board[i][j] =='-')&&(board[i][j+1]=='O')&&(board[i][j+2]=='O')&&(board[i][j+3]=='-')){
    				value -=150;
    			}
    			//check 4s
    			if((board[i][j] =='O')&&(board[i][j+1]=='O')&&(board[i][j+2]=='O')&&(board[i][j+3]=='O')){
    				value -=500000;
    			}
    			if((board[i][j] =='X')&&(board[i][j+1]=='X')&&(board[i][j+2]=='X')&&(board[i][j+3]=='X')){
    				value +=500000;
    			}
    		}
    	}
    	//check columns for possible 4s for O
    	for (int i =0; i<board.length-3;i++){
    		for(int j = 0;j<board.length; j++){
    			//for X
    			if((board[i][j] !='O')&&(board[i+1][j]!='O')&&(board[i+1][j]!='O')&&(board[i+3][j]!='O')){
    				value -=1;
    			}
    			//for O
    			if((board[i][j] !='X')&&(board[i+1][j]!='X')&&(board[i+2][j]!='X')&&(board[i+3][j]!='X')){
    				value +=1;
    			}
    			if((board[i][j] =='-')&&(board[i+1][j]=='X')&&(board[i+2][j]=='X')&&(board[i+3][j]=='-')){
    				value +=150;
    			}
    			if((board[i][j] =='-')&&(board[i+1][j]=='O')&&(board[i+2][j]=='O')&&(board[i+3][j]=='-')){
    				value -=150;
    			}
    			if((board[i][j] =='O')&&(board[i+1][j]=='O')&&(board[i+2][j]=='O')&&(board[i+3][j]=='O')){
    				value -=500000;
    			}
    			if((board[i][j] =='X')&&(board[i+1][j]=='X')&&(board[i+2][j]=='X')&&(board[i+3][j]=='X')){
    				value +=500000;
    			}
    		}
    	}
    	
    	
    	
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
    }

	public void setValue(int minValue) {
		value = minValue;
	}
	public int getValue(){
		return value;
	}
}
