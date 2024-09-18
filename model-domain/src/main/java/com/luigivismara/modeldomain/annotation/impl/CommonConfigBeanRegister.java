package com.luigivismara.modeldomain.annotation.impl;

import com.luigivismara.modeldomain.annotation.CommonConfig;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommonConfigBeanRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, org.springframework.beans.factory.support.BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(CommonConfig.class.getName());

        String[] basePackages = (String[]) attributes.get("basePackages");

        ComponentScan componentScan = new ComponentScan() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return ComponentScan.class;
            }

            @Override
            public String[] value() {
                return new String[0];
            }

            @Override
            public String[] basePackages() {
                return basePackages;
            }

            @Override
            public Class<?>[] basePackageClasses() {
                return new Class[0];
            }

            @Override
            public Class<? extends BeanNameGenerator> nameGenerator() {
                return null;
            }

            @Override
            public Class<? extends ScopeMetadataResolver> scopeResolver() {
                return null;
            }

            @Override
            public ScopedProxyMode scopedProxy() {
                return null;
            }

            @Override
            public String resourcePattern() {
                return "";
            }

            @Override
            public boolean useDefaultFilters() {
                return false;
            }

            @Override
            public Filter[] includeFilters() {
                return new Filter[0];
            }

            @Override
            public Filter[] excludeFilters() {
                return new Filter[0];
            }

            @Override
            public boolean lazyInit() {
                return false;
            }

        };

        EnableJpaRepositories enableJpaRepositories = new EnableJpaRepositories() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return EnableJpaRepositories.class;
            }

            @Override
            public String[] value() {
                return new String[0];
            }

            @Override
            public String[] basePackages() {
                return basePackages;
            }

            @Override
            public Class<?>[] basePackageClasses() {
                return new Class[0];
            }

            @Override
            public ComponentScan.Filter[] includeFilters() {
                return new ComponentScan.Filter[0];
            }

            @Override
            public ComponentScan.Filter[] excludeFilters() {
                return new ComponentScan.Filter[0];
            }

            @Override
            public String repositoryImplementationPostfix() {
                return "";
            }

            @Override
            public String namedQueriesLocation() {
                return "";
            }

            @Override
            public QueryLookupStrategy.Key queryLookupStrategy() {
                return null;
            }

            @Override
            public Class<?> repositoryFactoryBeanClass() {
                return null;
            }

            @Override
            public Class<?> repositoryBaseClass() {
                return null;
            }

            @Override
            public String entityManagerFactoryRef() {
                return "";
            }

            @Override
            public String transactionManagerRef() {
                return "";
            }

            @Override
            public boolean considerNestedRepositories() {
                return false;
            }

            @Override
            public boolean enableDefaultTransactions() {
                return false;
            }

            @Override
            public BootstrapMode bootstrapMode() {
                return null;
            }

            @Override
            public char escapeCharacter() {
                return 0;
            }

        };

        EntityScan entityScan = new EntityScan() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return EntityScan.class;
            }

            @Override
            public String[] value() {
                return new String[0];
            }

            @Override
            public String[] basePackages() {
                return basePackages;
            }

            @Override
            public Class<?>[] basePackageClasses() {
                return new Class[0];
            }
        };

        ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);
    }
}
