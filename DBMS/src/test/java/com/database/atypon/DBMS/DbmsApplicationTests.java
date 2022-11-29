package com.database.atypon.DBMS;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DbmsApplicationTests {

	@Test
	void contextLoads() throws JSONException {

		String test = "{ \"name\": \"String\"}";
		JSONObject obj = new JSONObject(test);
		System.out.println(obj.get("name"));
		System.out.println(obj.toString());

	}

}
