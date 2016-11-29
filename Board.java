class Board {

    private int [][] board;
    private final String [] COL = {"A", "B", "C", "D",
	    "E", "F", "G", "H"};

    Board() {
	this.board = new int[8][8];
    }

    public int[][] getBoard() {
	return this.board;
    }

    public void setBoard(int [][] board) {
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
		    if (board[i - 1][j - 1] == 0)
			System.out.print("[-]");
		    if (board[i - 1][j - 1] == 1)
			System.out.print("[X]");
		    if (board[i - 1][j - 1] == -1)
			System.out.print("[O]");
		}
	    }
	    System.out.println();
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
		if (count == 0 && board[i][j] != 0) {
		    check = board[i][j];
		    count++;
		}
		else if (board[i][j] == check && check != 0)
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
		if (count == 0 && board[i][j] != 0) {
		    check = board[i][j];
		    count++;
		}
		else if (board[i][j] == check && check != 0)
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
	if (this.board[input[0]][input[1]] == 0)
	    return false;
	return true;
    }

    public void move(int [] input, int player) {
	int move;
	
	if (player == 1)
	    move = 1;
	else
	    move = -1;

	this.board[input[0]][input[1]] = move;
    }
}
