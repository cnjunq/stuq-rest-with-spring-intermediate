package io.junq.examples.usercenter.client.template;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;

import io.junq.examples.common.spring.util.Profiles;
import io.junq.examples.usercenter.client.UserCenterPaths;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.util.UserCenter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Profile(Profiles.CLIENT)
@Component
public final class RoleSimpleApiClient {

    @Autowired
	protected UserCenterPaths paths;
    
	// API
    
    // 查找单条记录
    
    public final Role findOne(final long id) {
    	final Response response = findOneAsResponse(id);
    	Preconditions.checkState(response.getStatusCode() == 200, "Expecting status code is 200 OK, but actual response is not.");
    	return response.as(Role.class);
    }
    
    public final Response findOneAsResponse(final long id) {
    	return read(getUri() + "/" + id);
    }
    
    // 查找列表
    
    @SuppressWarnings("unchecked")
	public final List<Role> findAll() {
    	final Response response = findAllAsResponse();
    	Preconditions.checkState(response.getStatusCode() == 200, "Expecting status code is 200 OK, but actual response is not.");
    	return response.as(List.class);
    }
    
    public final Response findAllAsResponse() {
    	return read(getUri());
    }
    
    // 创建角色
    
    public final Role create(final Role role) {
    	final Response response = createAsResponse(role);
    	Preconditions.checkState(response.getStatusCode() == 201, "Expecting status code is 201 OK, but actual response is not.");
    	final String location = response.getHeader(HttpHeaders.LOCATION);
    	return read(location).as(Role.class);
    }
    
    public final Response createAsResponse(final Role role) {
    	return givenAuthenticated().contentType(ContentType.JSON).body(role).post(getUri());
    }
    
    // 更新角色
    
    public final Role update(final Role role) {
    	Response response = updateAsResponse(role);
    	Preconditions.checkState(response.getStatusCode() == 200, "Expecting status code is 200 OK, but actual response is not.");
    	return read(getUri() + "/" + role.getId()).as(Role.class);
    }
    
    public final Response updateAsResponse(final Role role) {
    	return givenAuthenticated().contentType(ContentType.JSON).body(role).put(getUri() + "/" + role.getId());
    }
    
    // 工具方法

    public final Response read(final String uri) {
    	return givenAuthenticated().accept(ContentType.JSON).get(uri);
    }
    
    public final String getUri() {
        return paths.getRoleUri();
    }

    public final RequestSpecification givenAuthenticated() {
        final Pair<String, String> credentials = getDefaultCredentials();
        return RestAssured.given()
        			.auth().preemptive().basic(credentials.getLeft(), credentials.getRight())
//        			.log().all()
        			;
    }

    private final Pair<String, String> getDefaultCredentials() {
        return new ImmutablePair<String, String>(UserCenter.ADMIN_EMAIL, UserCenter.ADMIN_PASS);
    }
}
