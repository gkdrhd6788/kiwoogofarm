package b303.farm.garden.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Garden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "garden_owner")
    private String gardenOwner;

    @Column(name = "garden_name")
    private String gardenName;

    @Column(name = "garden_address")
    private String gardenAddress;

    @Column(name = "garden_size")
    private Long gardenSize;

    @Column(name = "garden_extra")
    private String gardenExtra;

    @Column(name = "garden_apply")
    private String gardenApply;

    @Column(name = "garden_lat")
    private Double gardenLat;

    @Column(name = "garden_long")
    private Double gardenLong;


}
