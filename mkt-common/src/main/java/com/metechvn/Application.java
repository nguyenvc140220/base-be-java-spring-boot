//package com.metechvn;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootApplication
//@EnableJpaRepositories
//public class Application implements CommandLineRunner {
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
//
////    @Autowired
////    DynamicEntityTypeRepo dynamicEntityRepo;
//
//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
////        var s = dynamicEntityRepo.findIncludeProperties();
//
////        System.out.println(s);
//
////        var e = new DynamicEntityType();
////        e.setId(UUID.randomUUID());
////        e.setCode("CODE_1");
////
////        e.setProperties(new HashMap<>());
////
////        var f = new DynamicEntityTypeProperty();
////        f.setId(UUID.randomUUID());
////        f.setCode("CANG");
////
////        e.getProperties().put("CANG", f);
////
////        dynamicEntityRepo.save(e);
//    }
//
////    @EventListener(DynamicEntityTypeCreatedEvent.class)
////    public void Void (DynamicEntityTypeCreatedEvent e) {
////        System.out.println(e);
////    }
//}
