package com.lotto.domain.repository;

import com.lotto.domain.entity.WinnerWhiteList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinnerWhiteListRepository extends JpaRepository<WinnerWhiteList,String> {
    Optional<WinnerWhiteList> findByPhoneHashAndIsAppliedFalse(String phoneHash);
}
