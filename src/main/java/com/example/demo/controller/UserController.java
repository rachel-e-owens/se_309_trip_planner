package com.example.demo.controller;

import com.example.demo.Project1Application;
import com.example.demo.service.TripService;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Controller for User
 * @author david/hailey
 */
@RestController
public class UserController {

    /**
     * Service for user.
     */
	@Autowired
	UserService userService;

    /**
     * Service for trips.
     */
	@Autowired
	TripService tripService;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Simple hello world test.
     * @return "hello"
     */
	@GetMapping("/home/test")
	public String helloWorld() {
		return "goodbye";
	}

    /**
     * Returns all the users in the repository.
     * @return Iterable<Userr> of all users in repository.
     */
	@GetMapping("/users/all") //tested
	public Iterable<Userr> getAllUsers() {
		return userService.getAllUser();
	}

    /**
     * Adds a new user to the repository.
     * @param user, user to be created in the repository
     */
	@PostMapping("/user") //tested
	public void addUser(@RequestBody Userr user) {
		userService.addUser(user);
	}

    /**
     * Gets a particular user.
	 * IMPORTANT: Will not return the trip table or friends list.
     * @param username, username of the user to be returned
     * @param password, password of the user to be returned
     * @return a User if a user with the specified username and password exists in the repository,
     * returns an empty otherwise.
     */
	@GetMapping("/users/{username}/{password}") //tested
	public Optional<Userr> getUser(@PathVariable("username") String username, @PathVariable("password") String password) {
		return userService.findByUsername(username, password);
	}

    /**
     * Updates a particular user.
     * @param user, user object to be updated
     * Note: request body must have userName and then any other parameters to change.
     */
	@PatchMapping("/user") //tested
	public void update(@RequestBody Userr user) {
		userService.updateUser(user);
	}

    /**
     * Deletes a particular user from the repository.
     * @param username, username of the user to be deleted.
     */
	@DeleteMapping("/user/{username}") //tested
	public void delete(@PathVariable("username") String username) {
		userService.deleteUser(username);
	}

    /**
     * Gets the admin status of the user with the given username.
     * @param username, username of the user to get admin status
     * @return true if the user with given username is an admin, false otherwise
     */
	@GetMapping("/user/{username}/admin") //tested
	public boolean admin(@PathVariable("username") String username) {
		return userService.isAdmin(username);
	}

    /**
     * Makes the user with the given username an admin.
     * @param username, username of the user to make an admin
     */
	@PostMapping("user/{username}/admin/y") //tested
	public void makeAdmin(@PathVariable("username") String username) {
		userService.setAdmin(username);
	}

    /**
     * Removes admin permissions from the user with the given username.
     * @param username, username of the user to remove admin permissions from
     */
	@PostMapping("/user/{username}/admin/n") //tested
	public void removeAdmin(@PathVariable("username") String username) {
		userService.removeAdmin(username);
	}

    /**
     * Gets the list of friends that the user with the given username has.
     * @param username, username of user to get friends for
     * @return ArrayList of users that represents the given user's friends
     */
	@GetMapping("/user/{username}/friends")
	public FriendTable getFriends(@PathVariable("username") String username) {
		return userService.getFriends(username);
	}

    /**
     * Adds a friend to the list of friends associated with the user with the given username.
     * @param username, username of user to add a friend to
     * @param friend, username of friend to be added to user
     */
	@PostMapping("/user/{username}/addFriend") //null pointer exception if don't create friends
	public boolean addFriend(@PathVariable("username") String username, @RequestBody Friend friend) {
		return userService.addFriend(username, friend);
	}

    /**
     * Deletes a friend from the list of friends associated with the user with the given username.
     * @param username, username of the user to remove a friend from
     * @param friend, username of the friend to be removed from the user's friend list
     */
	@DeleteMapping("/username/{username}/{friend}")
	public void deleteFriend(@PathVariable("username") String username, @PathVariable("friend") Friend friend) {
		userService.removeFriend(username, friend);
	}

    /**
     * Adds a trip to the user with the given username
     * Note: this creates trip and automatically associates the user with it.
     * The user who creates this trip becomes the trip leader.
     * @param username, username of the user to add the trip to
     * @param trip, trip to be added
     */
	@PostMapping("/user/{username}/trip") //tested
	public void addTrip(@PathVariable("username") String username, @RequestBody Trip trip) {
		tripService.createTrip(username, trip);
	}

    /**
     * Get the set of trips associated with the user with the given username.
     * @param username, username of the user wanting trips for
     * @param password, password of the user wanting trips for
     * @return Set of trips that are associated with the user with the given username
     */
	@GetMapping("/user/{username}/{password}/trips") //tested
	public Set<Trip> getTrips(@PathVariable("username") String username, @PathVariable("password") String password) {
		return userService.findByUsername(username, password).get().getTripTable();
	}

    /**
     * Sets or changes the bio of the user with the given username.
     * @param username, username of the user to change bio for
     * @param bio, string that the bio should be set or changed to
     */
	@PostMapping("/user/{username}/bio") //tested
	public void setBio(@PathVariable("username") String username, @RequestBody String bio) {
		userService.setBio(username, bio);
	}

    /**
     * Gets the bio of the user with the given username.
     * @param username, username of the user to get the bio for
     * @return String that is the bio of the user with the given username
     */
	@GetMapping("user/{username}/bio") //tested
	public String getBio(@PathVariable("username") String username) {
		return userService.getBio(username);
	}

    /**
     * Sets or changes the session key for the user with the given username.
     * @param username, username of the user to change the session key for
     * @param password, password of the user to change the session key for
     * @param skey, session key value to be set or changed to
     */
	@PostMapping("user/{username}/{password}/skey") //tested
	public void setSkey(@PathVariable("username") String username, @PathVariable String password, @RequestBody String skey) {
		userService.setSkey(username, skey);
	}

    /**
     * Gets the session key for the user with the given username
     * @param username, username of user to get session key for
     * @return String that is the session key
     */
	@GetMapping("user/{username}/skey") //tested
	public String getSkey(@PathVariable("username") String username) {
		return userService.getSkey(username);
	}

	/**
	 * Uploads an imagine with the given username 
	 * @param username, username of the account the image should be added to
	 * @param profilePic, a String that can be converted back to an image
	 */
	@PostMapping("user/{username}/uploadImage")
	public void uploadImage(@PathVariable("username") String username, @RequestBody String profilePic) {
		userService.addProfilePic(username,profilePic);
	}

	/**
	 * Updates an imagine with the given username 
	 * @param username, username of the account the image should be updated to
	 * @param profilePic, a String that can be converted back to an image
	 */
	@PatchMapping("/user/{username}/updateImage")
	public void updatePicture(@PathVariable("username") String username, @RequestBody String profilePic) {
		userService.updateProfilePicture(username, profilePic);
	}

	/**
	 * Deletes an imagine with the given username 
	 * @param username, username of the account the image should be deleted for
	 */
	@DeleteMapping("user/{username}/deleteImage")
	public void deletePicture(@PathVariable("username") String username){
		userService.deleteProfilePicture(username); 
		}

	/**
	 * Gets an imagine with the given username 
	 * @param username, username of the account the image the user wants
	 */
	@GetMapping("user/{username}/getProfilePic")
	public String getProfilePic(@PathVariable("username") String username){
		return userService.getProfilePic(username);
	}

} 