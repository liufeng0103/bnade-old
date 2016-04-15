package lf.bnade.vo;

import java.util.List;

public class AuctionPetBreed {
	private int petSpeciesId;
	private List<AuctionPetStats> petStats;

	public int getPetSpeciesId() {
		return petSpeciesId;
	}

	public void setPetSpeciesId(int petSpeciesId) {
		this.petSpeciesId = petSpeciesId;
	}

	public List<AuctionPetStats> getPetStats() {
		return petStats;
	}

	public void setPetStats(List<AuctionPetStats> petStats) {
		this.petStats = petStats;
	}

}
