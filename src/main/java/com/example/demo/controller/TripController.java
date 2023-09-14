package com.example.demo.controller;

import com.example.demo.Model.Post;
import com.example.demo.Model.Trip;
import com.example.demo.Model.Userr;
import com.example.demo.Project1Application;
import com.example.demo.service.TripService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Set;

/**
 * Controller for Trip
 * @author hailey
 */
@RestController
public class TripController {
    /**
     * Service for User
     */
    @Autowired
    UserService userService;

    /**
     * Service for Trip
     */
    @Autowired
    TripService tripService;

    /**
     * Creates a new trip to be added to the repository and associated with the given user.
     * Cannot create a trip without associating a user.
     * The user than creates this trip will automatically become the trip leader.
     * @param username, username of the user creating this trip
     * @param trip, trip that is being created
     */
    @PostMapping("/{username}/trip") //tested
    public void createTrip(@PathVariable("username") String username, @RequestBody Trip trip){
        tripService.createTrip(username, trip);
    }

    /**
     * Gets a particular trip.
     * @param tripid, trip ID of the trip to get
     * @return A Trip object with the given trip ID
     */
    @GetMapping("/trips/{tripid}") //tested
    public Trip getTrip(@PathVariable("tripid") int tripid){
        return tripService.findByTripID(tripid);
    }

    /**
     * Updates the trip with the given ID to have the given information
     * Note: must have trip ID in the body
     * @param trip, new information for this trip
     */
    @PatchMapping("/trip") //tested
    public void updateTrip(@RequestBody Trip trip){tripService.updateTrip(trip);}

    /**
     * Deletes a particular trip from the repository
     * @param tripid, trip ID of the trip to be deleted
     */
    @DeleteMapping("/trips/{tripid}") //does not work
    public void deleteTrip(@PathVariable("tripid") int tripid){
        tripService.deleteTrip(tripid);
    }

//    /**
//     * Disassociates the trip with the given trip ID from the given user
//     * @param username, username of the user that wants to be removed from the trip
//     * @param tripid, trip ID of the trip the user wants to disassociate from
//     * @return 1 if successful, -1 otherwise
//     */
//    @DeleteMapping("/users/{username}/{tripid}")
//    public int disassociateTrip(@PathVariable("username") String username, @PathVariable("tripid") int tripid){
//        return tripService.disassociateTrip(username, tripid);
//    }

    /**
     * Adds a member to the trip with the given trip ID
     * @param tripid, trip ID of the trip the user wants to be added to
     * @param username, username of the user that wants to be added
     */
    @PostMapping("/trips/{tripid}/{username}") //tested
    public void addMember(@PathVariable("tripid") int tripid, @PathVariable("username") String username){
        tripService.addMember(username, tripid);
    }

    /**
     * Deletes a member from a particular trip.
     * @param tripid, trip ID of the trip the user should be removed from
     * @param username, username of the user to be removed
     */
    @DeleteMapping("/trips/{tripid}/{username}") //tested
    public void deleteMember(@PathVariable("tripid") int tripid, @PathVariable("username") String username){
        tripService.removeMember(username, tripid);
    }

    /**
     * Gets the members for a particular trip
     * @param tripid, ID of the trip to get members for
     * @return a Set of Userrs that are members of the trip
     */
    @GetMapping("/trips/{tripid}/members") //tested
    public Set<Userr> getMembers(@PathVariable("tripid") int tripid) {
        return tripService.getMembers(tripid);
    }

    /**
     * Adds longitude to a given trip
     * @param tripid, trip ID of the trip the user will add the longitude to
     * @param longitude, a longitude value on a map
     */
    @PostMapping("/trips/{tripid}/addLongitude") //tested
    public void addLongitude(@PathVariable("tripid") int tripid, @RequestBody double longitude) {
    	tripService.addLongitude(longitude, tripid);
    }
    
    /**
     * Updates a particular longitude of a trip location
     * @param tripid, trip id of the trip
     */
    @PatchMapping("/trips/{tripid}/updateLongitude") //tested
    public void updateLongitude(@PathVariable("tripid") int tripid, @RequestBody double longitude){
        tripService.updateLongitude(tripid, longitude);
    }
    
    /**
     * Deletes longitude of a trip replaces it with 0
     * @param tripid
     */
    @DeleteMapping("/trips/{tripid}/deleteLongitude") //tested
    public void deleteLongitude(@PathVariable("tripid") int tripid){
        tripService.updateLongitude(tripid,0);
    }

    
    /**
     * Adds latitude to a given trip
     * @param tripid, trip ID of the trip the user will add the latitude to
     * @param latitude, a latitude value on a map
     */
    @PostMapping("/trips/{tripid}/addLatitude") //tested
    public void addLatitude(@PathVariable("tripid") int tripid, @RequestBody double latitude) {
    	tripService.addLatitude(latitude, tripid);
    }
    
    /**
     * Updates a particular location for a trip
     * @param tripid, trip id of the trip
     */
    @PatchMapping("/trips/{tripid}/updateLatitude") //tested
    public void updateLatitude(@PathVariable("tripid") int tripid, @RequestBody double latitude){
        tripService.updateLatitude(tripid, latitude);
    }

