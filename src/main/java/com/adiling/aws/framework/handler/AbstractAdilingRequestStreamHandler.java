package com.adiling.aws.framework.handler;

import com.adiling.aws.framework.config.DaggerFrameworkComponent;
import com.adiling.aws.framework.config.FrameworkComponent;
import com.adiling.aws.framework.dao.IFrameworkDAOConfig;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractAdilingRequestStreamHandler implements AdilingRequestStreamHandler {

    @Inject
    protected IFrameworkDAOConfig frameworkDAOConfig;
    @Inject
    protected ObjectMapper objectMapper;

    private final FrameworkComponent frameworkComponent;
    protected AbstractAdilingRequestStreamHandler() {
        frameworkComponent = DaggerFrameworkComponent.builder().build();
        frameworkComponent.inject(this);
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        this.myLambdaFunction(inputStream, outputStream, context);
    }

    protected <T> T getObjectFromInputStream(InputStream inputStream, Class<T> returnedObjectClass) throws IOException {
        JsonNode event = objectMapper.readTree(inputStream);
        JsonNode body = event.findValue("body");
        return objectMapper.treeToValue(
                objectMapper.readTree(body.asText()),
                returnedObjectClass);
    }

    public abstract void myLambdaFunction(InputStream inputStream, OutputStream outputStream, Context context) throws IOException;
}
