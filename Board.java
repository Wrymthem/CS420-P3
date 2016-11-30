class Board {

    private char [][] board;
    private final String [] COL = {"A", "B", "C", "D",
	    "E", "F", "G", "H"};
    private boolean xWin, oWin;
    private int value;

    Board() {
    xWin = false;
    oWin = false;
	this.board = new char[8][8];
	initializeBoard();
    }

    public char[][] getBoard() {
	return this.board;
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
    				value +=5;
    			}
    			//for O
    			if((board[i][j] !='X')&&(board[i][j+1]!='X')&&(board[i][j+2]!='X')&&(board[i][j+3]!='X')){
    				value -=5;
    			}	
    			//Check open twos
    			if((board[i][j] =='-')&&(board[i][j+1]=='X')&&(board[i][j+2]=='X')&&(board[i][j+3]=='-')){
    				value +=10;
    			}
    			//for min
    			if((board[i][j] =='-')&&(board[i][j+1]=='O')&&(board[i][j+2]=='O')&&(board[i][j+3]=='-')){
    				value -=10;
    			}
    		}
    	}
    	//check columns for possible 4s for O
    	for (int i =0; i<board.length-3;i++){
    		for(int j = 0;j<board.length; j++){
    			//for X
    			if((board[i][j] !='O')&&(board[i+1][j]!='O')&&(board[i+1][j]!='O')&&(board[i+3][j]!='O')){
    				value +=5;
    			}
    			//for O
    			if((board[i][j] !='X')&&(board[i+1][j]!='X')&&(board[i+2][j]!='X')&&(board[i+3][j]!='X')){
    				value -=5;
    			}
    			if((board[i][j] =='-')&&(board[i+1][j]=='X')&&(board[i+2][j]=='X')&&(board[i+3][j]=='-')){
    				value +=10;
    			}
    			if((board[i][j] =='-')&&(board[i+1][j]=='O')&&(board[i+2][j]=='O')&&(board[i+3][j]=='-')){
    				value -=10;
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

    public boolean occupied(int [] input) {
	if (this.board[input[0]][input[1]] == '-')
	    return false;
	return true;
    }

    public void move(int [] input, int player) {
	char move;
	
	if (player == 1)
	    move = 'X';
	else
	    move = 'O';

	this.board[input[0]][input[1]] = move;
    }
}
