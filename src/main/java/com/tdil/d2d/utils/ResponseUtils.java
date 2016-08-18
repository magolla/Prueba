/**
 */
package com.tdil.d2d.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ResponseUtils {

    private static Logger logger = LogManager.getLogger(ResponseUtils.class);


    public static ObjectMapper getJsonMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

        return mapper;
    }
    
    public static ObjectMapper getJsonMapperNoCamel() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false);
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        mapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return mapper;
    }

    public static String createJsonString(Object object) {
        String jsonObject = null;
        try {
            if (object != null) {
                jsonObject = getJsonMapper().writeValueAsString(object);
            } else {
                jsonObject = new JSONObject(StringUtils.defaultString(null)).toString();
            }
        } catch (Exception e) {
            logger.error(ResponseUtils.class, e);
        }

        return jsonObject;
    }

    public static <E> E parseData(String jsonString, Class<E> type) {
        E response = null;
        try {
            ObjectMapper mapper = getJsonMapper();
            response = mapper.readValue(jsonString, type);
        } catch (Exception e) {
            logger.error(ResponseUtils.class, e);
        }

        return response;
    }
    
    public static <E> E parseDataNoCamel(String jsonString, Class<E> type) {
        E response = null;
        try {
            ObjectMapper mapper = getJsonMapperNoCamel();
            response = mapper.readValue(jsonString, type);
        } catch (Exception e) {
            logger.error(ResponseUtils.class, e);
        }

        return response;
    }

    public static String toString(HttpEntity entity) {
        String content = StringUtils.defaultString(null);
        try {
            content = EntityUtils.toString(entity);
        } catch (Exception e) {
            logger.error(ResponseUtils.class, e);
        }
        return content;
    }

}