    /**
     * Deletes a particular location for a trip.
     * @param tripid, trip id of the trip you want to delete the location of
     */
    @DeleteMapping("/trips/{tripid}/deleteLatitude") //tested
    public void deleteLatitude(@PathVariable("tripid") int tripid){
        tripService.updateLatitude(tripid,0);
    }

    /**
     * Adds location name to the given trip
     * @param tripid, trip ID of the trip the user will add a location name to
     * @param location, name the location should be named
     */
    @PostMapping("/trips/{tripid}/addLocation") //tested
    public void addLocationName(@PathVariable("tripid") int tripid, @RequestBody String location) {
    	tripService.addLocation(location, tripid);
    }
    
    /**
     * Updates a particular location for a trip
     * @param tripid, trip id of the trip
     * @param location, name of the location
     */
    @PatchMapping("/trips/{tripid}/updateLocation") //tested
    public void updateLocation(@PathVariable("tripid") int tripid, @RequestBody String location){
        tripService.updateLocation(tripid, location);
    }

    /**
     * Deletes a particular location for a trip.
     * @param tripid, trip id of the trip you want to delete the location of
     */
    @DeleteMapping("/trips/{tripid}/deleteLocation") //tested
    public void deleteLocation(@PathVariable("tripid") int tripid){
        tripService.updateLocation(tripid,"");
    }

    /**
     * Gets all the posts that have been posted with this trip
     * @param tripid, ID of the trip to get posts for
     * @return all the posts associated with this trip
     */
    @GetMapping("trips/{tripid}/posts") //tested
    public Iterable<Post> getPostsforTrip(@PathVariable("tripid") int tripid){
        return tripService.getAllPosts(tripid);
    }

    /**
     * Creates a post
     * @param username, username of the author of this post
     * @param tripid, tripid of the trip to add this post to
     * @param post, the information for the post to be created
     */
    @PostMapping("{username}/trips/{tripid}/post") //tested
    public void createPost(@PathVariable("username") String username, @PathVariable("tripid") int tripid, @RequestBody Post post){
        tripService.createPost(username, tripid, post);
    }

    /**
     * Gets a particular post
     * @param postid, ID of the post to get
     * @return the post with the given ID
     */
    @GetMapping("trips/posts/{postid}") //testedaly
    public Post getPost(@PathVariable("postid") int postid){
        return tripService.getPost(postid);
    }

    /**
     * Updates a particular post. Only the user who created the post can change it.
     * @param username, username of the user who is trying to update a post
     * @param post
     */
    @PatchMapping("{username}/posts") //tested
    public void updatePost(@PathVariable("username") String username, @RequestBody Post post){
        if(username.equals(getPost(post.getId()).getAuthor())){
            tripService.updatePost(post);
        }
    }

    /**
     * Deletes a particular post. Only the user who created the post and the trip leader can delete a post.
     * @param username, username of the user trying to delete the post
     * @param postid, ID of the post trying to delete
     */
    @DeleteMapping("{username}/trips/{postid}") //tested
    public void deletePost(@PathVariable("username") String username, @PathVariable("postid") int postid){
        if(username.equals(getPost(postid).getAuthor()) || username.equals(getPost(postid).getTrip().getTripLead())){
            tripService.deletePost(getPost(postid));
        }
    }
    
    /**
     * Adds a date to a trip
     * @param tripid
     * @param date
     */
    @PostMapping("/trips/{tripid}/addDate") //tested
    public void addDate(@PathVariable("tripid") int tripid, @RequestBody String date){
        tripService.addDate(date, tripid);
    }

    /**
     * Deletes a date from a particular trip.
     * @param tripid, trip ID of the trip the user should be removed from
     * @param date, date of the trip 
     */
    @DeleteMapping("/trips/{tripid}/{date}") //tested
    public void deleteDate(@PathVariable("tripid") int tripid, @PathVariable("date") String date){
        tripService.removeDate(date, tripid);
    }

    /**
     * Gets the date of the particular trip
     * @param tripid, ID of the trip to get members for
     * @return a string of the date 
     */
    @GetMapping("/trips/{tripid}/getDate") //tested
    public String getDate(@PathVariable("tripid") int tripid) {
        return tripService.getDate(tripid);
    }
    
    @PostMapping("/trips/{tripid}/addPic") //tested
    public void addPic(@PathVariable("tripid") int tripid, @RequestBody String picture){
        tripService.addPic(picture, tripid);
    }

    /**
     * Deletes a date from a particular trip.
     * @param tripid, trip ID of the trip the user should be removed from
     */
    @DeleteMapping("/trips/{tripid}/{pic}") //tested
    public void deletePic(@PathVariable("tripid") int tripid, @PathVariable("pic") String picture){
        tripService.removePic(picture, tripid);
    }

    /**
     * Gets the date of the particular trip
     * @param tripid, ID of the trip to get members for
     * @return a string of the date 
     */
    @GetMapping("/trips/{tripid}/getPic") //tested
    public String getPic(@PathVariable("tripid") int tripid) {
        return tripService.getPic(tripid);
    }
}
