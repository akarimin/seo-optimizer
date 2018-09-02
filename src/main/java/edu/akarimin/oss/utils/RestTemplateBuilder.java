package edu.akarimin.oss.utils;

import edu.akarimin.oss.constants.ApplicationConstants;
import edu.akarimin.oss.domain.CompletionModel;
import edu.akarimin.oss.domain.Estimate;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.apache.http.impl.client.HttpClientBuilder.create;
import static org.apache.http.protocol.HTTP.USER_AGENT;


/**
 * @author akarimin@buffalo.edu
 */

public class RestTemplateBuilder {

    public static List<Estimate> httpClient(CompletionModel model) throws IOException {
        String content = getAmazonResponseContent(model);
        return mapToWeightedList(content);
    }

    private static String urlParamBuilder(CompletionModel model) {
        return String.format(ApplicationConstants.getAmazonCompletionUrl(),
                ApplicationConstants.getAmazonCompletionHost(), model.getMethod(),
                model.getMkt(), model.getSearchAlias(), model.getQuery());
    }

    private static String getAmazonResponseContent(CompletionModel model) throws IOException {
        HttpClient client = create().build();
        HttpGet request = new HttpGet(urlParamBuilder(model));
        request.addHeader(ApplicationConstants.getUserAgent(), USER_AGENT);
        HttpResponse response = client.execute(request);
        System.out.println("\nSending 'GET' request to URL : " + urlParamBuilder(model));
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return JacksonMapper.getMapper().writeValueAsString(result.toString())
                .substring(14, JacksonMapper.getMapper().writeValueAsString(result.toString()).length() - 11)
                .replaceAll("\\\\", "");
    }

    private static List<Estimate> mapToWeightedList(String content) throws IOException {
        Object[] products = JacksonMapper.getMapper().readValue(content, Object[].class);
        List<String> estimates = new ArrayList<>(10);
        estimates.addAll(Objects.nonNull(products[1]) ? (List) products[1] : null);
        List<Estimate> weightedList = new ArrayList<>(10);
        if (estimates.size() != 0) {
            IntStream.range(0, estimates.size())
            .forEach(index -> {
                Estimate weighedEstimate = new Estimate()
                .setKeyword(estimates.get(index))
                .setScore(BigDecimal.ONE.subtract(new BigDecimal(index)
                .divide(BigDecimal.TEN)).multiply(new BigDecimal(100)));
                weightedList.add(weighedEstimate);
            });

        }
        nullOut(products, estimates);
        weightedList.forEach(System.out::println);
        return weightedList;
    }

    private static void nullOut(Object[] products, List<String> estimates) {
        products[0] = null;
        products[1] = null;
        products[2] = null;
        products[3] = null;
        estimates.clear();
    }
}
