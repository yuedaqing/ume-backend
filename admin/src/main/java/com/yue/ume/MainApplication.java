package com.yue.ume;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 项目入口
 *
 * @author yueyue
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableScheduling
@Slf4j
public class MainApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(MainApplication.class, args);
        TomcatServletWebServerFactory tomcatServlet = application.getBean(TomcatServletWebServerFactory.class);
        String ip = InetAddress.getLocalHost().getHostAddress();
        int port = tomcatServlet.getPort();
        String path = tomcatServlet.getContextPath();
        log.info("\n----------------------------------------------------------\n\t" +
                "swagger-ui: \thttp://" + ip + ":" + port + path + "/doc.html\n\t" +
                "----------------------------------------------------------");
        log.info("+++++++++++++++++++项目启动成功+++++++++++++++++++++++");
    }

}