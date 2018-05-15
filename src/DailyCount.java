import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;

public class DailyCount {
	Map<Date,Hit> map;
	List<Date> sortedDate;
	Long minDate;
	Long maxDate;
	public DailyCount() {
		map=new HashMap<Date,Hit>();
		minDate=Long.MAX_VALUE;
		maxDate=Long.MIN_VALUE;
	}
	public void readFile(String path) throws IOException {
		 TimeZone.setDefault(TimeZone.getTimeZone("GMT"));  
		 //List<String> lines = new ArrayList<String>();
		 File file = new File(path);
	     BufferedReader reader = null;
	     reader = new BufferedReader(new FileReader(file));
	     String temp = null;
         while ((temp = reader.readLine()) != null) {
        	// lines.add(temp);
        	String[] strs=temp.split("\\|");
        	Date date=generateDate(strs[0]);
        	if(date.getTime()>maxDate) {
        		maxDate=date.getTime();
        	}
        	if(date.getTime()<minDate) {
        		minDate=date.getTime();
        	}
        	if(map.containsKey(date)) {
        		Hit hit=map.get(date);
        		hit.add(strs[1]);
        		map.put(date, hit);
        	}
        	else {
        		Hit hit=new Hit();
        		hit.add(strs[1]);
        		map.put(date, hit);
        	}
         }
         reader.close();
	     
	}
	Date generateDate(String orignial) {
		long mills=Long.parseLong(orignial)*1000;
		Date date=new Date(mills);
    	TimeZone timeZone = TimeZone.getTimeZone("GMT");
        Calendar cal = Calendar.getInstance(timeZone);
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
	}
	void output() {
		for(long i=minDate;i<=maxDate;i+=86400000) {
        	Date date=new Date(i);
        	if(map.containsKey(date)) {
        		TimeZone timeZone = TimeZone.getTimeZone("GMT");
                Calendar cal = Calendar.getInstance(timeZone);
                cal.setTime(date);
                System.out.println(cal.get(Calendar.MONTH) + 1+"/"+cal.get(Calendar.DATE)+"/"+
                        cal.get(Calendar.YEAR)+" "+timeZone.getID());
        		map.get(date).print();
        	}
        }
	}
	public static void main(String args[] ) throws Exception {
        DailyCount d=new DailyCount();
        Scanner sc = new Scanner(System.in);       
        String file = sc.nextLine();  
        d.readFile(file);
        d.output();
    }    
}
