package com.johann.springboottk;

import com.johann.springboottk.aotoconfig.UserScan;
import com.johann.springboottk.aotoconfig.dto.UserA;
import com.johann.springboottk.aotoconfig.dto.UserB;
import com.johann.springboottk.aotoconfig.UserImportSelector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import({UserImportSelector.class,UserB.class})
@UserScan("com.johann.springboottk.aotoconfig.dto")
public class SpringboottkApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringboottkApplication.class, args);

        /**
         * https://blog.csdn.net/u011320740/article/details/105094866
         */
        Object userA = run.getBeanFactory().getBean(UserA.class);
        System.out.println(userA.toString());
        Object userB = run.getBeanFactory().getBean(UserB.class);
        System.out.println(userB.toString());
    }

}
