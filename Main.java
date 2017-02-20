import java.lang.*;
import java.util.*;

public class Main{
	public static void main(String[] args){
		
		// scan the root
		Scanner scan = new Scanner(System.in);
		Rokudoku r = new Rokudoku(scan);
	
		// build a tree using the given root
		Tree<Rokudoku> t = new Tree<Rokudoku>(r);
		ArrayList<Rokudoku> data = new ArrayList<Rokudoku>();
		data.add(r);
		Tree<Rokudoku> tree = treeBuilder(r, 0, data);
		System.out.println("size" + tree.getSize());

		/* debug 
		Tree<Rokudoku> t = new Tree<Rokudoku>(r);
		int[] data = {1,2,0};
		Rokudoku s = r.swapper(data);
		t.add(s);
		Rokudoku v = s.swapper(data);
		System.out.println(contains(t,v));
		*/




	}

	public static Tree<Rokudoku> treeBuilder(Rokudoku r, int level, ArrayList<Rokudoku> data){

		Tree<Rokudoku> tree = new Tree<Rokudoku>(r);

		ArrayList<int[]> allSwaps = r.getAllSwaps();
		
		for (int i = 0; i < allSwaps.size(); i++){
			Rokudoku newroku = r.swapper(allSwaps.get(i));

			//System.out.println(allSwaps.get(i)[0] + " " + allSwaps.get(i)[1] + " " + allSwaps.get(i)[2]);
			//newroku.print();
			
			if (!contains(data, newroku)){
				
				data.add(newroku);

				if (data.size() % 1000 == 0)
					System.out.println(data.size());
				
				tree.add(treeBuilder(newroku, level+1, data));
				
				//System.out.println("add");
			} else {
				//System.out.println("found");
			}
		}
			return tree;
	}

	public static boolean contains(ArrayList<Rokudoku> data, Rokudoku r){
		int count = 0;
		while(count < data.size()){
			if (data.get(count).equiv(r)){
				return true;
			}
			count++;
		}
		return false;

	}
}
