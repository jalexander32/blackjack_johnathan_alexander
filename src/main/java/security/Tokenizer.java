package security;

import java.util.Map;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rds.auth.GetIamAuthTokenRequest;
import com.amazonaws.services.rds.auth.RdsIamAuthTokenGenerator;

import utils.YamlParser;

public class Tokenizer {
	protected Map<String, Object> config;
	public Tokenizer() {
		config = new YamlParser("database").getMapping();
	}
	public final Token generateToken() {
		BasicAWSCredentials credentials = new BasicAWSCredentials((String) config.get("aws_access_key_id"), (String) config.get("aws_secret_access_key"));
		
		RdsIamAuthTokenGenerator generator = RdsIamAuthTokenGenerator.builder()
				.credentials(new AWSStaticCredentialsProvider(credentials))
				.region((String)config.get("RDS_REGION"))
				.build();
		
		String token = generator.getAuthToken(
				GetIamAuthTokenRequest.builder()
				.hostname((String)config.get("RDS_HOSTNAME"))
				.port(Integer.parseInt((String)config.get("RDS_PORT")))
				.userName((String)config.get("username"))
				.build());
		return new Token(token);
	}
}
