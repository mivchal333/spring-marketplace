package pl.edu.pb.springmarketplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.edu.pb.springmarketplace.model.Auction;
import pl.edu.pb.springmarketplace.model.repository.AuctionRepository;
import pl.edu.pb.springmarketplace.service.exception.AuctionNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final AuthFacade authFacade;

    @Override
    public Iterable<Auction> findPublished() {
        log.info("Listing all published auctions.");
        return auctionRepository.findAllByPublishedTrue();
    }

    @Override
    public Iterable<Auction> findAll() {
        log.info("Listing all auctions");
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

    @Override
    public Auction changePublishState(Long id, Boolean isPublish) {
        Auction auction = auctionRepository.findById(id)
                .orElseThrow(() -> new AuctionNotFoundException("Not found auction id=" + id));

        auction.setPublished(isPublish);
        log.info("Auction with id={} published", id);
        return save(auction);
    }
}
