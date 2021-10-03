package com.adiling.aws.framework.config;

import com.adiling.aws.framework.dao.FrameworkDAOConfig;
import com.adiling.aws.framework.dao.IFrameworkDAOConfig;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Optional;

@Module
public class FrameworkModule {
    @Singleton
    @Provides
    @Named("tableName")
    String tableName() {
        return Optional.ofNullable(System.getenv("TABLE_NAME")).orElseThrow(() ->new RuntimeException("Table name not provided"));
    }

    @Singleton
    @Provides
    DynamoDBMapper dynamoDbClient() {
        final String endpoint = System.getenv("ENDPOINT_OVERRIDE");
        AmazonDynamoDBClientBuilder clientBuilder = AmazonDynamoDBClientBuilder.standard();
        if (endpoint != null && !endpoint.isEmpty()) {
            AWSCredentialsProvider credentialsProvide = new DefaultAWSCredentialsProviderChain();
            clientBuilder
                    .withEndpointConfiguration(new AwsClientBuilder
                            .EndpointConfiguration(endpoint, "us-east-1"));
            clientBuilder.setCredentials(credentialsProvide);
        }
        DynamoDBMapperConfig mapperConfig = new DynamoDBMapperConfig.Builder().withTableNameOverride(DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(this.tableName()))
                .build();
        return new DynamoDBMapper(clientBuilder.build(), mapperConfig);
    }
    @Singleton
    @Provides
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }
    @Singleton
    @Provides
    public IFrameworkDAOConfig frameworkDAO(DynamoDBMapper mapper, @Named("tableName") String tableName) {
        return new FrameworkDAOConfig(mapper, tableName,10);
    }

}
