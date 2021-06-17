package pl.edu.pb.springmarketplace.service;

import org.springframework.scheduling.annotation.Async;
import pl.edu.pb.springmarketplace.appuser.AppUser;
import pl.edu.pb.springmarketplace.model.Auction;

@Async
public interface EmailService {

    void sendAuctionPublishedMail(AppUser appUser, Auction auction);

    void sendAuctionUnpublishedMail(AppUser appUser, Auction auction);
}
