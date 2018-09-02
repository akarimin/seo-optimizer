package edu.akarimin.oss.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author akarimin@buffalo.edu
 */


final class JacksonMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {

        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    static ObjectMapper getMapper() {
        return MAPPER;
    }
}
