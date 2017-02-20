/* The class implements a tree structure

*/

import java.util.*;
import java.lang.*;

public class Tree<T> {
	public T data;
	public ArrayList<Tree<T>> children;
	private int size;

	public Tree(T data){
		this.data = data;
		this.children = new ArrayList<Tree<T>>();
		this.size = 1;
	}

	public void add(Tree<T> child){
		children.add(child);
		size++;
	}

	public void add(T r){
		children.add(new Tree<T>(r));
		size++;
	}

	public int getSize(){
		int s = 1;
		for (int i = 0; i < children.size(); i++){
			s += children.get(i).getSize();
		}
		return s;
	}

	/*public boolean contains(T data){
		int num = children.size();
		if (this.data.equals(data)){
			return true;
		} else {
			for (int i = 0; i < num; i++){
				if (children.get(i).contains(data)){
					return true;
				}
			}
		}
		return false;

	}*/		


}