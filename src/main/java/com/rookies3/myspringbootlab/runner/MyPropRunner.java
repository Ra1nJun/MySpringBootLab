package com.rookies3.myspringbootlab.runner;

import com.rookies3.myspringbootlab.config.MyEnvironment;
import com.rookies3.myspringbootlab.properties.MyPropProperties;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MyPropRunner implements ApplicationRunner {
    @Value("${myprop.username}")
    private String username;

    @Value("${myprop.port}")
    private int port;

    @Autowired
    private MyPropProperties properties;

    @Autowired
    private MyEnvironment myEnvironment;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("@Value 어노테이션을 사용 username = "+username);
        log.debug("@Value 어노테이션을 사용 port = "+port);

        log.info("getter 메서드로 출력 username = "+properties.getUserName());
        log.debug("getter 메서드로 출력 port = "+properties.getPort());

        log.info("myEnironment mode = "+myEnvironment);
    }

}
