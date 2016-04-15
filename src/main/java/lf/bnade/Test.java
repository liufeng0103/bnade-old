package lf.bnade;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import lf.bnade.model.Player;


public class Test {

	public static void main(String[] args) throws Exception {
		
SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		System.out.println(sdf.parse("2016-01-13").getTime());
		System.out.println(sdf.parse("2016-01-13"));
		System.out.println(sdf.parse("2016-01-24").getTime());
		System.out.println(sdf.parse("2016-01-24"));
		
		System.out.println(URLEncoder.encode(" ","utf-8"));
		System.out.println(new Date(1455703200000l));

		Player p1 = new Player();
		p1.setRealm("国王之谷");
		p1.setName("ßöâ");
		Player p2 = new Player();
		p2.setRealm("国王之谷");
		p2.setName("ßöå");
		System.out.println(p1.equals(p2));
		System.out.println(p1.hashCode() + "	" + p2.hashCode());
	}

}
