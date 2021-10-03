package com.adiling.aws.framework.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public interface IFrameworkDAOConfig {

    String getTableName();
    DynamoDBMapper getDynamoDBMapper();
    int getPageSize();
}
