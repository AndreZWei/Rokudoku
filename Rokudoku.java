/* The Class Rokudoku contains the constructor for a Rokudoku

*/


import java.util.*;
import java.lang.*;
import java.io.*;

public class Rokudoku{
	public int[][] puzzle;

	// Constructors of Rokudoku
	
	public Rokudoku(int[][] puzzle){
		this.puzzle = puzzle;
	}

	public Rokudoku(Scanner scan){

		int[][] puzzle = new int[6][6];

		int[][] prepuzzle = new int[6][6];
		for (int i = 0; i < 6; i ++){
			String[] tokens = scan.nextLine().split(" ");
			for (int j = 0; j < 6; j++){
				prepuzzle[i][j] = Integer.parseInt(tokens[j]);
			}
		}
		
		for (int i = 0; i < 3; i++){
			puzzle[0][i] = prepuzzle[0][i];
			puzzle[1][i] = prepuzzle[0][i+3];
			puzzle[0][i+3] = prepuzzle[1][i];
			puzzle[1][i+3] = prepuzzle[1][i+3];
			puzzle[2][i] = prepuzzle[2][i];
			puzzle[3][i] = prepuzzle[2][i+3];
			puzzle[2][i+3] = prepuzzle[3][i];
			puzzle[3][i+3] = prepuzzle[3][i+3];
			puzzle[4][i] = prepuzzle[4][i];
			puzzle[5][i] = prepuzzle[4][i+3];
			puzzle[4][i+3] = prepuzzle[5][i];
			puzzle[5][i+3] = prepuzzle[5][i+3];
		}	

		if (!solved(puzzle)){
			System.out.println("Not a good sudoku!");
		}
		else{
			System.out.println("Good sudoku");
		}

		this.puzzle = puzzle;
	}
	

	// Method for format printting

