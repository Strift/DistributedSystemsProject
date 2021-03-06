package project.server;

import java.util.Collections;
import java.util.List;

public class SimpleSorter implements project.services.Sorter {
	
	private static int instances = 0;
	
	private String name;
	
	public SimpleSorter() {
		SimpleSorter.instances++;
		this.name = "SimpleSorter" + SimpleSorter.instances;
	}

	@Override
	public List<String> sort(List<String> list) {
		System.out.println(this + ": received " + list);
		Collections.sort(list);
		System.out.println(this + ": returning " + list);
		return list;
	}

	@Override
	public List<String> reverseSort(List<String> list) {
		System.out.println(this + ": receveid " + list);
		Collections.sort(list);
		Collections.reverse(list);
		System.out.println(this + ": returning " + list);
		return list;
	}

	@Override
	public String toString() {
		return this.name + " " + Thread.currentThread();
	}

}
