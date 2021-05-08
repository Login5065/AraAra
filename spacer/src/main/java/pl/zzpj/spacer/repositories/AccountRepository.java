package pl.zzpj.spacer.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.zzpj.spacer.model.Account;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
