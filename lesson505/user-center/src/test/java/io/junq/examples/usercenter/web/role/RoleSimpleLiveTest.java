package io.junq.examples.usercenter.web.role;

import static io.junq.examples.common.spring.util.Profiles.CLIENT;
import static io.junq.examples.common.spring.util.Profiles.TEST;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.Collection;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.Sets;

import io.junq.examples.common.interfaces.IDto;
import io.junq.examples.usercenter.client.template.GenericSimpleApiClient;
import io.junq.examples.usercenter.client.template.RoleSimpleApiClient;
import io.junq.examples.usercenter.persistence.model.Privilege;
import io.junq.examples.usercenter.persistence.model.Role;
import io.junq.examples.usercenter.spring.RestTestConfig;
import io.junq.examples.usercenter.spring.UserCenterClientConfig;
import io.junq.examples.usercenter.spring.UserCenterLiveTestConfiguration;

@ActiveProfiles({ CLIENT, TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserCenterLiveTestConfiguration.class, UserCenterClientConfig.class, RestTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleSimpleLiveTest extends GenericSimpleLiveTest<Role> {

	@Autowired
	private RoleSimpleApiClient api;
	
	@Override
	protected GenericSimpleApiClient<Role> getApi() {
		return api;
	}

	@Override
	protected Privilege createNewAssociationResource() {
		return new Privilege(randomAlphabetic(8));
	}

	@Override
	protected Collection<Privilege> getAssociations(Role resource) {
		return resource.getPrivileges();
	}

	@Override
	protected Role createNewResource() {
		return new Role(randomAlphabetic(8), Sets.<Privilege> newHashSet());
	}
	
}
