package com.interview.questions;
class InstagramAccount {
    
    String instagram_id; 
    String username; 
    String biography;
    int follower_count; 
    List<String> followers;
    
    public InstagramAccount (String instagram_id, String username, String biography, 
                             int follower_count, List<String> followers) {
        
        
        this.instagram_id = instagram_id; 
        this.username = username; 
        this.biography = biography;
        this.follower_count = follower_count; 
        this.followers = followers;
    }
    
    
    
}