package com.example.bfhealth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.bfhealth.service.WebhookService;

@SpringBootApplication
public class BfhealthApplication implements CommandLineRunner {

    @Autowired
    private WebhookService webhookService;

    public static void main(String[] args) {
        SpringApplication.run(BfhealthApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        webhookService.executeTask();
    }
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
