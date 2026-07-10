package com.example.demo;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class SwaggerUiBrowserLauncher implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!Desktop.isDesktopSupported()) {
            return;
        }

        try {
            Desktop desktop = Desktop.getDesktop();
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                return;
            }
            String url = "http://localhost:8080/swagger-ui/index.html";
            desktop.browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            // ignore browse failure on unsupported platforms
        }
    }
}
