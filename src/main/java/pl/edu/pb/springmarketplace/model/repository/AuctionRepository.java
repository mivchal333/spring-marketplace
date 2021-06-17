package pl.edu.pb.springmarketplace.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pb.springmarketplace.model.Auction;

import java.util.List;

@Repository
public interface AuctionRepository extends CrudRepository<Auction, Long> {
    List<Auction> findAllByPublishedTrue();

    List<Auction> findAllByCreatorUsername(String name);

}
