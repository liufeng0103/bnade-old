package lf.bnade.model;

public class PetBreed {
	private int petSpeciesId;
	private int petBreedId;

	public int getPetSpeciesId() {
		return petSpeciesId;
	}

	public void setPetSpeciesId(int petSpeciesId) {
		this.petSpeciesId = petSpeciesId;
	}

	public int getPetBreedId() {
		return petBreedId;
	}

	public void setPetBreedId(int petBreedId) {
		this.petBreedId = petBreedId;
	}

	@Override
	public boolean equals(Object obj) {
		PetBreed pb = (PetBreed)obj;
		return this.petSpeciesId == pb.petSpeciesId && this.petBreedId == pb.petBreedId;
	}
}
