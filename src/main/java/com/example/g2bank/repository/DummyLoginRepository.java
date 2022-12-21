package com.example.g2bank.repository;


import com.example.g2bank.model.User;

public class DummyLoginRepository {
    private static User loggedInUser;
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public static void setLoggedInUser(User loggedInUser) {
        DummyLoginRepository.loggedInUser = loggedInUser;
    }
}
