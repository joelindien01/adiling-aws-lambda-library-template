package com.adiling.aws.framework.model.entity;

public interface DynamoDBSKey extends DynamoDBKey {
    String DynamoDB_SKEY_NAME = "sk";
    String computeSk();
}
