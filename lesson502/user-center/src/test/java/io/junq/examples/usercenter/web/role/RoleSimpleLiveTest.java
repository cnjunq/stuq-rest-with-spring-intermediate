package io.junq.examples.usercenter.web.role;

import static io.junq.examples.common.spring.util.Profiles.CLIENT;
import static io.junq.examples.common.spring.util.Profiles.TEST;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


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
    
    // 查找单条记录

    @Test
    public final void whenNonExistingResourceIsRetrieved_then404IsReceived() {
        final Response response = getApi().findOneAsResponse(IDUtil.randomPositiveLong());

        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public final void whenResourceIsRetrievedByNonNumericId_then400IsReceived() {
        // When
        final Response res = getApi().read(getUri() + WebConstants.PATH_SEP + randomAlphabetic(6));

        // Then
        assertThat(res.getStatusCode(), is(400));
    }

    @Test
    public final void givenResourceForIdExists_whenResourceOfThatIdIsRetrieved_then200IsRetrieved() {
        // Given
        final String uriForResourseCreation = getApi().createAsResponse(createNewResource()).getHeader(HttpHeaders.LOCATION);

        // when
        final Response response = getApi().read(uriForResourseCreation);

        // Then
        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public final void whenResourceIsCreated_thenResourceIsCorrectlyRetrieved() {
        // Given, When
        final Role newResource = createNewResource();
        final Role createdResource = getApi().create(newResource);

        // Then
        assertEquals(createdResource, newResource);
    }

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
