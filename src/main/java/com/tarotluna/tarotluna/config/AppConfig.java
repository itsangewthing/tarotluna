package com.tarotluna.tarotluna.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    // Below is S3 config code. 
    // utilizing local mysql db; AWS does not run
    
    // @Value("${do.space.secret}")
    // private String secret;

    // @Value("${do.space.key}")
    // private String key;

    // @Value("${do.space.region}")
    // private String region = "sgp1";

    // @Value("${do.space.endpoint}")
    // private String endpoint = "sgp1.digitaloceanspaces.com";

    // @Bean
    // public AmazonS3 createS3Client() {
    //     EndpointConfiguration config = new EndpointConfiguration(endpoint, region);
    //     BasicAWSCredentials cred = new BasicAWSCredentials(key, secret);
    //     return AmazonS3ClientBuilder.standard()
    //                                 .withEndpointConfiguration(config)
    //                                 .withCredentials(new AWSStaticCredentialsProvider(cred))
    //                                 .build();
    // }
}
