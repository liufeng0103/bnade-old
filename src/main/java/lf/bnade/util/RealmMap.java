package lf.bnade.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import lf.bnade.jmodel.JRealm;
import lf.bnade.jmodel.JRealms;

import com.google.gson.Gson;

public class RealmMap {
	
	public static Map<String, String> realmMap = new HashMap<String, String>();
	private static Scanner scanner;

	public static String getConnectedRealm(String Name) {
		if (realmMap.size() == 0) {
			try {
				String file = "realms.json";
				if("TF".equals(BnadeProperties.getRegion())) {
					file = "tfrealms.json";
				}
				InputStream is = BnadeProperties.class.getClassLoader().getResourceAsStream(file);
				if (is == null) {
					is = new FileInputStream(file);
				}
				scanner = new Scanner(is);
				String json = scanner.nextLine();
				Gson gson = new Gson();
				JRealms jRealms = gson.fromJson(json, JRealms.class);
				for(JRealm jRealm : jRealms.getRealms()) {
					String realmName = jRealm.getName();
					String realm = realmMap.get(realmName);
					if (realm == null) {
						String[] connected =  jRealm.getConnected_realms();
						for(String rName : connected) {
							realmMap.put(rName, realmName);
//							System.out.println(rName + "	" + realmName);
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		return realmMap.get(Name);
	}
	
	public static void main(String[] args) {
		System.out.println(RealmMap.getConnectedRealm(""));
		Map<String,String> map = new HashMap<String, String>();
		for(String realm : RealmMap.realmMap.keySet()){
			if(!realm.equals(RealmMap.getConnectedRealm(realm))){
				String value = map.get(RealmMap.getConnectedRealm(realm));
				if(value==null){
					map.put(RealmMap.getConnectedRealm(realm), RealmMap.getConnectedRealm(realm)+"-"+realm);
				}else{
					map.put(RealmMap.getConnectedRealm(realm), map.get(RealmMap.getConnectedRealm(realm))+"-"+realm);
				}
			}
		}
		System.out.println(RealmMap.realmMap.size());
		for(String realm : map.keySet()){
			System.out.print("\"" + realm + "\":\"" + map.get(realm) + "\",");
		}
	}
}
