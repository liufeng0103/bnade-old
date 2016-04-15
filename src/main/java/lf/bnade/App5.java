package lf.bnade;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lf.bnade.jmodel.JRealm;
import lf.bnade.jmodel.JRealms;

import com.google.gson.Gson;

/*
 * 获取所有服务器名字，合服的服务器只显示第一个出现的服务器
 */
public class App5 {
	public static void main(String[] args) throws FileNotFoundException, SQLException {
		Scanner scanner = new Scanner(new FileInputStream("realms.json"));
		String json = scanner.nextLine();
		Gson gson = new Gson();
		JRealms jRealms = gson.fromJson(json, JRealms.class);	
		List<String> realNames = new ArrayList<String>();
		List<String> connectedName = new ArrayList<String>();
		for(JRealm jRealm : jRealms.getRealms()) {
			if (!connectedName.contains(jRealm.getName())) {
				realNames.add(jRealm.getName());
				String[] connected =  jRealm.getConnected_realms();
				for(String rName : connected) {
					connectedName.add(rName);
				}
			}
		}
//		System.out.println(realNames.size());
		for(String  realName : realNames) {
			System.out.println(realName);
		}
	}
}
