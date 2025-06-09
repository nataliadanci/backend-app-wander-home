package demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="real_estates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealEstate {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "real_estate_type")
    private String realEstateType;

    @Column(name = "status")
    private String status;

    @Column(name = "image_path")
    private String image;

    @OneToMany(mappedBy = "realEstate")
    @OrderBy("dateAdded DESC")
    List<Review> reviewsReceived;

    @ManyToMany(mappedBy = "favoriteRealEstates")
    private Set<Client> interestedClients;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Client owner;

    @OneToMany(mappedBy = "rentalRealEstate")
    Set<Rental> rentals;

    @ManyToMany(mappedBy = "realEstatesSwitched")
    //@OrderBy("gpol_id DESC")
    private Set<SwitchBooking> switchBookings = new LinkedHashSet<SwitchBooking>(0);
}
