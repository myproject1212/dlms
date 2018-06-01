package com.erac.dlm;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import javax.cache.CacheManager;
import javax.cache.Caching;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {
  private static final Long MAX_ELEMENTS_IN_MEMORY = 1000L;
  private static final Long LONG_DURATION = 36000L;

  @Bean
  @Override
  public org.springframework.cache.CacheManager cacheManager() {
    CacheManager cacheManager = createInMemoryCacheManager();
    return new JCacheCacheManager(cacheManager);
  }

  private CacheManager createInMemoryCacheManager() {
    Map<String, CacheConfiguration<?, ?>> caches = createCacheConfigurations();

    EhcacheCachingProvider provider = getCachingProvider();
    DefaultConfiguration configuration = new DefaultConfiguration(caches, provider.getDefaultClassLoader());
    return provider.getCacheManager(provider.getDefaultURI(), configuration);
  }

  private Map<String, CacheConfiguration<?, ?>> createCacheConfigurations() {
    CacheConfiguration<Object, Object> longCacheConfiguration =
        CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(MAX_ELEMENTS_IN_MEMORY))
            .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(LONG_DURATION))).build();

    Map<String, CacheConfiguration<?, ?>> caches = new HashMap<>();
    caches.put(Caches.AUTHENTICATE_USER_CACHE, longCacheConfiguration);
    caches.put(Caches.LOCAL_SERVICE_AUTHENTICATION_APP_SEC_TOKEN_CACHE, longCacheConfiguration);
    caches.put(Caches.REMOTE_SERVICE_AUTHENTICATION_APP_SEC_TOKEN_CACHE, longCacheConfiguration);

    return caches;
  }

  private EhcacheCachingProvider getCachingProvider() {
    return (EhcacheCachingProvider) Caching.getCachingProvider();
  }
}
