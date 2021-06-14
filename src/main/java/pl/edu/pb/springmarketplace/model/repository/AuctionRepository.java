package pl.edu.pb.springmarketplace.model.repository;

import org.springframework.stereotype.Repository;
import pl.edu.pb.springmarketplace.model.Auction;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface AuctionRepository extends CrudRepository<Auction, Long> {
}
