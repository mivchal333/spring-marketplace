package pl.edu.pb.springmarketplace.model;

import lombok.Getter;
import lombok.Setter;

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
    @Column(nullable = false)
    private String creatorUsername;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
