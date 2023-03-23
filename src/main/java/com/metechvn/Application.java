package com.metechvn;

import luongdev.cqrs.EnableCqrsBus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableCaching
@EnableJpaRepositories
@EnableCqrsBus
@SpringBootApplication(scanBasePackages = {"luongdev.*", "com.metechvn.*"})
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Autowired
//    DynamicEntityTypeRepository dynamicEntityTypeRepository;

    @Override
    public void run(String... args) {
//        var p1 = DynamicProperty.builder().code("NAME").displayName("Tên").dataType(DataType.KEYWORD).build();
//        var p2 = DynamicProperty.builder().code("AGE").displayName("Tuổi").dataType(DataType.KEYWORD).build();
//        var p3 = DynamicProperty.builder().code("PHONE").displayName("Số điện thoại").dataType(DataType.KEYWORD).build();
//
//        var p = DynamicEntityType.builder().code("CONTACT").displayName("Con tách").build();
//        p.setTenant("tenant1");
//        p.addProperties(p1, p2, p3);
//
//        dynamicEntityTypeRepository.save(p);


    }
}
