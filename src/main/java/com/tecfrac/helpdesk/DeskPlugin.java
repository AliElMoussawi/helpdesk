package com.tecfrac.helpdesk;

import java.io.File;
import org.jivesoftware.openfire.component.InternalComponentManager;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.tecfrac.helpdesk.openfire.component.DeskComponent;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DeskPlugin implements Plugin {

    public static final String SUB_DOMAIN = "helpDesk";

    @Autowired
    DeskComponent deskComponent;

    private static final Logger log = LoggerFactory.getLogger(DeskPlugin.class);

    @Override
    public void initializePlugin(PluginManager manager, File pluginDirectory) {
        SpringApplication.run(DeskPlugin.class, new String[]{});
    }

    @PostConstruct
    private void setInterceptor() {
        try {
            InternalComponentManager.getInstance().addComponent(DeskPlugin.SUB_DOMAIN, deskComponent);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void destroyPlugin() {
        if (deskComponent != null) {
            InternalComponentManager.getInstance().removeComponent(SUB_DOMAIN);
        }
        log.info("destroy plugin");
    }
}
