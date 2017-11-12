package io.junq.examples.usercenter.client.template;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import io.junq.examples.common.spring.util.Profiles;
import io.junq.examples.usercenter.persistence.model.Role;

@Component
@Profile(Profiles.CLIENT)
public final class RoleSimpleApiClient extends GenericSimpleApiClient<Role> {

    public RoleSimpleApiClient() {
        super(Role.class);
    }

    // API

    @Override
    public final String getUri() {
        return paths.getRoleUri();
    }

}
