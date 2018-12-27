package spring.root;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "spring.root")
//@PropertySource(value = "classpath:jdbc.properties")
@Import({SpringDAOConfig.class, SpringServiceConfig.class})
public class RootConfig {

}
