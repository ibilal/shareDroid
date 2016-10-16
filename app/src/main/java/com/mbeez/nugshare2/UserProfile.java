package com.mbeez.nugshare2;

import java.util.Arrays;
import java.util.Date;


public class UserProfile {
    private String email;
    private String name;
    private String nickName;
    private int age;
    private Date dob;
    private String picArray[];
    private String profilePicture;
    private String id;
    private String idToken;

    public UserProfile(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile)) return false;

        UserProfile that = (UserProfile) o;

        if (age != that.age) return false;
        if (!email.equals(that.email)) return false;
        if (!name.equals(that.name)) return false;
        if (!nickName.equals(that.nickName)) return false;
        if (!dob.equals(that.dob)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(picArray, that.picArray)) return false;
        return profilePicture != null ? profilePicture.equals(that.profilePicture) : that.profilePicture == null;

    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + nickName.hashCode();
        result = 31 * result + age;
        result = 31 * result + dob.hashCode();
        result = 31 * result + Arrays.hashCode(picArray);
        result = 31 * result + (profilePicture != null ? profilePicture.hashCode() : 0);
        return result;
    }
}
