package com.example.toolinventorysystem.utils;

import com.example.toolinventorysystem.exception.ElementNotFound;
import com.example.toolinventorysystem.models.User;

//static - The static variable gets memory only once in the class area at the time of class loading.
//its in utils - A Utils class is a general purposed utility class using which we can reuse the existing block of code without creating instance of the class.
public class CurrentUser {
    private static final ThreadLocal<User> threadUser = new ThreadLocal<>();

    public static void set(User user) {
        threadUser.set(user);
    }

    public static User get() {
        if (threadUser.get() == null) {
            throw new ElementNotFound("user does not exist currently");
        } return threadUser.get();
    }
}
