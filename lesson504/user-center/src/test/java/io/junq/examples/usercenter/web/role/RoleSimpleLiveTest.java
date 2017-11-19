package io.junq.examples.usercenter.web.role;

import static io.junq.examples.common.spring.util.Profiles.CLIENT;
import static io.junq.examples.common.spring.util.Profiles.TEST;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.Sets;
import com.google.common.net.HttpHeaders;

import io.junq.examples.common.web.WebConstants;
import io.junq.examples.test.common.util.IDUtil;
import io.junq.examples.usercenter.client.template.RoleSimpleApiClient;
import io.junq.examples.usercenter.persistence.model.Privilege;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.spring.RestTestConfig;
import io.junq.examples.usercenter.spring.UserCenterClientConfig;
import io.junq.examples.usercenter.spring.UserCenterLiveTestConfiguration;
import io.restassured.response.Response;

@ActiveProfiles({ CLIENT, TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserCenterLiveTestConfiguration.class, UserCenterClientConfig.class, RestTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleSimpleLiveTest {

    @Autowired
    private RoleSimpleApiClient apiClient;
    
    // 查找单条记录，指定id（数字型）的角色对象不存在

    @Test
    public final void whenNonExistingResourceIsRetrieved_then404IsReceived() {
        final Response response = getApi().findOneAsResponse(IDUtil.randomPositiveLong());
        
        // 状态码将是500，抛出资源不存在的异常IJResourceNotFoundException

        assertThat(response.getStatusCode(), is(404));
    }
    
    // 查找单条记录，指定id（非数字型）的角色对象，应返回400

    @Test
    public final void whenResourceIsRetrievedByNonNumericId_then400IsReceived() {
        // When
        final Response res = getApi().read(getUri() + randomAlphabetic(6));

        // Then
        assertThat(res.getStatusCode(), is(400));
    }

    // 查找所有角色列表
    
    @Test
    public final void whenAllResourcesAreRetrieved_then200IsReceived() {
        // When
        final Response response = getApi().read(getUri());

        // Then
        assertThat(response.getStatusCode(), is(200));
    }
    
    // 创建角色资源

    // 创建成功，并返回201
    @Test
    public final void whenResourceIsCreated_then201IsReceived() {
        // When
        final Response response = getApi().createAsResponse(createNewResource());

        // Then
        assertThat(response.getStatusCode(), is(201));
    }
    
    // 创建成功（名字有空格），并返回201
    @Test
    public final void givenResourceHasNameWithSpace_whenResourceIsCreated_then201IsReceived() {
        final Role newResource = createNewResource();
        newResource.setName(randomAlphabetic(4) + " " + randomAlphabetic(4));

        // When
        final Response createAsResponse = getApi().createAsResponse(newResource);

        // Then
        assertThat(createAsResponse.getStatusCode(), is(201));
    }
    
    // 创建失败（不支持的内容格式），返回415
    @Test
    public final void whenResourceWithUnsupportedMediaTypeIsCreated_then415IsReceived() {
        // When
        final Response response = getApi().givenAuthenticated().contentType("unknown").post(getUri());

        // Then
        assertThat(response.getStatusCode(), is(415));
    }
    
    // 创建失败（已存在id），返回409
    @Test
    public final void whenResourceIsCreatedWithNonNullId_then409IsReceived() {
        final Role resourceWithId = createNewResource();
        resourceWithId.setId(5l);

        // When
        final Response response = getApi().createAsResponse(resourceWithId);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }
    
    // 创建成功，返回location
    @Test
    public final void whenResourceIsCreated_thenResponseContainsTheLocationHeader() {
        // When
        final Response response = getApi().createAsResponse(createNewResource());

        // Then
        assertNotNull(response.getHeader(HttpHeaders.LOCATION));
    }

    // 创建失败（重复创建），返回409
    @Test
    public final void givenResourceExsits_whenResourceWithSameAttributeIsCreated_then409IsReceived() {
        // Given
        final Role newEntity = createNewResource();
        getApi().createAsResponse(newEntity);

        // when
        final Response response = getApi().createAsResponse(newEntity);

        // Then
        assertThat(response.getStatusCode(), is(409));
    }

    // 更新操作，PUT
    
    // 删除操作，DELETE
    
    // 工具方法

    private final String getUri() {
        return getApi().getUri() + WebConstants.PATH_SEP;
    }

    private final RoleSimpleApiClient getApi() {
        return apiClient;
    }

    private final Role createNewResource() {
        return new Role(randomAlphabetic(8), Sets.<Privilege> newHashSet());
    }
	
}
