package lf.bnade.ws;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lf.bnade.jmodel.Result;
import lf.bnade.service.PetService;

@Path("pet")
public class PetResource {
	
	private PetService petService;
	
	public PetResource() {
		petService = new PetService();
	}

	/*
	 * 宠物名称的模糊查询
	 */
	@GET
	@Path("/fuzzy/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getPetsByFuzzyName(@PathParam("name")String name) {
		if (name == null || "".equals(name)) {
			return Result.FAILED("宠物名不能为空");
		}
		try {
			return petService.getPetNamesByFuzzyName(name);
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED(e.getMessage());
		}		
	}
	
	/*
	 * 宠物查询
	 */
	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getPetByName(@PathParam("name")String name) {
		if (name == null || "".equals(name)) {
			return Result.FAILED("宠物名不能为空");
		}
		try {
			return petService.getPetByName(name);
		} catch (SQLException e) {
			e.printStackTrace();
			return Result.FAILED(e.getMessage());
		}		
	}
}
