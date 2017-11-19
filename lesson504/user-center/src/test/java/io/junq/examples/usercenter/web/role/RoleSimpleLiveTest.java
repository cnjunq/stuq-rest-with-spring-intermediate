package io.junq.examples.usercenter.web.role;

import static io.junq.examples.common.spring.util.Profiles.CLIENT;
import static io.junq.examples.common.spring.util.Profiles.TEST;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.Sets;
import com.google.common.net.HttpHeaders;

import io.junq.examples.common.interfaces.IDto;
import io.junq.examples.common.web.WebConstants;
import io.junq.examples.test.common.util.IDUtil;
import io.junq.examples.usercenter.client.template.GenericSimpleApiClient;
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
public class RoleSimpleLiveTest extends GenericSimpleLiveTest<Role> {
	
    @Autowired
    private RoleSimpleApiClient api;
    
    @Override
    protected final RoleSimpleApiClient getApi() {
        return api;
    }

    @Override
    protected final Role createNewResource() {
        return new Role(randomAlphabetic(8), Sets.<Privilege> newHashSet());
    }

    @Override
    protected Collection<Privilege> getAssociations(Role resource) {
        return resource.getPrivileges();
    }

    @Override
    protected Privilege createNewAssociationResource() {
        return new Privilege(randomAlphabetic(8));
    }
}
