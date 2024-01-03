//package apishop.config;
//
//import io.micrometer.core.instrument.binder.mongodb.MongoMetricsCommandListener;
//import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import io.micrometer.core.instrument.MeterRegistry;
//import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
//
//
//@Configuration
//public class MongoConfiguration {
//    @Bean
//    public MeterRegistry meterRegistry() {
//        return new SimpleMeterRegistry();
//    }
//    @Bean
//    public MongoClientSettingsBuilderCustomizer mongoClientSettingsBuilderCustomizer(MeterRegistry meterRegistry) {
//        return builder -> builder.addCommandListener(new MongoMetricsCommandListener(meterRegistry));
//    }
//
//}
//
