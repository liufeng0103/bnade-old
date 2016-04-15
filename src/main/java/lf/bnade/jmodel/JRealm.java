package lf.bnade.jmodel;

public class JRealm {
	private int id;
	private String type;
	private String population;
	private String queue;
	private String status;
	private String name;
	private String slug;
	private String battlegroup;
	private String local;
	private String timezone;
	private String[] connected_realms;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPopulation() {
		return population;
	}
	public void setPopulation(String population) {
		this.population = population;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getBattlegroup() {
		return battlegroup;
	}
	public void setBattlegroup(String battlegroup) {
		this.battlegroup = battlegroup;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String[] getConnected_realms() {
		return connected_realms;
	}
	public void setConnected_realms(String[] connected_realms) {
		this.connected_realms = connected_realms;
	}

}
