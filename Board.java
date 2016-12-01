import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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
    private int player;
    
    private int currentTurn;
    private int turn;
    private int[] lastMove;

    Board() {
		this.board = new char[8][8];
		turn = 1;
		currentTurn = 1;
    }

    Board(Board board, int row, int col) {
		lastMove = new int[]{row,col};
		this.board = new char[8][8];
		for (int i = 0; i < board.getBoard().length; i++)
		    for (int j = 0; j < board.getBoard().length; j++)
		    	this.board[i][j] = board.getBoard()[i][j];
		this.currentTurn = board.getCurrentTurn();
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
    	
		if(this.turn%2 == player && player == 0){
			if(this.checkBoard()) {
				value= Integer.MAX_VALUE;
				//System.out.println("Win");
			}
			else if (this.currentTurn == 1) {
				value = WEIGHT[this.lastMove[0]][this.lastMove[1]];
				//System.out.println("Here 1: " + value);
			}
			else {
				value = scoreMove(row,col);
			}
		}
		else{
			if(this.checkBoard()) {
				value= Integer.MIN_VALUE;
				//System.out.println("Win");
			}
			else if (this.currentTurn == 1) {
				value = -scoreMove(row,col)*WEIGHT[this.lastMove[0]][this.lastMove[1]];
				//System.out.println("Here 2");
			}
			else {
				value = -scoreMove(row,col);
				//System.out.println(value);
			}
		}
		
    	alpha = value;
    	beta = value;
    	return value;
    }
    private int scoreMove(int row, int col) {
		int score=0;
		
		//Against right wall
		if (col == SIZE-1) {
			if (board[row][col-3] != '-' && board[row][col-3] != board[row][col]) //if you cannot possibly get a connect 4
				score -= 1000;
			if (board[row][col] != board[row][col-1] && board[row][col-1] != '-')
				if (board[row][col-2] != '-' && board[row][col-1] == board[row][col-2])
					if (board[row][col-3] != '-' && board[row][col-2] == board[row][col-3]) //against wall, enemy about to win
						score += 10000;
		}
		
		//Against left wall
		if (col == 0) {
			if (board[row][col+3] != '-' && board[row][col+3] != board[row][col]) //if you cannot possibly get a connect 4
				score -= 1000;
			else if (board[row][col+1] != '-' && board[row][col+1] != board[row][col])
				if (board[row][col+2] != '-' && board[row][col+1] == board[row][col+2])
					if (board[row][col+3] != '-' && board[row][col+2] == board[row][col+3]) //against wall, enemy about to win
						score += 10000;
		}
		
		//Against bottom
		if (row == SIZE-1) {
			if (board[row-3][col] != '-' && board[row-3][col] != board[row][col]) //if you cannot possibly get a connect 4
				score -= 1000;
			else if (board[row-1][col] != '-' && board[row-1][col] != board[row][col])
				if (board[row-2][col] != '-' && board[row-1][col] == board[row-2][col])
					if (board[row-3][col] != '-' && board[row-2][col] == board[row-3][col]) //against wall, enemy about to win
						score += 10000;
		}
		
		//Against top
		if (row == 0) {
			if (board[row+3][col] != '-' && board[row+3][col] != board[row][col]) //if you cannot possibly get a connect 4
				score -= 1000;
			else if (board[row+1][col] != '-' && board[row][col] != board[row+1][col])
				if (board[row+2][col] != '-' && board[row+1][col] == board[row+2][col])
					if (board[row+3][col] != '-' && board[row+2][col] == board[row+3][col]) //against wall, enemy about to win
						score += 10000;
		}
		
		//Check to the right
		if (col<SIZE-1){
			if (board[row][col+1] == board[row][col]){ // 2 allies
					score +=20;
				if (col<SIZE-2){
					if (board[row][col+2] == board[row][col]){ // 3 allies
						score +=50;
						if (col<SIZE-3){
							if (board[row][col+3] == board[row][col])// win
								score += 7000;
						}
					}
					else if (board[row][col+2] == '-') // 2 allies, 1 empty
						score +=25;
					else // 2 allies, 1 enemy
						score += 10;
				}
			}
			else if (board[row][col+1] != '-') { // 1 enemy
				score += 40;
				if (col<SIZE-2) {
					if (board[row][col+2] == board[row][col+1]) // 2 enemies
						score += 20;
					else if (board[row][col+2] == board[row][col]) // sandwich 1 enemy
						score -= 20;
					if (col<SIZE-3) {
						if (board[row][col+3] == board[row][col]) // sandwich 2 enemies
							score -= 5;
						else if (board[row][col+3] == '-') // enemy empty empty
							score -= 20;
						else if (board[row][col+3] != board[row][col]) // 3 enemies
							score += 10000;
					}
				}
			}
			else { // Next to an empty
				score += 10;
				if (col<SIZE-2) {
					if (board[row][col+2] == '-') // empty empty
						score -= 10;
					else if (board[row][col+2] == board[row][col]) // empty ally
						score += 20;
					else { // empty enemy
						score += 20;
						if (col<SIZE-3) {
							if (board[row][col+3] == board[row][col+2]) // empty enemy enemy
								score += 100;
							else if (board[row][col+3] == board[row][col] ||
									board[row][col+3] == '-') // empty enemy ally
								score -= 20;
						}
					}
				}
			}
		}
		
		//Check to the left
		if (col>0){
			if (board[row][col-1] == board[row][col]){ // ally
				score +=20;
				if (col>1){
					if (board[row][col-2] == board[row][col]){ // ally ally
						score +=50;
						if (col>2){
							if (board[row][col-3] == board[row][col])// win
								score += 7000;
						}
					}
					else if (board[row][col-2] == '-') // 2 allies, 1 empty
						score +=25;
					else // 2 allies, 1 enemy
						score += 10;
				}
			}
			else if (board[row][col-1] != '-') { // 1 enemy
				score += 40;
				if (col>1) {
					if (board[row][col-2] == board[row][col-1]) { // 2 enemies
						score += 20;
						if (col>2) {
							if (board[row][col-3] == board[row][col]) // sandwich 2 enemies
								score -= 5;
							else if (board[row][col-3] == '-') // 2 enemies + empty
								score += 10000;
							else if (board[row][col-3] != board[row][col]) // 3 enemies
								score += 10000;
						}
					}
					else if (board[row][col-2] == board[row][col]) // sandwich 1 enemy
						score -= 20;
					else if (board[row][col-2] == '-') { // enemy + empty
						score += 20;
						if (col>2) {
							if (board[row][col-3] == board[row][col]) // enemy empty ally
								score -= 10;
							else if (board[row][col-3] == '-') // enemy empty empty
								score -= 20;
							else if (board[row][col-3] != board[row][col]) // enemy empty enemy
								score += 10000;
						}
					}
				}
			}
			else {// Next to an empty
				score += 10;
				if (col>1) {
					if (board[row][col-2] == '-') // empty empty
						score -= 10;
					else if (board[row][col-2] == board[row][col]) // empty ally
						score += 20;
					else { // empty enemy
						score += 20;
						if (col>2) {
							if (board[row][col-3] == board[row][col-2]) // empty enemy enemy
								score += 100;
							else if (board[row][col-3] == board[row][col] ||
									board[row][col-3] == '-') // empty enemy ally
								score -= 20;
						}
					}
				}
			}
		}
		
		//check down
		if (row<SIZE-1){
			if (board[row+1][col] == board[row][col]){ // ally
				score +=20;
				if (row<SIZE-2){
					if (board[row+2][col] == board[row][col]){ // ally ally
						score +=50;
						if (row<SIZE-3){
							if (board[row+3][col] == '-')
								score += 100;
							if (board[row+3][col] == board[row][col])// win
								score += 7000;
						}
					}
					else if (board[row+2][col] == '-') // 2 allies, 1 empty
						score +=25;
					else // 2 allies, 1 enemy
						score += 10;
				}
			}
			else if (board[row+1][col] != '-') { // 1 enemy
				score += 40;
				if (row<SIZE-2) {
					if (board[row+2][col] == board[row+1][col]) { // 2 enemies
						score += 20;
						if (row<SIZE-3) {
							if (board[row+3][col] == board[row][col]) // sandwich 2 enemies
								score -= 5;
							else if (board[row+3][col] == '-') // 2 enemies + empty
								score += 10000;
							else if (board[row+3][col] != board[row][col]) // 3 enemies
								score += 10000;
						}
					}
					else if (board[row+2][col] == board[row][col]) // sandwich 1 enemy
						score -= 20;
				}
			}
			else { // Next to an empty
				score += 10;
				if (row<SIZE-2) {
					if (board[row+2][col] == '-') // empty empty
						score -= 10;
					else if (board[row+2][col] == board[row][col]) // empty ally
						score += 20;
					else { // empty enemy
						score += 20;
						if (row<SIZE-3) {
							if (board[row+3][col] == board[row+2][col]) // empty enemy enemy
								score += 100;
							else if (board[row+3][col] == board[row][col]
									|| board[row+3][col] == '-') // empty enemy ally || empty enemy empty
								score -= 20;
						}
					}
				}
			}
		}
		
		
		
		//check up
		if (row>0){
			if (board[row-1][col] == board[row][col]){ // 2 allies
				score +=20;
				if (row>1){
					if (board[row-2][col] == board[row][col]){ // 3 allies
						score +=50;
						if (row>2){
							if (board[row-3][col] == board[row][col])// win
								score += 7000;
						}
					}
					else if (board[row-2][col] == '-') // 2 allies, 1 empty
						score +=25;
					else // 2 allies, 1 enemy
						score += 10;
				}
			}
			else if (board[row-1][col] != '-') { // enemy
				score += 40;
				if (row>1) {
					if (board[row-2][col] == board[row-1][col]) { // enemy enemy
						score += 20;
						if (row>2) {
							if (board[row-3][col] == board[row][col]) // enemy enemy ally
								score -= 5;
							if (board[row-3][col] == '-') // enemy enemy empty
								score += 7000;
							else if (board[row-3][col] != board[row][col]) // enemy enemy enemy
								score += 10000;
						}
					}
					else if (board[row-2][col] == board[row][col]) // enemy ally
						score -= 20;
					else { // enemy empty
						score += 20;
						if (row>2) {
							if (board[row-3][col] == board[row][col]) // enemy empty ally
								score -= 5;
							if (board[row-3][col] == '-') // enemy empty empty
								score -= 20;
							else if (board[row-3][col] != board[row][col]) //enemy empty 
								score += 5000;
						}
					}
				}
			}
			else { // Next to an empty
				score += 10;
				if (col<SIZE-2) {
					if (board[row][col+2] == '-') // empty empty
						score -= 10;
					else if (board[row][col+2] == board[row][col]) // empty ally
						score += 20;
					else { // empty enemy
						score += 20;
						if (col<SIZE-3) {
							if (board[row][col+3] == board[row][col+2]) // empty enemy enemy
								score += 100;
							else if (board[row][col+3] == board[row][col]
									|| board[row][col+3] == '-') // empty enemy ally
								score -= 20;
						}
					}
				}
			}
		}
		
		/*//TESTING
		if (board[row][col] == 'X') {
			System.out.println("Row: " + row + ", Col:" + col + ", Score: " + score + ", Current Turn: " + currentTurn);
			System.out.println("Player " + board[row][col]);
			this.printBoard();
		}
		//*/
		
		return score;
	}

	public boolean winCase(int row, int col) {
		if (0 < row && row < (SIZE-1)) {
			if (row <= SIZE-2)
				if (board[row+1][col] == board[row][col] && board[row+2][col] == '-' &&
						board[row-1][col] == '-')
					return true;
			else
				if (board[row-1][col] == board[row][col] && board[row-2][col] == '-' &&
					board[row+1][col] == '-')
				return true;
		}
		if (0 < col && col < (SIZE-1)) {
			if (col <= SIZE-2)
				if (board[row][col+1] == board[row][col] && board[row][col+2] == '-' &&
						board[row-1][col] == '-')
					return true;
			else
				if (board[row][col-1] == board[row][col] && board[row][col-2] == '-' &&
					board[row][col+1] == '-')
				return true;
		}
				
		return false;
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
    
    public void setPlayer(int player) {
    	this.player = player;
    }
    
    public void incrimentCurrentTurn() {
    	++this.currentTurn;
    }
    
    public int getCurrentTurn() {
    	return this.currentTurn;
    }
    
    public int getAlpha(){
    	return alpha;
    }
    
    public int getBeta(){
    	return beta;
    }
    public int getPlayer(){
    	return player;
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