package ru.yandex.practicum.catsgram;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatsgramApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(CatsgramApplication.class, args);



	/*		ObjectMapper om = new ObjectMapper();
			String str = "{\"param\"=\"value\"}";
			System.err.println("String original: " + str);
			String s1 = om.writeValueAsString(str);
			System.err.println("serialised value: " + s1);
			String s = om.readValue(str, String.class);
			System.err.println(s);

*/

	}

}