package cn.yuyizyk;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class GroundApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(GroundApplication.class, args);
	}
}
