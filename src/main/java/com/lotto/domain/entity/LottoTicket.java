package com.lotto.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor @Getter
@Table(name = "tb_lotto_ticket", indexes = {
        @Index(name = "idx_phone_hash", columnList = "phoneHash"),
        @Index(name = "idx_entry_order", columnList = "entryOrder")
})
public class LottoTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_hash", unique = true, nullable = false)
    private String phoneHash;

    @Column(nullable = false)
    private Integer entryOrder; // 참여 순번


    @Column(nullable = false) private Integer n1;
    @Column(nullable = false) private Integer n2;
    @Column(nullable = false) private Integer n3;
    @Column(nullable = false) private Integer n4;
    @Column(nullable = false) private Integer n5;
    @Column(nullable = false) private Integer n6;

    @Column(name = "win_rank", nullable = false)
    private Integer winRank;

    @Column(name = "view_cnt", nullable = false)
    private Integer viewCnt = 0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 참가일시


    @Builder
    public LottoTicket(String phoneHash, List<Integer> numbers, Integer winRank, Integer entryOrder) {
        // 리스트를 정렬하여 n1~n6에 할당하는 로직
        Collections.sort(numbers);
        this.phoneHash = phoneHash;
        this.n1 = numbers.get(0);
        this.n2 = numbers.get(1);
        this.n3 = numbers.get(2);
        this.n4 = numbers.get(3);
        this.n5 = numbers.get(4);
        this.n6 = numbers.get(5);
        this.winRank = winRank;
        this.entryOrder = entryOrder;
    }

    public void increaseViewCount() {
        this.viewCnt++;
    }

}
