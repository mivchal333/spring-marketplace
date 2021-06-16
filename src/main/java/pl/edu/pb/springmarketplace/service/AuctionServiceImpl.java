package pl.edu.pb.springmarketplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.edu.pb.springmarketplace.model.Auction;
import pl.edu.pb.springmarketplace.model.repository.AuctionRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;

    @Override
    public Iterable<Auction> findAll() {
        log.info("Listing all auctions.");
        return auctionRepository.findAll();
    }

    @Override
    public Auction save(Auction auction) {
        return auctionRepository.save(auction);
    }

    @Override
    public Optional<Auction> findById(Long id) {
        return auctionRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        try {
            auctionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error("Auction with id={} not found!", id);
        }
    }
}
