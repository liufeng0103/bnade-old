package lf.bnade.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lf.bnade.dao.PetDao;
import lf.bnade.model.Pet;
import lf.bnade.model.PetBreed;
import lf.bnade.model.PetStats;
import lf.bnade.vo.AuctionPetBreed;
import lf.bnade.vo.AuctionPetStats;

/*
 * 宠物相关操作
 */
public class PetService extends BaseService {
	
	private PetDao petDao;
	
	public PetService() {
		petDao = new PetDao();
	}

	public int addPet(Pet pet) throws SQLException {
		return petDao.addPet(pet);
	}
	
	/*
	 * 根据物品名称模糊查询，数据来自内存表，每次关闭数据库需要重新导入数据到内存表
	 * 找不到返回大小为0的list
	 */
	public List<String> getPetNamesByFuzzyName(String name) throws SQLException {
		return petDao.getPetNamesByFuzzyName(name);
	}
	
	/*
	 * 通过名获取宠物信息
	 */
	public AuctionPetBreed getPetByName(String name) throws SQLException {
		AuctionPetBreed apb = new AuctionPetBreed();
		List<PetStats> petStats = petDao.getPetByName(name);
		List<AuctionPetStats> stats = new ArrayList<AuctionPetStats>();
		for (PetStats ps : petStats) {
			apb.setPetSpeciesId(ps.getSpeciesId());
			AuctionPetStats aps = new AuctionPetStats();
			aps.setBreedId(ps.getBreedId());
			aps.setHealth(ps.getHealth());
			aps.setPower(ps.getPower());
			aps.setSpeed(ps.getSpeed());
			stats.add(aps);
		}
		apb.setPetStats(stats);
		return apb;
	}
	
	/*
	 * 添加pet breed信息
	 */
	public int add(PetBreed petBreed) throws SQLException {
		return petDao.add(petBreed);
	}
	
	/*
	 * 添加pet stats信息
	 */
	public int add(PetStats petStats) throws SQLException {
		return petDao.add(petStats);
	}
	
	/*
	 * 获取所有pet breed信息
	 */
	public List<PetBreed> getPetBreeds() throws SQLException {
		return petDao.getPetBreeds();
	}
	
	/*
	 * 获取所有pet stats信息
	 */
	public List<PetBreed> getNewPetBreed() throws SQLException {
		return petDao.getNewPetBreed();
	}
	
	/*
	 * 获取所有t_latest_auction中的pet breed信息
	 */
	public List<PetBreed> getAuctionPetBreeds() throws SQLException {
		return petDao.getAuctionPetBreeds();
	}
}
