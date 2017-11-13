package io.junq.examples.usercenter.web.role;

import static io.junq.examples.common.spring.util.Profiles.CLIENT;
import static io.junq.examples.common.spring.util.Profiles.TEST;
import static org.hamcrest.CoreMatchers.not;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@ActiveProfiles({ CLIENT, TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserCenterLiveTestConfiguration.class, UserCenterClientConfig.class, RestTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleBasicLiveTest {

	private final static String BASEURI = "http://localhost:8082";
	
    private final static String URI = BASEURI + "/api/roles";
    
    private static ContentType contentType = null;

    private static String accessToken = null;
    
    private static String refreshToken = null;
    
    // setup and teardown
    
    @BeforeClass 
    public static void setup() {
    	contentType = ContentType.JSON;
    	
    	// 生成access token
    	String response = RestAssured.given().params("username", UserCenter.ADMIN_EMAIL,
    								   "password", UserCenter.ADMIN_PASS,
    								   "grant_type", "password",
    								   "client_id", "uc"
    									)
    						.auth().preemptive()
    						.basic("uc", "VXB0YWtlLUlyb24h")
    						.accept(contentType)
    				.when()
    					.post(BASEURI + "/oauth/token")
    					.asString();
    	
        JsonPath jsonPath = new JsonPath(response);
        accessToken = jsonPath.getString("access_token");
        refreshToken = jsonPath.getString("refresh_token");
     
    }
    
    private static RequestSpecification oauthAuthSpec() {
    	return RestAssured.given().auth().preemptive().oauth2(accessToken);
    }
    
    @AfterClass 
    public static void tearndown() {
    	
    }
    
    // tests

    /**
     * 获取角色列表，期待返回200状态码
     */
    @Test
    public void whenAllRolesAreRetrieved_then200OK() {
    	
        final Response response = oauthAuthSpec().accept(contentType).get(URI);

        Assert.assertThat(response.getStatusCode(), Matchers.equalTo(200));
    }
    
    /**
     * 获取角色列表，期待列表中至少有一个角色对象
     */
    @Test
    public void whenAllRolesAreRetrieved_thenAtLeastOneRoleExists() {
        final Response response = oauthAuthSpec().accept(contentType).get(URI);
        final List<Role> roles = response.as(List.class);

        Assert.assertThat(roles, not(Matchers.<Role> empty()));
    }
    
    /**
     * 创建角色并获取该角色，期待创建成功返回201状态码、获取创建的角色数据成功
     */
    @Test
    public void whenCreatingANewRole_thenRoleCanBeRetrieved() {
        final Role newRole = new Role(randomAlphabetic(6), Sets.newHashSet());
        final Response createResponse = oauthAuthSpec().contentType(ContentType.JSON).body(newRole).post(URI);

        Assert.assertThat(createResponse.getStatusCode(), Matchers.equalTo(201));
        
        final String locationHeader = createResponse.getHeader("Location");
        final Response retrievedResponse = oauthAuthSpec().accept(ContentType.JSON).get(locationHeader);
        final Role retrievedRole = retrievedResponse.as(Role.class);

        assertThat(newRole, equalTo(retrievedRole));
    }
    
}
