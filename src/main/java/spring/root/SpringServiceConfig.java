package spring.root;

        import org.springframework.context.annotation.ComponentScan;
        import org.springframework.context.annotation.Configuration;

@Configuration  //配置文件
@ComponentScan("com.wgc.instance.redis")   //扫描service层
public class SpringServiceConfig {

}
