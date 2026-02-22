package com.lotto.domain.repository;

import com.lotto.domain.entity.WinningSequence;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface WinningSequenceRepository extends JpaRepository<WinningSequence, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM WinningSequence w WHERE w.targetSeq = :seq AND w.isIssued = false")
    Optional<WinningSequence> findByTargetSeqWithLock(Integer seq);
}
