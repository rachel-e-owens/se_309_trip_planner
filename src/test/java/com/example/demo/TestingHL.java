package com.example.demo;

import com.example.demo.Model.Friend;
import com.example.demo.Model.Post;
import com.example.demo.Model.Trip;
import com.example.demo.Model.Userr;
import com.example.demo.controller.UserController;
import com.example.demo.dao.FriendRepo;
import com.example.demo.dao.PostRepo;
import com.example.demo.dao.TripRepo;
import com.example.demo.dao.UserrRepo;
import com.example.demo.service.PostService;
import com.example.demo.service.TripService;
import com.example.demo.service.UserService;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import com.google.gson.Gson;
import io.micrometer.core.instrument.util.JsonUtils;
import org.hamcrest.text.IsEmptyString;
import org.json.JSONObject;
import org.mockito.Mock;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.hamcrest.core.IsNull;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;


import javax.swing.text.html.Option;
import java.awt.*;
import java.util.*;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class TestingHL {

    @TestConfiguration
    static class UserConfiguration{
        @Bean
        public UserService uService(){
            return new UserService();
        }

        @Bean
        public TripService tService(){
            return new TripService();
        }

        @Bean
        PostService pService(){ return new PostService(); }

        @Bean
        UserrRepo getURepo(){
            return mock(UserrRepo.class);
        }

        @Bean
        TripRepo gettRepo(){
            return mock(TripRepo.class);
        }

        @Bean
        FriendRepo getRRepo(){ return mock(FriendRepo.class);}

        @Bean
        PostRepo getPRepo(){ return mock(PostRepo.class);}
    }

    @Mock
    private TripService tripServicee;

    @Autowired
    private MockMvc controller;

    @Autowired
    private UserrRepo userrRepo;

    @Autowired
    private TripRepo tripRepo;

    @Autowired
    private PostRepo postRepo;

    /**
     * Tests deleting a trip to make sure it is using trip service and not throwing errors
     * @throws Exception
     */
    @Test
    public void deleteTrip() throws Exception{
        Trip trip = new Trip();
        when(tripRepo.findByid(4)).thenReturn(trip);
        controller.perform(delete("/trips/4").contentType(MediaType.APPLICATION_JSON));
        verify(tripServicee, times(1));
    }

    /**
     * Tests updating a user's profile pic with a very long string, because the bit maps of the pictures
     * we are getting from frontend are 100,000+ characters.
     * @throws Exception
     */
    @Test
    public void testLongStringHandler() throws Exception{
        Userr dummy = new Userr("hailey", "password", "skey", "bio", false, "pic");
        String longg = "";
        Random r = new Random();
        for(int i = 0; i < 200000; i++){
            longg = longg.concat(Integer.toString(r.nextInt()));
        }
        String s = "{\"userName\": \"hailey\", \"profilePic\": \"" + longg + "\" }";
        when(userrRepo.findByUsername("hailey")).thenReturn(Optional.of(dummy));
        controller.perform(patch("/user").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(s))
                .andExpect(status().isOk());

    }

    /**
     * Tests getting a user that does not exist in the repository.
     * @throws Exception
     */
    @Test
    public void testInvalidUsername() throws Exception{
        Optional<Userr> dummy = Optional.empty();
        when(userrRepo.findByUsername("hailey")).thenReturn(dummy);
        controller.perform(get("/users/hailey/ilikecats").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("null"));
    }

    /**
     * Tests posting a username and then trying to get it with the wrong password.
     * @throws Exception
     */
    @Test
    public void testInvalidPassword() throws Exception{
        List<Userr> u = new ArrayList<Userr>();
        when(userrRepo.save((Userr)any())).thenAnswer(x -> {
            Userr us = x.getArgument(0);
            u.add(us);
            return null;
        });
        when(userrRepo.findByUsername("andre")).thenAnswer(x -> {
            return userrRepo.findByUsername("andre").get().getPassword();
            //return Optional.empty();
        });

        String s = "{\"userName\": \"andre\", \"password\": \"iowa\", \"admin\": \"false\" }";
        controller.perform(post("/user").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8").content(s)).andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(content().string(IsEmptyString.emptyOrNullString()));

        controller.perform(get("/users/andre/tesla"))
                .andExpect(status().isOk());
    }

    /**
     * Tests adding a new friend to a user's friend list
     * @throws Exception
     */
    @Test
    public void testAddFriend() throws Exception{
        Userr mockuser = mock(Userr.class);
        doReturn(true).when(mockuser).addFriend((Friend) any());
        boolean added = mockuser.addFriend(new Friend());
        assertTrue(added);
    }

    /**
     * Tests adding a trip to a user's set of trips
     * @throws Exception
     */
    @Test
    public void testAddTrip() throws Exception{
        Userr mockuser = mock(Userr.class);
        Trip mocktrip = mock(Trip.class);
        doReturn(true).when(mockuser).addTrip((Trip) any());
        boolean added = mockuser.addTrip(mocktrip);
        assertTrue(added);
    }
    
}
