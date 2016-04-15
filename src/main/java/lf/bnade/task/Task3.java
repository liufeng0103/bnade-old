package lf.bnade.task;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import lf.bnade.jmodel.JRealm;
import lf.bnade.jmodel.JRealms;

import com.google.gson.Gson;


/**
 * 
 * 更新数据库列表
 *
 */
public class Task3 {

	private static Scanner scanner;

	public static void main(String[] args) throws FileNotFoundException, SQLException {
		scanner = new Scanner(new FileInputStream("realms.json"));
		String json = scanner.nextLine();
		Gson gson = new Gson();
		JRealms jRealms = gson.fromJson(json, JRealms.class);	
		System.out.println(jRealms.getRealms().size());
		Map<String, String> realmMap = new HashMap<String, String>();
		for(JRealm jRealm : jRealms.getRealms()) {
			String realmName = jRealm.getName();
			String realm = realmMap.get(realmName);
			if (realm == null) {
				String[] connected =  jRealm.getConnected_realms();
				for(String rName : connected) {
					realmMap.put(rName, realmName);
				}
			}
		}

		for(String  realName : realmMap.keySet()) {
			System.out.println(realName + " - " + realmMap.get(realName));
		}
		System.out.println(realmMap.size());
	}

}
