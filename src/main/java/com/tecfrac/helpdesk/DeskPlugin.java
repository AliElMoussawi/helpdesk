package com.tecfrac.helpdesk;

import java.io.File;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.tecfrac.helpdesk.openfire.component.DeskComponent;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.jivesoftware.openfire.component.InternalComponentManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.xmpp.component.ComponentException;

@SpringBootApplication
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DeskPlugin implements Plugin {

    public static final String SUB_DOMAIN = "helpDesk";

    @Autowired
    DeskComponent deskComponent;

    private static final Logger log = LoggerFactory.getLogger(DeskPlugin.class);
    ConfigurableApplicationContext context;

    @Override
    public void initializePlugin(PluginManager manager, File pluginDirectory) {
        context = SpringApplication.run(DeskPlugin.class, new String[]{});
    }

    @PostConstruct
    private void setInterceptor() {
        try {
            InternalComponentManager.getInstance().addComponent(DeskPlugin.SUB_DOMAIN, deskComponent);

        } catch (ComponentException e) {
            e.printStackTrace(System.out);
        }
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }

    @Override
    public void destroyPlugin() {
        System.out.println("com.tecfrac.helpdesk.DeskPlugin.destroyPlugin() " + deskComponent);
//        InternalComponentManager.getInstance().removeComponent(SUB_DOMAIN);
        SpringApplication.exit(context);
        ((ConfigurableApplicationContext) context).close();
        log.info("destroy plugin");
    }
}