	public void print(){

		int[][] printpuzzle = new int[6][6];
		
		for (int i = 0; i < 3; i++){
			printpuzzle[0][i] = this.puzzle[0][i];
			printpuzzle[1][i] = this.puzzle[0][i+3];
			printpuzzle[0][i+3] = this.puzzle[1][i];
			printpuzzle[1][i+3] = this.puzzle[1][i+3];
			printpuzzle[2][i] = this.puzzle[2][i];
			printpuzzle[3][i] = this.puzzle[2][i+3];
			printpuzzle[2][i+3] = this.puzzle[3][i];
			printpuzzle[3][i+3] = this.puzzle[3][i+3];
			printpuzzle[4][i] = this.puzzle[4][i];
			printpuzzle[5][i] = this.puzzle[4][i+3];
			printpuzzle[4][i+3] = this.puzzle[5][i];
			printpuzzle[5][i+3] = this.puzzle[5][i+3];
		}

		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 6; j++){
				System.out.print(printpuzzle[i][j] + " ");
			}
			System.out.println("");
		}

		
		// Check if the sudoku is successfully solved

		/*if (solved(this.puzzle)){
			System.out.println("YEAH!");
		}*/

	}

	// Method to determine if two Rokudokus are equal

	public boolean equiv(Rokudoku other){
		for (int i =0; i < 6; i++){
			for (int j =0; j < 6; j++){
				if (this.puzzle[i][j] != other.puzzle[i][j]){
					return false;
				}
			}
		}
		return true;
	}


	// Method for permute two numbers a and b in block block (returns a new Rokudoku)

	public Rokudoku swapper(int[] data){
		int a = data[0];
		int b = data[1];
		int block = data[2];

		//int[][] newpuzzle = Arrays.copyOf(puzzle, puzzle.length);

		int[][] newpuzzle = new int[6][6];

		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 6; j++){
				newpuzzle[i][j] = puzzle[i][j];
			}
		}

		int hn = hneighbor(block);
		int[] vn = vneighbor(block);

		
		// Find the effect of the initial change
		
		boolean vchange0 = false;
		boolean vchange1 = false;
		boolean vchange2 = false;
		boolean vchange3 = false;
		boolean vchange4 = false;
		
		swap(newpuzzle[block], a, b);

		while(!solved(newpuzzle)){
		if (!vchange0) {
			if ((!vcompatible(a, b, newpuzzle[vneighbor(hn)[0]], newpuzzle[hn])) || 
				(!vcompatible(a, b, newpuzzle[vneighbor(hn)[1]], newpuzzle[hn])) || 
				(!hcompatible(a, b, newpuzzle[block], newpuzzle[hn]))){
				swap(newpuzzle[hn], a, b);
				vchange0 = true;
			}
		}	

		if (!vchange1) {
			if ((!hcompatible(a, b, newpuzzle[vneighbor(hn)[0]], newpuzzle[vn[0]])) || 
				(!vcompatible(a, b, newpuzzle[vn[0]], newpuzzle[vn[1]])) ||
				(!vcompatible(a, b, newpuzzle[block], newpuzzle[vn[0]]))){
				swap(newpuzzle[vn[0]], a, b);
				vchange1 = true;
			}
		}

		if (!vchange2) {
			if ((!hcompatible(a, b, newpuzzle[vneighbor(hn)[1]], newpuzzle[vn[1]])) || 
				(!vcompatible(a, b, newpuzzle[vn[0]], newpuzzle[vn[1]])) ||
				(!vcompatible(a, b, newpuzzle[block], newpuzzle[vn[1]]))){
				swap(newpuzzle[vn[1]], a, b);
				vchange2 = true;
			}
		}

		if (!vchange3) {
			if ((!hcompatible(a, b, newpuzzle[vn[0]], newpuzzle[hneighbor(vn[0])])) || 
				(!vcompatible(a, b, newpuzzle[hneighbor(vn[1])], newpuzzle[hneighbor(vn[0])])) ||
				(!vcompatible(a, b, newpuzzle[hn], newpuzzle[vneighbor(hn)[0]]))){
				swap(newpuzzle[hneighbor(vn[0])], a, b);
				vchange3 = true;
			}
		}

		if (!vchange4) {
			if ((!hcompatible(a, b, newpuzzle[vn[1]], newpuzzle[hneighbor(vn[1])])) || 
				(!vcompatible(a, b, newpuzzle[hneighbor(vn[0])], newpuzzle[hneighbor(vn[1])])) ||
				(!vcompatible(a, b, newpuzzle[hn], newpuzzle[vneighbor(hn)[1]]))){
				swap(newpuzzle[hneighbor(vn[1])], a, b);
				vchange4 = true;
			}
		}
		}				

		/*String swap = "Changed blocks: ";
		for (int i = 0; i < swapped.size(); i++){
			swap = swap + (swapped.get(i)+1 + " ");
		}
		System.out.println(swap);*/
		
		Rokudoku result = new Rokudoku(newpuzzle);

		return result;	
	}

	// Method for permute two numbers a and b in block block (returns an arraylist of modified blocks)

	public ArrayList<Integer> permute(int[] data){

		int a = data[0];
		int b = data[1];
		int block = data[2];

		puzzle = this.puzzle;

		int hn = hneighbor(block);
		int[] vn = vneighbor(block);

		
		// Find the effect of the initial change
		
		boolean vchange0 = false;
		boolean vchange1 = false;
		boolean vchange2 = false;
		boolean vchange3 = false;
		boolean vchange4 = false;

		ArrayList<Integer> swapped = new ArrayList<Integer>();
		
		swap(puzzle[block], a, b);
		swapped.add(block);

		while(!solved(puzzle)){
		if (!vchange0) {
			if ((!vcompatible(a, b, puzzle[vneighbor(hn)[0]], puzzle[hn])) || 
				(!vcompatible(a, b, puzzle[vneighbor(hn)[1]], puzzle[hn])) || 
				(!hcompatible(a, b, puzzle[block], puzzle[hn]))){
				swap(puzzle[hn], a, b);
				swapped.add(hn);
				vchange0 = true;
			}
		}	

		if (!vchange1) {
			if ((!hcompatible(a, b, puzzle[vneighbor(hn)[0]], puzzle[vn[0]])) || 
				(!vcompatible(a, b, puzzle[vn[0]], puzzle[vn[1]])) ||
				(!vcompatible(a, b, puzzle[block], puzzle[vn[0]]))){
				swap(puzzle[vn[0]], a, b);
				swapped.add(vn[0]);
				vchange1 = true;
			}
		}

		if (!vchange2) {
			if ((!hcompatible(a, b, puzzle[vneighbor(hn)[1]], puzzle[vn[1]])) || 
				(!vcompatible(a, b, puzzle[vn[0]], puzzle[vn[1]])) ||
				(!vcompatible(a, b, puzzle[block], puzzle[vn[1]]))){
				swap(puzzle[vn[1]], a, b);
				swapped.add(vn[1]);
				vchange2 = true;
			}
		}

		if (!vchange3) {
			if ((!hcompatible(a, b, puzzle[vn[0]], puzzle[hneighbor(vn[0])])) || 
				(!vcompatible(a, b, puzzle[hneighbor(vn[1])], puzzle[hneighbor(vn[0])])) ||
				(!vcompatible(a, b, puzzle[hn], puzzle[vneighbor(hn)[0]]))){
				swap(puzzle[hneighbor(vn[0])], a, b);
				swapped.add(hneighbor(vn[0]));
				vchange3 = true;
			}
		}

		if (!vchange4) {
			if ((!hcompatible(a, b, puzzle[vn[1]], puzzle[hneighbor(vn[1])])) || 
				(!vcompatible(a, b, puzzle[hneighbor(vn[0])], puzzle[hneighbor(vn[1])])) ||
				(!vcompatible(a, b, puzzle[hn], puzzle[vneighbor(hn)[1]]))){
				swap(puzzle[hneighbor(vn[1])], a, b);
				swapped.add(hneighbor(vn[1]));
				vchange4 = true;
			}
		}
		}				

		/*String swap = "Changed blocks: ";
		for (int i = 0; i < swapped.size(); i++){
			swap = swap + (swapped.get(i)+1 + " ");
		}
		System.out.println(swap);*/
		
		this.puzzle = puzzle;

		return swapped;
	}

	// Method to return all non-identical swaps

	public ArrayList<int[]> getAllSwaps(){
		ArrayList<int[]> allSwaps = new ArrayList<int[]>();
		Rokudoku roku = new Rokudoku(puzzle);
		for (int i = 1; i < 6; i++){
			for (int j = i+1; j <= 6; j++){
				ArrayList<Integer> blocks = new ArrayList<Integer>();
				int num = 0;
				while (blocks.size() < 6){
					int[] data = {i,j,num};
					ArrayList<Integer> changedBlocks = roku.permute(data);
					boolean viable = true;
					for (int k = 0; k < changedBlocks.size(); k++){
						if (blocks.contains(changedBlocks.get(k)))
							viable = false;
					}
					if (viable == true){
						allSwaps.add(data);
						for (int n = 0; n < changedBlocks.size(); n++){
							blocks.add(changedBlocks.get(n));
						}
					}
					roku.permute(data);
					num++;
				}
			}
		}
		return allSwaps;
	}
	
	
	
	// static methods used in permutations


	// find the horizontal neighbor of a block

	public static int hneighbor(int a){
		switch (a){
			case 1: return 0;
			case 2: return 3;
			case 3: return 2;
			case 4: return 5;
			case 5: return 4;
			default: return 1;
		}
	}


	// find the vertical neighbors of a block

	public static int[] vneighbor(int a){
		int[] vn = new int[2];
		switch (a){
			case 1: vn[0] = 3; vn[1] = 5; return vn;
			case 2: vn[0] = 0; vn[1] = 4; return vn;
			case 3: vn[0] = 1; vn[1] = 5; return vn;			
			case 4: vn[0] = 0; vn[1] = 2; return vn;
			case 5: vn[0] = 1; vn[1] = 3; return vn;
			default: vn[0] = 2; vn[1] = 4; return vn;
		}
	}
	

	public static int find(int[] block, int a){
		int i = 0;
		while (block[i] != a){
			i++;
		}
		return i;
	}

	public static boolean exists(int[] block, int a){
		boolean found = false;
		int i = 0;
		while ((found == false) && (i < 6)){
			found = (block[i] == a);
			i ++;
		}
		return found;
	}


	public static void swap(int[] block, int a, int b){
		int alpha = find(block, a);
		int beta = find(block, b);
		block[alpha] = b;
		block[beta] = a;
	}

	public static boolean hcompatible(int a, int b, int[] blocka, int[] blockb){
		int alphaa = find(blocka, a);
		int betaa = find(blocka, b);
		int alphab = find(blockb, a);
		int betab = find(blockb, b);
		return(((alphaa -2.5) * (alphab -2.5) < 0) && ((betaa -2.5) * (betab -2.5) < 0));
	}

	public static boolean vcompatible(int a, int b, int[] blocka, int[] blockb){
		int alphaa = find(blocka, a);
		int betaa = find(blocka, b);
		int alphab = find(blockb, a);
		int betab = find(blockb, b);
		return(((alphaa % 3) != (alphab % 3)) && ((betaa % 3) != (betab % 3)));
	}
	
	public static boolean satisfy(int[] set){
		return (exists(set, 1) && exists(set, 2) && exists(set, 3) &&
			exists(set, 4) && exists(set, 5) && exists(set, 6));
	}
	
	public static boolean hsatisfy(int[] blocka, int[] blockb){
		int[] set1 = new int[6];
		int[] set2 = new int[6];
		for (int i = 0; i < 3; i++){
			set1[i] = blocka[i];
			set1[i+3] = blockb[i];
			set2[i] = blocka[i+3];
			set2[i+3] = blockb[i+3];
		}
		return(satisfy(set1) && satisfy(set2));
	}
	
	public static boolean vsatisfy(int[] blocka, int[] blockb, int[] blockc){
		int[] set1 = new int[6];
		int[] set2 = new int[6];
		int[] set3 = new int[6];
		for (int i = 0; i < 2; i++){
			set1[i] = blocka[3*i];
			set1[i+2] = blockb[3*i];
			set1[i+4] = blockc[3*i];
			set2[i] = blocka[3*i + 1];
			set2[i+2] = blockb[3*i + 1];
			set2[i+4] = blockc[3*i + 1];
			set3[i] = blocka[3*i + 2];
			set3[i+2] = blockb[3*i + 2];
			set3[i+4] = blockc[3*i + 2];
		}
			return(satisfy(set1) && satisfy(set2) && satisfy(set3));
	}
	
	public static boolean solved (int[][] puzzle){
		return (hsatisfy(puzzle[0],puzzle[1]) && hsatisfy(puzzle[2],puzzle[3]) && hsatisfy(puzzle[4],puzzle[5]) &&  
			vsatisfy(puzzle[0],puzzle[2],puzzle[4]) && vsatisfy(puzzle[1],puzzle[3],puzzle[5])); 
	}

	// Used for debug

	public static void main(String[] args){

		Scanner scan = new Scanner(System.in);

		Rokudoku roku = new Rokudoku(scan);

		/*int[] data = {1,3,0};

		ArrayList<Integer> ch = roku.permute(data);

		System.out.println(ch.size());*/

		/*roku.print();

		for (int i =0; i< ch.size(); i++){
			System.out.println(ch.get(i));
		}*/
		
		ArrayList<int[]> allSwaps = roku.getAllSwaps();


		System.out.println(allSwaps.size());
		for (int i =0; i< allSwaps.size(); i++){
			int[] swap = allSwaps.get(i);
			System.out.println(swap[0] + " " + swap[1] + " " + (swap[2]+1));
		}

	}

}

