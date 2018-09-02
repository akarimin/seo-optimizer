package edu.akarimin.oss.domain;

/**
 * @author akarimin@buffalo.edu
 */
public class CompletionModel {

    private String method = "completion";
    private int mkt = 1;
    private String searchAlias = "aps";
    private String query;

    public String getMethod() {
        return method;
    }

    public CompletionModel setMethod(String method) {
        this.method = method;
        return this;
    }

    public int getMkt() {
        return mkt;
    }

    public CompletionModel setMkt(int mkt) {
        this.mkt = mkt;
        return this;
    }

    public String getSearchAlias() {
        return searchAlias;
    }

    public CompletionModel setSearchAlias(String searchAlias) {
        this.searchAlias = searchAlias;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public CompletionModel setQuery(String query) {
        this.query = query;
        return this;
    }
}
