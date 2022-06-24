package com.sprint.config;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnResource(resources = "classpath:git.properties")
public class ApplicationVersionConfig implements MeterBinder {

    final GitProperties gitProperties;

    public ApplicationVersionConfig(@Autowired GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Override
    public void bindTo(@NonNull MeterRegistry registry) {
        Gauge.builder("git.commit.count", this::extractCommitCount)
                .tag("build.version", gitProperties.get("build.version"))
                .tag("commit.message", gitProperties.get("commit.message.short"))
                .tag("branch", gitProperties.getBranch())
                .register(registry);
    }

    private int extractCommitCount() {
        try {
            return Integer.parseInt(gitProperties.get("total.commit.count"));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
