package lotto.domain;

import lombok.Getter;
import lotto.constant.PrizeCondition;
import lotto.domain.Lotto;
import lotto.domain.LottoNumber;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WinningLotto extends Lotto {

    @Getter
    private LottoNumber bonusNumber;

    public WinningLotto(String winningNumberInput, String bonusNumberInput) {
        super(Arrays.stream(winningNumberInput.split(","))
                .map(String::trim)
                .map(LottoNumber::new)
                .collect(Collectors.toList()));
        this.bonusNumber = new LottoNumber(bonusNumberInput);
    }

    public PrizeCondition findPrize(Lotto targetLotto) {
        return PrizeCondition.findPrize(getMatchNumbersCount(targetLotto), isBonusMatch(targetLotto));
    }

    private boolean isBonusMatch(Lotto targetLotto) {
        return targetLotto.getLottoNumbers().contains(bonusNumber);
    }

    private int getMatchNumbersCount(Lotto targetLotto) {
        targetLotto.getLottoNumbers().sort(Comparator.comparingInt(LottoNumber::getLottoNumber));
        this.lottoNumbers.sort(Comparator.comparingInt(LottoNumber::getLottoNumber));
        int targetIdx = 0, winningIdx = 0;
        int matchNumbersCount = 0;
        while (targetIdx < targetLotto.lottoNumbers.size() && winningIdx < this.lottoNumbers.size()) {
            int targetNumber = targetLotto.lottoNumbers.get(targetIdx).getLottoNumber();
            int winningNumber = this.lottoNumbers.get(winningIdx).getLottoNumber();
            if (targetNumber == winningNumber) {
                matchNumbersCount++;
                targetIdx++;
                winningIdx++;
            }
            else if (targetNumber > winningNumber) {
                winningIdx++;
            }
            else {
                targetIdx++;
            }
        }
        System.out.println(matchNumbersCount);
        return matchNumbersCount;
    }
}