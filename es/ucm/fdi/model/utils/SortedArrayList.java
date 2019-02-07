package es.ucm.fdi.model.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class SortedArrayList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;
	
	private Comparator<E> cmp;
	
	public SortedArrayList(Comparator<E> cmp) {
		this.cmp = cmp;
	};

	@Override
	public boolean add(E e) {
		//int a = binarySearch(e);
		super.add(e);
		int i = size() - 1;
		while (i > 0 && cmp.compare(get(i - 1), get(i)) == 1) //Se puede cambiar aun
		{
			//Collections.swap(this, i, i-1);
			E aux = get(i - 1);
			super.set(i-1, get(i));
			super.set(i, aux);
			i--;
		}
		return true; //Por convención

	} 
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean ret = false;
		for (E element : c)
		{
			add(element);
			ret = true;
		}
		return ret;
	}

	//The next 3 functions are overriden in order to avoid messing up the sorted list.
	//These methods should never be used in a SortedArrayList
	@Override
	public void add(int index, E element) {}

	@Override
	public boolean addAll(int index, Collection<? extends E> c)
	{
		return false;
	}
	
	@Override
	public E set(int index, E element)
	{
		return element;
	}
}
