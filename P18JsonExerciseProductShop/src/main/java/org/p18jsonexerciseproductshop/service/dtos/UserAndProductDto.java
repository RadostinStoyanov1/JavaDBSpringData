package org.p18jsonexerciseproductshop.service.dtos;

import java.io.Serializable;
import java.util.List;

public class UserAndProductDto implements Serializable {
    private int userCount;
    private List<UserSoldDto> users;

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public List<UserSoldDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserSoldDto> users) {
        this.users = users;
    }
}
