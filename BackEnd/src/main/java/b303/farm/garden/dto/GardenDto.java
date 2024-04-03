package b303.farm.garden.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GardenDto {
    private Long id;
    private String gardenOwner;
    private String gardenName;
    private String gardenAddress;
    private Long gardenSize;
    private String gardenExtra;
    private String gardenApply;
    private Double gardenLat;
    private Double gardenLong;

    @Builder
    public GardenDto(Long id, String gardenOwner, String gardenName, String gardenAddress, Long gardenSize, String gardenExtra, String gardenApply, Double gardenLat, Double gardenLong) {
        this.id = id;
        this.gardenOwner = gardenOwner;
        this.gardenName = gardenName;
        this.gardenAddress = gardenAddress;
        this.gardenSize = gardenSize;
        this.gardenExtra = gardenExtra;
        this.gardenApply = gardenApply;
        this.gardenLat = gardenLat;
        this.gardenLong = gardenLong;
    }
}
