package com.example.demo.Model;

import java.util.List;

public class FriendTable {
	
	List<LimitedUser> user;
	public FriendTable(List<LimitedUser> user){
		this.user = user;
	}
	public List<LimitedUser> getFriend() {
		return user;
	}
	public void setFriend(List<LimitedUser> user) {
		this.user = user;
	}
}
