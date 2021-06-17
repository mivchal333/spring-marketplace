package pl.edu.pb.springmarketplace.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.edu.pb.springmarketplace.appuser.AppUser;
import pl.edu.pb.springmarketplace.model.Auction;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailAddress;



    public void sendNotification(AppUser appUser, Auction auction) throws MailException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(appUser.getEmail());
        mail.setFrom(mailAddress);
        mail.setSubject(String.format("Your auction %s: %s has been published!", auction.getId(), auction.getTitle()));
        mail.setText("SIEMA BYKU");

        try {
            javaMailSender.send(mail);
        }catch (Exception e){
            log.error("Error while sending the mail, mail address: {}", appUser.getEmail());
        }

    }

    @Override
    public void sendAuctionPublishedMail(AppUser appUser, Auction auction) {

    }

    @Override
    public void sendAuctionUnpublishedMail(AppUser appUser, Auction auction) {

    }
}
