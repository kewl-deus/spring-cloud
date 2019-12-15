package com.example.customerservice.util;

import com.example.customerservice.model.valueobject.CustomerIdentifier;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerIdentifierJsonConverter implements Converter<String, CustomerIdentifier> {
    @Override
    public CustomerIdentifier convert(String source) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            return mapper.readValue(source, CustomerIdentifier.class);
        } catch (JsonProcessingException e) {
            throw new ConversionFailedException(TypeDescriptor.forObject(source), TypeDescriptor.valueOf(CustomerIdentifier.class), source, e);
        }
    }
}
