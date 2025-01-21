package de.ma.form.jaxbschemagenerationexample;

import jakarta.xml.bind.Marshaller;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

@Configuration
public class XmlConfig implements WebMvcConfigurer {


    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("de.ma.form.generated");
        marshaller.setMarshallerProperties(Map.of(
                Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE,
                Marshaller.JAXB_ENCODING, "UTF-8"
        ));
        return marshaller;
    }
}
