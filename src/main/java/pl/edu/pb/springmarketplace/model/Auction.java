package pl.edu.pb.springmarketplace.model;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pb.springmarketplace.appuser.AppUser;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private BigDecimal price;
    private Boolean published = false;
    @ManyToOne(optional = false)
    private AppUser creator;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
