package demo.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name="clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    @Column(name = "image_path")
    private String image;

    @Column(name = "profile_status")
    private String profileStatus;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "client")
    Set<Review> reviewsWritten;

    @ManyToMany
    @JoinTable(
            name = "real_estate_favorites",
            joinColumns = @JoinColumn(name = "interested_client_id"),
            inverseJoinColumns = @JoinColumn(name = "real_estate_id"))
    private Set<RealEstate> favoriteRealEstates;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "owner_id", updatable = false)
    private Set<RealEstate> realEstatesOwned;

    @OneToMany(mappedBy = "rentalGuest")
    Set<Rental> rentals;

}
