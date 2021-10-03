package com.adiling.aws.framework.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.Getter;

@Getter
public class FrameworkDAOConfig implements IFrameworkDAOConfig {

    private final String tableName;
    private final DynamoDBMapper dynamoDBMapper;
    private final int pageSize;
    public FrameworkDAOConfig(final DynamoDBMapper mapper, final String tableName, final int pageSize) {
        this.dynamoDBMapper = mapper;
        this.tableName = tableName;
        this.pageSize = pageSize;
    }
}
