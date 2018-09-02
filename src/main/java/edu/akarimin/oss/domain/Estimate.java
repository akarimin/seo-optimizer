package edu.akarimin.oss.domain;

import java.math.BigDecimal;

/**
 * @author akarimin
 */

public class Estimate {

    private String keyword;
    private BigDecimal score;

    public String getKeyword() {
        return keyword;
    }

    public Estimate setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public BigDecimal getScore() {
        return score;
    }

    public Estimate setScore(BigDecimal score) {
        this.score = score;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Estimate estimate = (Estimate) o;

        return keyword != null ? keyword.equals(estimate.keyword) : estimate.keyword == null;
    }

    @Override
    public int hashCode() {
        return keyword != null ? keyword.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Estimate{" +
                "keyword='" + keyword + '\'' +
                ", score=" + score +
                '}';
    }
}
