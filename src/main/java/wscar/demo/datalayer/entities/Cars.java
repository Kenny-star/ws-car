package wscar.demo.datalayer.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="car_data")
@NoArgsConstructor
@Setter
@Getter
public class Cars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String car_id;
    private String model;
    private String cost;
    private String provider;
    private String release_year;
    private String country;
}
