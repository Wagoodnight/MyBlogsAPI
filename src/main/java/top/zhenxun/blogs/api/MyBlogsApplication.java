package top.zhenxun.blogs.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fusesource.jansi.AnsiConsole;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import top.zhenxun.blogs.api.config.SystemSettingInitializer;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@EnableAsync
@SpringBootApplication
@MapperScan("top.zhenxun.blogs.api.mapper")
public class MyBlogsApplication {

    private static final Logger logger = LogManager.getLogger(MyBlogsApplication.class);

    public static void main(String[] args) {
        AnsiConsole.systemInstall();

        SpringApplication application = new SpringApplication(MyBlogsApplication.class);
        application.addInitializers(new SystemSettingInitializer());
        application.run(args);
    }
}
