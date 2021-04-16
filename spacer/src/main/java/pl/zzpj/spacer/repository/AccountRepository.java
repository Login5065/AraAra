package pl.zzpj.spacer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import pl.zzpj.spacer.model.AccountEntity;

@Component
public interface AccountRepository extends MongoRepository<AccountEntity, Long> {

}
