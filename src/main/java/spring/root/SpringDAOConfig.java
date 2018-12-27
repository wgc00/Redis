package spring.root;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;

@Configuration
/*@ComponentScan(basePackages = "dynamicProxy.instanceJDBC7")*/ //测试AOP
//@ComponentScan(basePackages = "transaction.instance1")
//@EnableAspectJAutoProxy /*启用@Aspect类*/
//@EnableTransactionManagement/*启用事务*/
//@ComponentScan(basePackages = "com.wgc.instance.dao")
@EnableCaching //启动缓存机制
@PropertySource("classpath:jdbc.properties")
public class SpringDAOConfig implements EnvironmentAware {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.user}")
    private String user;

    @Value("${jdbc.password}")
    private String password;

    @Autowired
    private Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    /*数据库操作*/
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }

    /*连接池的配置*/
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxWaitMillis(1500);
        config.setTestWhileIdle(true);
        config.setBlockWhenExhausted(false);
        return config;
    }


    /*Redis*/
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    /*redis模板*/
    @Bean
    public RedisTemplate redisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }

    /**/
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig().computePrefixWith(cacheName -> cacheName);
        RedisCacheManager manager = RedisCacheManager.builder(redisConnectionFactory()).cacheDefaults(configuration).build();
        return manager;
    }


    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException, PropertyVetoException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setTypeAliasesPackage("com.wgc.instance.entity");
        factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        factoryBean.setDataSource(dataSource());
        return factoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.wgc.instance.dao");
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return configurer;
    }

    /*事务*/
    @Bean
    public PlatformTransactionManager platformTransactionManager() throws PropertyVetoException {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource());
        return manager;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }
}
