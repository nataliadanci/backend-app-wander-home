package demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="switch_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SwitchBooking {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    private String status;

    @ManyToMany
    @JoinTable(
            name = "switched_real_estates",
            joinColumns = @JoinColumn(name = "switched_booking_id"),
            inverseJoinColumns = @JoinColumn(name = "real_estate_id"))
    private Set<RealEstate> realEstatesSwitched = new LinkedHashSet<RealEstate>(0); //we will only allow a maximum of 2 in this set

}
