package com.msgrserver.model.entity.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getId() {
        User user1 = User.builder().id(1L).name("Ali").build();
        User user2 = User.builder().id(1L).name("Ali").build();
        assertEquals(user1, user2);
    }
}