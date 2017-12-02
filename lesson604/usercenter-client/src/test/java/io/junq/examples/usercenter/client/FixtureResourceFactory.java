package io.junq.examples.usercenter.client;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import com.google.common.collect.Sets;

import io.junq.examples.usercenter.persistence.model.Privilege;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.persistence.model.User;

public class FixtureResourceFactory {
	
    private FixtureResourceFactory() {
        throw new AssertionError();
    }

    // user

    /*public static User createNewUser() {
        return createNewUser(randomAlphabetic(8), randomAlphabetic(8));
    }
    
    public static User createNewUser(final String name) {
        return createNewUser(name, randomAlphabetic(8));
    }
    
    public static User createNewUser(final String name, final String pass) {
        final User user = new User(name, pass, Sets.<Role> newHashSet());
        user.setEmail(randomAlphabetic(6) + "@gmail.com");
        return user;
    }
    */
    // User

    public static User createNewUser() {
        return createNewUser(randomAlphabetic(8), randomAlphabetic(8));
    }

    public static User createNewUser(final String name, final String pass) {
        return new User(name, pass, Sets.<Role> newHashSet());
    }

    // role

    public static Role createNewRole() {
        return createNewRole(randomAlphabetic(8));
    }

    public static Role createNewRole(final String name) {
        return new Role(name, Sets.<Privilege> newHashSet());
    }

    // privilege

    public static Privilege createNewPrivilege() {
        return createNewPrivilege(randomAlphabetic(8));
    }

    public static Privilege createNewPrivilege(final String name) {
        return new Privilege(name);
    }

}
