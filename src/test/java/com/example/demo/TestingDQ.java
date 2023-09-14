package com.example.demo;

import com.example.demo.Model.Friend;
import com.example.demo.Model.Trip;
import com.example.demo.Model.Userr;
import com.example.demo.controller.TripController;
import com.example.demo.controller.UserController;
import com.example.demo.dao.TripRepo;
import com.example.demo.dao.UserrRepo;
import com.example.demo.service.TripService;
import com.example.demo.service.UserService;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


import com.google.gson.Gson;
import io.micrometer.core.instrument.util.JsonUtils;
import org.hamcrest.text.IsEmptyString;
import org.json.JSONObject;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.hamcrest.core.IsNull;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.text.html.Option;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestingDQ {

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private UserService userservice;
	
	@InjectMocks
	private UserController userController;
	
	@Mock
	private TripService tripService;
	
	@InjectMocks
	private TripController tripController; 
	
	//Simple check to see if status code is right
	@Test
	public void getBioOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/hd/getBio"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	//Check if getter for bio service works
	@Test
	public void getBio() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/hd/bio"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("hello"));
	}
	
	//Check if getter for Keys Work
	@Test
	public void sKey() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/Nicole/skey"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("2343"));
	}
	
	//Testing if you can replace and object with another object if you have the same ID. Answer : Yes
	
	@Test
	public void complexTest() throws Exception{
		String s = "{\"description\":null,\"latitude\":43.0,\"longitude\":43.0,\"tripname\":null,\"members\":[],\"tripID\":9}";
		mockMvc.perform(MockMvcRequestBuilders.get("/trips/9"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().json("{\"description\":null,\"latitude\":43.0,\"longitude\":43.0,\"tripname\":null,\"members\":[],\"tripID\":9}"));
		mockMvc.perform(MockMvcRequestBuilders.post("/quan/trip").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(s))
				.andExpect(status().isOk());
		mockMvc.perform(MockMvcRequestBuilders.get("/trips/9"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().json("{\"description\":null,\"latitude\":43.0,\"longitude\":43.0,\"tripname\":null,\"members\":[],\"tripID\":9}"));
	}
	
	//Silly test to see if mockMvc is working properly
	@Test
	public void getHome() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/home/test"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("hello"));
	}
	
//	@Test
//	public void complexTest() throws Exception{
//		mockMvc.perform()
//	}
}
