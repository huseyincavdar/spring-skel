package com.cepheid.cloud.skel;

import com.cepheid.cloud.skel.model.State;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cepheid.cloud.skel.controller.ItemController;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.repository.ItemRepository;

@SpringBootApplication(scanBasePackageClasses = { ItemController.class, SkelApplication.class })
@EnableJpaRepositories(basePackageClasses = { ItemRepository.class })
public class SkelApplication {

  public static void main(String[] args) {
    SpringApplication.run(SkelApplication.class, args);
  }

  @Bean
  ApplicationRunner initItems(ItemRepository repository) {
    return args -> {
      Stream.of("Lord of the rings", "Hobbit", "Silmarillion", "Unfinished Tales and The History of Middle-earth")
          .forEach(name -> {
            Item item = new Item();
            item.setState(State.VALID);
            item.setName(name);
            repository.save(item);
          });
      repository.findAll().forEach(System.out::println);
    };
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration()
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
        .setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
    mapper.getConfiguration().setAmbiguityIgnored(true);
    return mapper;
  }

}
