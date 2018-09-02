package edu.akarimin.oss.service;

import edu.akarimin.oss.domain.CompletionModel;
import edu.akarimin.oss.domain.Estimate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static edu.akarimin.oss.utils.RestTemplateBuilder.httpClient;
import static java.math.BigDecimal.*;
import static java.math.MathContext.DECIMAL32;
import static java.math.RoundingMode.HALF_UP;

/**
 * @author akarimin
 */
@Service
public class ProductsService implements AmazonProductsService<String, Estimate> {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public Estimate asyncEstimate(String keyword) {
        return new Estimate()
                .setKeyword(keyword)
                .setScore(new BigDecimal(Stream.iterate(1, i -> i + 1)
                .limit(keyword.length())
                .parallel()
                .map(index -> printLog(index, setCoefficient(index), keyword)
                .asyncAmazonHttpClient(index, keyword))
                .map(this::registerFuture)
                .map(this::setTotalScore)
                .collect(Collectors.averagingInt(BigDecimal::intValue)))
                .round(DECIMAL32));
    }

    private ProductsService printLog(int index, BigDecimal loopScore, String keyword) {
        System.out.println("==================================================================================================="
                + "\nLOOP: "
                + index
                + "\nLOOP SCORE: "
                + loopScore
                + "\nSUB-KEY-WORD: "
                + keyword.substring(0, index)
                + "\n===================================================================================================");
        return this;
    }

    private BigDecimal setCoefficient(int index) {
        return ONE.subtract(new BigDecimal(index - 1)
                .divide(TEN, 2, HALF_UP))
                .multiply(new BigDecimal(10));
    }

    private CompletionModel setAmazonModel(String keyword, int index) {
        return new CompletionModel()
                .setQuery(keyword.substring(0, index));
    }

    private Future<BigDecimal> asyncAmazonHttpClient(int index, String keyword) {
        return executorService.submit(() -> setCoefficient(index)
                .multiply(new BigDecimal(httpClient(setAmazonModel(keyword, index))
                .stream()
                .filter(es -> es.getKeyword().contains(keyword))
                .mapToInt(x -> x.getScore().intValue()).sum())));
    }

    private BigDecimal registerFuture(Future<BigDecimal> future) {
        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return ZERO;
        }
    }

    private BigDecimal setTotalScore(BigDecimal subScore) {
        return subScore.divide(new BigDecimal(55), 2, HALF_UP);
    }
}
