package vttp.batch5.sdf.task02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


// pseudocode to do 
// first read the board from file.txt such as figure1.txt
// after read the file , find the empty position in the board
// need to print the board from the file.
// create a constructor of int x and y.
// Evalute the position
// do the hasWon method to check the winning
// need to do blocking method 
// then clone the board

public class Main {

	public final static String ARG_Message = "Error:  Filename not provided. Provide the full path of the file";

	public static void main(String[] args) throws IOException

	{
		if (args.length == 0) {
			System.out.println(ARG_Message);
			return;
		}
        
		char[][] board = readBoardFromFile(args[0]);

        //print the board from filename args[0]
        System.out.println();
        System.out.println("Processing " + args[0]);
        System.out.println();
        printBoard(board);

		List<xAndyPosition> emptyPositions = getEmptyPositions(board);

		Map<xAndyPosition, Integer> utility = new HashMap<>();
		for (xAndyPosition pos : emptyPositions) {
			char[][] newBoard = cloneBoard(board);
			newBoard[pos.y][pos.x] = 'O';
			utility.put(pos, evaluatePosition(newBoard));
		}
		
		for (xAndyPosition pos : emptyPositions) {
			System.out.printf("Y = %d, X = %d, utility = %d \n",
					pos.y, pos.x, utility.get(pos));
		}

	}
	private static char[][] readBoardFromFile(String filename) throws FileNotFoundException {
	char[][] board = new char[3][3];
    File file = new File(filename);
	Scanner sc = new Scanner(file);
		for (int i = 0; i < 3 && sc.hasNextLine(); i++) {
			String line = sc.nextLine();
			for (int j = 0; j < 3 && j < line.length(); j++) {
				board[i][j] = line.charAt(j);
			}
		}
		sc.close();
		return board;
	}

    private static void printBoard(char[][] board) {
        System.out.println("Board: ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

	 private static List<xAndyPosition> getEmptyPositions(char[][] board) {
        List<xAndyPosition> emptyPositions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '.') {
                    emptyPositions.add(new xAndyPosition(j, i)); // x=j, y=i for output format
                }
            }
        }
        return emptyPositions;
    }
	private static int evaluatePosition(char[][] board) {
        if (hasWon(board, 'O')) {
            return 1;
        }

        if (needsBlocking(board)) {
            return -1;
        }

        // if (hasWon(board, 'X')) {
        //     return -1;
        // }

        // if (!hasWon(board, 'O')){
        // return 0;
        // }

        // if (!hasWon(board, 'X')){
        // return 0;
        // }
        
        else{
        return 0;
        }
    }

    //finding out whether win
	private static boolean hasWon(char[][] board, char player) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
                return true;
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        // Check other diagonals from 00,11,22
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        // hasWonfrompractice
    //            (board[0][0] == symbol && board[0][1] == symbol && board[0][2] == symbol) ||
    //            (board[1][0] == symbol && board[1][1] == symbol && board[1][2] == symbol) ||
    //            (board[2][0] == symbol && board[2][1] == symbol && board[2][2] == symbol) ||
    //            (board[0][0] == symbol && board[1][0] == symbol && board[2][0] == symbol) ||
    //            (board[0][1] == symbol && board[1][1] == symbol && board[2][1] == symbol) ||
    //            (board[0][2] == symbol && board[1][2] == symbol && board[2][2] == symbol) ||
    //            (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
    //            (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    


        return false;
    }

	private static boolean needsBlocking(char[][] board) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            int xCount = 0;
            int emptyCount = 0;
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'X')
                    xCount++;
                if (board[i][j] == '.')
                    emptyCount++;
            }
            if (xCount == 2 && emptyCount == 1)
                return true;
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            int xCount = 0;
            int emptyCount = 0;
            for (int i = 0; i < 3; i++) {
                if (board[i][j] == 'X')
                    xCount++;
                if (board[i][j] == '.')
                    emptyCount++;
            }
            if (xCount == 2 && emptyCount == 1)
                return true;
        }

        // Check main diagonal
        int xCount = 0;
        int emptyCount = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][i] == 'X')
                xCount++;
            if (board[i][i] == '.')
                emptyCount++;
        }
        if (xCount == 2 && emptyCount == 1)
            return true;

        // Check other diagonal
        xCount = 0;
        emptyCount = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][2 - i] == 'X')
                xCount++;
            if (board[i][2 - i] == '.')
                emptyCount++;
        }
        if (xCount == 2 && emptyCount == 1)
            return true;

        return false;
    }

	private static char[][] cloneBoard(char[][] board) {
        char[][] clone = new char[3][3];
        for (int i = 0; i < 3; i++) {
            clone[i] = board[i].clone();
        }
        return clone;
    }
}
