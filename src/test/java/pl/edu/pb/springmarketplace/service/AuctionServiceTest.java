package pl.edu.pb.springmarketplace.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.edu.pb.springmarketplace.model.Auction;
import pl.edu.pb.springmarketplace.model.repository.AuctionRepository;

import java.math.BigDecimal;
import java.util.Collections;

public class AuctionServiceTest {

    AuctionService sut;
    AuctionRepository auctionRepository = Mockito.mock(AuctionRepository.class);


    @BeforeEach
    public void setUp() {
        sut = new AuctionServiceImpl(auctionRepository);
    }

    @Test
    public void testFindAllEmpty() {
        Mockito.when(auctionRepository.findAll()).thenReturn(Collections.emptyList());
        Iterable<Auction> all = sut.findAll();

        Assertions.assertFalse(all.iterator().hasNext());
    }

    @Test
    public void testFindAllFilled() {
        Auction auction = new Auction();
        auction.setTitle("title");
        auction.setDescription("desc");
        auction.setPrice(BigDecimal.ONE);

        Mockito.when(auctionRepository.findAll()).thenReturn(Collections.singletonList(auction));
        Iterable<Auction> all = sut.findAll();

        Assertions.assertTrue(all.iterator().hasNext());
    }
}
