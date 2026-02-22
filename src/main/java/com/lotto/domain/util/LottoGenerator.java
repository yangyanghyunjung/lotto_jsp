package com.lotto.domain.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/* 로또 번호 생성기 : "6자리 동일", "5자리 동일" 등의 조건을 반영한 알고리즘 */
public class LottoGenerator {
    private static final List<Integer> WINNING_NUMBERS = List.of(7, 14, 21, 28, 35, 42);

    public static List<Integer> generate(int rank) {
        List<Integer> result;

        switch (rank) {
            case 1:
                result = new ArrayList<>(WINNING_NUMBERS);
                break;
            case 2:
            case 3:
            case 4:
                result = generateMatchedList(7 - rank);
                break;
            default:
                result = generateRandomList();
                break;
        }

        Collections.sort(result);
        return result;

    }

    private static List<Integer> generateMatchedList(int matchCount) {
        List<Integer> base = new ArrayList<>(WINNING_NUMBERS);
        Collections.shuffle(base);

        List<Integer> result = new ArrayList<>(base.subList(0, matchCount));
        fillWithUniqueNumbers(result);

        return result;

    }

    private static List<Integer> generateRandomList() {
        List<Integer> base = new ArrayList<>(WINNING_NUMBERS);
        Collections.shuffle(base);

        Random random = new Random();
        int matchCount = random.nextInt(3);
        List<Integer> result = new ArrayList<>(base.subList(0, matchCount));
        fillWithUniqueNumbers(result);

        return result;

    }

    private static void fillWithUniqueNumbers(List<Integer> list) {
        Random random = new Random();
        while (list.size() < 6) {
            int num = random.nextInt(45) + 1;
            if (!list.contains(num) && !WINNING_NUMBERS.contains(num)) {
                list.add(num);
            }
        }
    }
}
