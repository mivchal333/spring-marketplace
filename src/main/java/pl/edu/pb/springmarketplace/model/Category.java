package pl.edu.pb.springmarketplace.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(
            mappedBy = "id",
            orphanRemoval = true
    )
    private List<Auction> auctions = new LinkedList<>();

    @Override
    public String toString() {
        return name;
    }
}
