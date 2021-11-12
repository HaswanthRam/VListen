package POJO;

import java.util.ArrayList;

public class cheerPosts {

    String userName;

    ArrayList<String> userIds = new ArrayList<>();
    int cheers;
    String cheerPostId;
    String text;
    String userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(ArrayList<String> userIds) {
        this.userIds = userIds;
    }

    public int getCheers() {
        return cheers;
    }

    public void setCheers(int cheers) {
        this.cheers = cheers;
    }

    public String getCheerPostId() {
        return cheerPostId;
    }

    public void setCheerPostId(String cheerPostId) {
        this.cheerPostId = cheerPostId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
