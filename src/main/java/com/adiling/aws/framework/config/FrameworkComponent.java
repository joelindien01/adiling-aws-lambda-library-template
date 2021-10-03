package com.adiling.aws.framework.config;

import com.adiling.aws.framework.handler.AdilingRequestStreamHandler;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {FrameworkModule.class})
public interface FrameworkComponent {
    void inject(AdilingRequestStreamHandler requestHandler);
}
