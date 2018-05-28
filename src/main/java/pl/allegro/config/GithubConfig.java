package pl.allegro.config;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GithubConfig {

  @Bean
  public RestTemplate createRestTemplate(ObjectMapper objectMapper) {
    final RestTemplate restTemplate = new RestTemplate();

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(objectMapper);
    restTemplate.getMessageConverters().add(converter);

    return restTemplate;
  }

  @Bean
  public ObjectMapper objectMapper() {
    final ObjectMapper mapper = new ObjectMapper();

    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule
        .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ISO_DATE_TIME));
    javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(ISO_DATE));
    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(ISO_DATE));
    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ISO_DATE_TIME));
    mapper.registerModule(javaTimeModule);
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    return mapper;
  }
}