package pl.zzpj.spacer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.zzpj.spacer.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByUsername(String username);
}
