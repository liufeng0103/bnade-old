package lf.bnade.model;

import com.google.gson.annotations.SerializedName;

public class Player {
	private String name;
	private String realm;
	private int realmId;
	@SerializedName("class")
	private int classV;
	private int race;
	private int gender;
	private int level;
	private String thumbnail;
	private long lastModified;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public int getRealmId() {
		return realmId;
	}

	public void setRealmId(int realmId) {
		this.realmId = realmId;
	}

	public int getClassV() {
		return classV;
	}

	public void setClassV(int classV) {
		this.classV = classV;
	}

	public int getRace() {
		return race;
	}

	public void setRace(int race) {
		this.race = race;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public int hashCode() {
		return (name + realm).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Player player = (Player) obj;
		return this.name.equals(player.getName())
				&& this.realm.equals(player.getRealm());
	}
}
