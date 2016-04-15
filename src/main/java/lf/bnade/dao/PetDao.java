package lf.bnade.dao;

import java.sql.SQLException;
import java.util.List;

import lf.bnade.model.Pet;
import lf.bnade.model.PetBreed;
import lf.bnade.model.PetStats;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

/*
 * 宠物相关的db操作
 */
public class PetDao extends BaseDao {

	/*
	 * 添加宠物信息
	 */
	public int addPet(Pet pet) throws SQLException {
		return run.update("insert into t_pet (id,name,icon) values(?,?,?)",pet.getId(), pet.getName(), pet.getIcon());
	}
	
	/*
	 * 模糊查询所有宠物名
	 */
	public List<String> getPetNamesByFuzzyName(String name) throws SQLException {
		return run.query("select name from t_pet where name like ?", new ColumnListHandler<String>(), "%"+name+"%");
	}
	
	/*
	 * 通过名获取宠物信息
	 */
	public List<PetStats> getPetByName(String name) throws SQLException {
		return run.query("select speciesId,breedId,health,power,speed from t_pet p join t_pet_stats ps on p.id=ps.speciesId where name=?", new BeanListHandler<PetStats>(PetStats.class), name);
	}
	
	/*
	 * 添加pet breed信息
	 */
	public int add(PetBreed petBreed) throws SQLException {
		return run.update( "insert into t_pet_breed (petSpeciesId,petBreedId) values(?,?)",
				petBreed.getPetSpeciesId(), petBreed.getPetBreedId());
	}
	
	/*
	 * 添加pet stats信息
	 */
	public int add(PetStats petStats) throws SQLException {
		return run.update( "insert into t_pet_stats (speciesId,breedId,health,power,speed) values(?,?,?,?,?)",
				petStats.getSpeciesId(), petStats.getBreedId(), petStats.getHealth(), petStats.getPower(), petStats.getSpeed());
	}
	
	/*
	 * 获取所有pet breed信息
	 */
	public List<PetBreed> getPetBreeds() throws SQLException {
		return run.query("select petSpeciesId,petBreedId from t_pet_breed", new BeanListHandler<PetBreed>(PetBreed.class));
	}
	
	/*
	 * 获取所有pet stats信息
	 */
	public List<PetBreed> getNewPetBreed() throws SQLException {
		return run.query("select petSpeciesId,petBreedId from (select * from t_pet_breed pb left join t_pet_stats ps on ps.speciesId=pb.petSpeciesId) h where health is null", new BeanListHandler<PetBreed>(PetBreed.class));
	}
	
	/*
	 * 获取所有t_latest_auction中的pet breed信息
	 */
	public List<PetBreed> getAuctionPetBreeds() throws SQLException {
		return run.query("select petSpeciesId,petBreedId from t_latest_auction where itemId=82800 and petBreedId!=0 group by petSpeciesId,petBreedId", new BeanListHandler<PetBreed>(PetBreed.class));
	}
}
