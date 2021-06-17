package pl.edu.pb.springmarketplace.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.access.AccessDeniedException;
import pl.edu.pb.springmarketplace.appuser.AppUser;
import pl.edu.pb.springmarketplace.model.Auction;
import pl.edu.pb.springmarketplace.model.repository.AuctionRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

public class AuctionServiceTest {

    AuctionService sut;
    AuctionRepository auctionRepository = Mockito.mock(AuctionRepository.class);
    AuthFacade authFacade = Mockito.mock(AuthFacade.class);


    @BeforeEach
    public void setUp() {
        sut = new AuctionServiceImpl(auctionRepository, authFacade);
    }

    @Test
    public void testFindAllEmpty() {
        Mockito.when(auctionRepository.findAll()).thenReturn(Collections.emptyList());
        Iterable<Auction> all = sut.findPublished();

        Assertions.assertFalse(all.iterator().hasNext());
    }

    @Test
    public void testFindAllFilled() {
        Auction auction = prepareAuction();

        Mockito.when(auctionRepository.findAllByPublishedTrue()).thenReturn(Collections.singletonList(auction));
        Iterable<Auction> all = sut.findPublished();

        Assertions.assertTrue(all.iterator().hasNext());
    }

    @Test
    public void shouldThrowAccessDeniedException() {
        Long id = 3L;
        String user1 = "user1";
        Auction auction = prepareAuction();

        Mockito.when(auctionRepository.findById(id))
                .thenReturn(Optional.of(auction));
        Mockito.when(authFacade.getCurrentUserName())
                .thenReturn(user1);

        Assertions.assertThrows(AccessDeniedException.class, () -> {
            sut.changePublishState(id, false);
        });
    }

    private Auction prepareAuction() {
        Auction auction = new Auction();
        auction.setTitle("title");
        auction.setDescription("desc");
        auction.setPrice(BigDecimal.ONE);
        AppUser user = new AppUser();
        auction.setCreator(user);
        auction.setPublished(false);
        return auction;
    }
}
