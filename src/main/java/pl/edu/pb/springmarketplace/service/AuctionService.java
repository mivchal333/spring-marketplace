package pl.edu.pb.springmarketplace.service;

import pl.edu.pb.springmarketplace.model.Auction;

import java.util.Optional;

public interface AuctionService {

    Iterable<Auction> findPublished();

    Iterable<Auction> findAllMyAuctions();

    Auction save(Auction auction);

    Optional<Auction> findById(Long id);

    void deleteById(Long id);

    Auction publishAuction(Long id);
}
