package com.example.crm;

import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import com.vaadin.flow.component.page.AppShellConfigurator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* 
 * The @PWA annotation tells Vaadin to create a ServiceWorker and a manifest file.
 * name is the full name of the application for the manifest file.
 * shortName should be short enough to fit under an icon when installed, and shouldn’t exceed 12 characters.
 */
@PWA(name = "Flow CRM", shortName = "CRM", offlinePath = "ofline.html", offlineResources = { "./images/offline.webp" })
@Theme(value = "crm")
@SpringBootApplication
public class CrmApplication implements AppShellConfigurator {
	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}

}