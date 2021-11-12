package POJO;

import java.util.ArrayList;

public class Posts {
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }



    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }




    public ArrayList<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(ArrayList<String> userIds) {
        this.userIds = userIds;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    String userName;

    ArrayList<String> userIds = new ArrayList<>();
    int likes;
    int groupId;
    String postId;
    String text;
    String user;


}
