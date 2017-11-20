package io.junq.examples.usercenter.model;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.springframework.stereotype.Component;

import io.junq.examples.client.IDtoOperations;
import io.junq.examples.usercenter.persistence.model.Privilege;

@Component
public final class PrivilegeDtoOpsImpl implements IDtoOperations<Privilege> {

    public PrivilegeDtoOpsImpl() {
        super();
    }

    // template method

    @Override
    public final Privilege createNewResource() {
        return new Privilege(randomAlphabetic(8));
    }

    @Override
    public final void invalidate(final Privilege entity) {
        entity.setName(null);
    }

    @Override
    public void change(final Privilege resource) {
        resource.setName(randomAlphabetic(8));
    }

}
