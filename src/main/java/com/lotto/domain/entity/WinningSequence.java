package com.lotto.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "tb_winning_sequence", indexes = {
        @Index(name = "idx_target_seq", columnList = "target_seq")
})
@Getter
public class WinningSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer targetSeq;

    @Column(nullable = false)
    private Integer winRank;

    @Column(nullable = false)
    private Boolean isIssued = false;

    public WinningSequence(Integer targetSeq, Integer winRank) {
        this.targetSeq = targetSeq;
        this.winRank = winRank;
        this.isIssued = false;
    }

    public void markAsIssued() {
        this.isIssued = true;
    }
}
