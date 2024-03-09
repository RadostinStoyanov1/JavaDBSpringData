package p11springdataintro.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import p11springdataintro.data.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
