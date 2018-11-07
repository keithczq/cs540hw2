public class studentAI extends Player {
    private int maxDepth;


    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void move(BoardState state) {
    	move = alphabetaSearch(state, maxDepth);
    }

    public int alphabetaSearch(BoardState state, int maxDepth) {
    	/*v ←MAX-VALUE(state,−∞,+∞)
			return the action in ACTIONS(state) with value v*/
    	int move = 0; //to store the move index to be returned, initialize to error value for debugging 
    	Integer alpha = Integer.MIN_VALUE;
    	Integer beta = Integer.MAX_VALUE;
    	Integer v = Integer.MIN_VALUE;
    	for (int i = 0; i < 6; i ++) {
    		//Check if legal move
    		if (state.isLegalMove(1, i)) {
	    		v = Math.max(v, minValue(state.applyMove(1, i), maxDepth, maxDepth-1, alpha, beta));
	    		if (v >= alpha) {
	    			alpha = v;
	    			move = i;
	    		}
    		}
    		if (alpha >= beta) {
    			break;
    		}
    	}
    	return move;
    }

    public int maxValue(BoardState state, int maxDepth, int currentDepth, int alpha, int beta) {
    	//Search cuts off when current depth = max allowed depth
    	//Determining a leaf node: (besides having reached max min depth) no legal moves for player to make, ending game
    	if (currentDepth == 0) {
    		//When game is over, call SBE
    		return sbe(state);
    	}
    	Integer v = Integer.MIN_VALUE; //Set v = -infinity
    	//For each successor, evaluate their alpha beta values
    	boolean leafNode = true; //flag to check if the current node is a leaf node or not, assuming it is first
    	for (int i = 0; i < 6; i ++) {
    		if (state.isLegalMove(1, i)) {
    			leafNode = false; //since successor was found, node is not a leaf node
    			v = Math.max(v, minValue(state.applyMove(1, i), maxDepth, currentDepth - 1, alpha, beta));
    			if (v >= beta) {
    				return v;
    			}	
    			alpha = Math.max(alpha, v);
    		}
    	}
    	if (leafNode) {
    		return sbe(state);
    	}
    	return v;
    }

    public int minValue(BoardState state, int maxDepth, int currentDepth, int alpha, int beta) {
    	//Search cuts off when current depth = max allowed depth
    	//Determining a leaf node: (besides having reached max min depth) no legal moves for player to make, ending game
    	if (currentDepth == 0) {
    		//When game is over, call SBE
    		return sbe(state);
    	}
    	Integer v = Integer.MAX_VALUE; //Set v = +infinity
    	//For each successor, evaluate their alpha beta values
    	boolean leafNode = true; //flag to check if the current node is a leaf node or not, assuming it is first
    	for (int i = 0; i < 6; i ++) {
    		if (state.isLegalMove(2, i)) {
    			leafNode = false; //since successor was found, node is not a leaf node
    			v = Math.min(v, maxValue(state.applyMove(2, i), maxDepth, currentDepth - 1, alpha, beta));
    			if (v <= alpha) {
    				return v;
    			}	
    			beta = Math.min(beta, v);
    		}
    	}
    	if (leafNode) {
    		return sbe(state);
    	}
    	return v;
    }

    public int sbe(BoardState state){
    	//Return the number of stones in the storehouse of the current player minus the number of stones 
    	//in the opponent’s storehouse. Always assume the current player is player 1.
    	return state.score[0] - state.score[1];
    }


}