package com.example.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Objects;

public class SystemUtil {
    public static void copyPropsFromSrcToDest(Object src, Object dest) {
        Objects.requireNonNull(src, "src should not be null");
        Objects.requireNonNull(dest, "dest should not be null");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(src, dest);
    }

    public static <T> T castPropsToDestination(Object src, Class<T> dest) {
        Objects.requireNonNull(src, "src should not be null");
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(src, dest);
    }

    public static String readJSONField(String json, String name) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        jsonNode = jsonNode.get(name);
        return (jsonNode == null ? null : jsonNode.textValue());
    }

    public static String readJSONObject(String json, String name) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        jsonNode = jsonNode.get(name);
        return (jsonNode == null ? null : jsonNode.toPrettyString());
    }

    public static Object readJSONObject(String json, Class<?> objectClass) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        return (jsonNode == null ? null : mapper.convertValue(jsonNode, objectClass));
    }

    public static String writeObjectAsString(Object object) {
        String result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertJSONtoXML(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper xmlMapper = new ObjectMapper();
        JsonNode tree = objectMapper.readTree(json);
        String xml = xmlMapper.writer().withoutRootName().writeValueAsString(tree);
        return xml.replace("<>", "").replace("</>", "");
    }


    public static <T> String convertObjectToXML(T object, String rootName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper xmlMapper = new ObjectMapper();
        JsonNode tree = objectMapper.readTree(objectMapper.writeValueAsString(object));
        return xmlMapper.writer().withRootName(rootName).writeValueAsString(tree).replace("<>", "").replace("</>", "");
    }


}
