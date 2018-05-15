import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Hit {
	Map<Integer,Set<String>> map;
	Map<String,Integer> countMap;
	int max;
	Hit(){
		map=new HashMap<Integer,Set<String>>();
		countMap=new HashMap<String,Integer>();
		max=0;
	}
	public void add(String url) {
		if(!countMap.containsKey(url)) {
			countMap.put(url, 1);
			if(!map.containsKey(1)) {
				Set<String> set=new TreeSet<String>();
				set.add(url);
				map.put(1, set);
			}
			else {
				map.get(1).add(url);
			}
			if(max<1)max=1;
		}
		else {
			int count=countMap.get(url);
			int count2=count+1;
			countMap.put(url, count2);
			Set<String> s=map.get(count);
			s.remove(url);
			map.put(count, s);
			if(!map.containsKey(count2)) {
				Set<String> set=new TreeSet<String>();
				set.add(url);
				map.put(count2, set);
			}
			else {
				map.get(count2).add(url);
			}
			if(max<count2)max=count2;
		}
	}
	public void print() {
		for(int i=max;i>=1;i--) {
			Set<String> set=map.get(i);
			if(set!=null) {
				for(String str:set) {
					System.out.println(str+" "+i);
				}
			}
		}
	}
}
