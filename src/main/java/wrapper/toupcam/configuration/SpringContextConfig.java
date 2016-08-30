package wrapper.toupcam.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "wrapper.toupcam", "wrapper.toupcam.strucutres" })
public class SpringContextConfig {

}
