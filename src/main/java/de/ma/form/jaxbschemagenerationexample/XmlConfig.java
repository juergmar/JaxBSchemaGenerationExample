package de.ma.form.jaxbschemagenerationexample;

import jakarta.xml.bind.Marshaller;
import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class XmlConfig implements WebMvcConfigurer {

    private static class CustomNamespacePrefixMapper extends NamespacePrefixMapper {
        @Override
        public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
            if ("http://mvp.bafin.de/sp/v1/mmdl/auslanz/".equals(namespaceUri)) {
                return "au";
            }
            return suggestion;
        }
    }

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("com.example.jaxb.model");

        Map<String, Object> props = new HashMap<>();
        props.put("org.glassfish.jaxb.namespacePrefixMapper", new CustomNamespacePrefixMapper());
        props.put("jaxb.formatted.output", Boolean.TRUE);

        marshaller.setMarshallerProperties(props);
        return marshaller;
    }

    @Bean
    public MarshallingHttpMessageConverter xmlConverter(Jaxb2Marshaller marshaller) {
        MarshallingHttpMessageConverter converter = new MarshallingHttpMessageConverter();
        converter.setMarshaller(marshaller);
        converter.setUnmarshaller(marshaller);
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(xmlConverter(marshaller()));
    }
}
