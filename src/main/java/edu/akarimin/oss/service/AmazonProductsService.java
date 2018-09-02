package edu.akarimin.oss.service;

/**
 * @author akarimin
 */

public interface AmazonProductsService<S, E> {

    E asyncEstimate(S keyword);

}
