package ch.fhnw.deardevbackend.repositories;

import ch.fhnw.deardevbackend.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query(
            value = "select provider from accounts where \"userId\" = :userId",
            nativeQuery = true)
    String findProviderByUserId(@Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query(value = "delete from accounts where \"userId\" = :userId", nativeQuery = true)
    void deleteByUserId(Integer userId);
}
