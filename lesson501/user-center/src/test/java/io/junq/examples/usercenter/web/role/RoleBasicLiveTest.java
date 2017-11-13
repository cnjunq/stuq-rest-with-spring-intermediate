package io.junq.examples.usercenter.web.role;

import static io.junq.examples.common.spring.util.Profiles.CLIENT;
import static io.junq.examples.common.spring.util.Profiles.TEST;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.Sets;

import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.spring.RestTestConfig;
import io.junq.examples.usercenter.spring.UserCenterClientConfig;
import io.junq.examples.usercenter.spring.UserCenterLiveTestConfiguration;
import io.junq.examples.usercenter.util.UserCenter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@ActiveProfiles({ CLIENT, TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserCenterLiveTestConfiguration.class, UserCenterClientConfig.class, RestTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleBasicLiveTest {

    private final static String URI = "http://localhost:8082/api/roles";

    // tests

    @Test
    public void whenAllRolesAreRetrieved_then200OK() {
        final RequestSpecification basicAuth = RestAssured.given().auth().preemptive().basic(UserCenter.ADMIN_EMAIL, UserCenter.ADMIN_PASS);
        final Response response = basicAuth.accept(ContentType.JSON).get(URI);

        Assert.assertThat(response.getStatusCode(), Matchers.equalTo(200));
    }

//    @Test
//    public void whenAllRolesAreRetrieved_thenAtLeastOneRoleExists() {
//        final Response response = RestAssured.given().auth().preemptive().basic(UserCenter.ADMIN_EMAIL, UserCenter.ADMIN_PASS).accept(ContentType.JSON).get(URI);
//        final List<Role> roles = response.as(List.class);
//
//        Assert.assertThat(roles, not(Matchers.<Role> empty()));
//    }
//
//    @Test
//    public void whenCreatingANewRole_thenRoleCanBeRetrieved() {
//        final Role newRole = new Role(randomAlphabetic(6), Sets.newHashSet());
//        final RequestSpecification writeAuth = RestAssured.given().auth().preemptive().basic(UserCenter.ADMIN_EMAIL, UserCenter.ADMIN_PASS);
//        final Response createResponse = writeAuth.contentType(ContentType.JSON).body(newRole).post(URI);
//
//        final String locationHeader = createResponse.getHeader("Location");
//        final RequestSpecification readAuth = RestAssured.given().auth().preemptive().basic(UserCenter.ADMIN_EMAIL, UserCenter.ADMIN_PASS);
//        final Role retrievedRole = readAuth.accept(ContentType.JSON).get(locationHeader).as(Role.class);
//
//        assertThat(newRole, equalTo(retrievedRole));
//    }
//
//    // == working unlike expected in video
//    @Test
//    public void whenCreatingANewRole_thenRoleCanBeRetrieved_2() {
//        final Role newRole = new Role(randomAlphabetic(6), Sets.newHashSet());
//        final RequestSpecification auth = RestAssured.given().auth().preemptive().basic(UserCenter.ADMIN_EMAIL, UserCenter.ADMIN_PASS);
//        final Response createResponse = auth.contentType(ContentType.JSON).body(newRole).post(URI);
//
//        final String locationHeader = createResponse.getHeader("Location");
//        final Role retrievedRole = auth.accept(ContentType.JSON).get(locationHeader).as(Role.class);
//
//        assertThat(newRole, equalTo(retrievedRole));
//    }
	
}
