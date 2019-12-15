package com.example.customerservice.util;

import com.example.customerservice.model.valueobject.CustomerIdentifier;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class CustomerIdentifierJsonConverterTest {

    @Test
    public void shouldDeserialize(){
        String json ="{key:'5d438c12-343b-49f2-810d-98d2515fe7be', type:'external'}";
        CustomerIdentifier customerIdentifier = new CustomerIdentifierJsonConverter().convert(json);
        assertThat(customerIdentifier.getKey()).isEqualTo("5d438c12-343b-49f2-810d-98d2515fe7be");
        assertThat(customerIdentifier.getType()).isEqualTo("external");
    }
}
