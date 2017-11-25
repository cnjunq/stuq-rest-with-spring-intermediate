package io.junq.examples.usercenter.web.role;

import static io.junq.examples.common.spring.util.Profiles.CLIENT;
import static io.junq.examples.common.spring.util.Profiles.TEST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.junq.examples.usercenter.client.template.RoleSimpleApiClient;
import io.junq.examples.usercenter.spring.RestTestConfig;
import io.junq.examples.usercenter.spring.UserCenterClientConfig;
import io.junq.examples.usercenter.spring.UserCenterLiveTestConfiguration;
import io.restassured.response.Response;

@ActiveProfiles({ CLIENT, TEST })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserCenterLiveTestConfiguration.class, UserCenterClientConfig.class, RestTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleContractLiveTest {

	@Autowired
	private RoleSimpleApiClient api;
	
    @Test
    public final void whenResourceIsCreated_then201IsReceived() throws IOException {
        // When
        final Response response = getApi().createAsResponse(createNewResource());

        // Then
        assertThat(response.getStatusCode(), is(201));
        assertNotNull(response.getHeader(HttpHeaders.LOCATION));
    }
    
//    private final Role createNewResource() {
//        return new Role(randomAlphabetic(8), Sets.<Privilege> newHashSet());
//    }
    
//    private final String createNewResource() {
//    	final Role role = new Role(randomAlphabetic(8), Sets.<Privilege> newHashSet());
//    	return getApi().getMarshaller().encode(role);
//    }
    
//    private final String createNewResource() {
//        final String roleData = "{\"id\":null,\"name\":\"" + randomAlphabetic(8) + "\",\"privileges\":[]}";
//    	return roleData;
//    }
    
//    private final String createNewResource() throws IOException {
//    	final InputStream stream = getClass().getResourceAsStream("/data/role_json_01.json");
//    	final String roleData = CharStreams.toString(new InputStreamReader(stream));
//		return roleData;
//    }
    
    private final String createNewResource() throws JsonProcessingException, IOException {
    	final InputStream stream = getClass().getResourceAsStream("/data/role_json_01.json");
    	final JsonNode rootNode = new ObjectMapper().readTree(stream);
    	((ObjectNode) rootNode).set("name", JsonNodeFactory.instance.textNode(randomAlphabetic(8)));
    	return rootNode.toString();
    }
    
	protected RoleSimpleApiClient getApi() {
		return api;
	}
}
