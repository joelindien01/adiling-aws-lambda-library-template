package com.adiling.aws.framework.model.entity;

public interface DynamoDBPkey {
    String DynamoDB_PKEY_NAME = "id";
    String computePk();
}
