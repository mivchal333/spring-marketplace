package pl.edu.pb.springmarketplace.appuser;

import pl.edu.pb.springmarketplace.model.Auction;

import java.util.Optional;

public interface AppUserService {

    AppUser buildAndSaveUser(AppUser appUser);

    AppUser registerNewUser(AppUser appUser);


    Iterable<AppUser> findAll();

    Optional<AppUser> findById(Long id);

    void deleteById(Long id);


}
