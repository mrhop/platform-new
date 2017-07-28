package cn.hopever.platform.user.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by Donghui Huo on 2016/9/5.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

    public static Logger logger = LoggerFactory.getLogger(AuthorizationServerConfig.class);


    @Value("${config.oauth2.privateKey}")
    private String privateKey;

    @Value("${config.oauth2.publicKey}")
    private String publicKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("clientTableService")
    private ClientDetailsService clientDetailsService;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        logger.info("Initializing JWT with public key:\n" + publicKey);
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Bean
    public ApprovalStore approvalStore() throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore());
        return store;
    }

    @Bean
    public ApprovalStoreUserApprovalHandler approvalHandler() throws Exception {
        ApprovalStoreUserApprovalHandler approvalHandler = new ApprovalStoreUserApprovalHandler();
        approvalHandler.setApprovalStore(approvalStore());
        approvalHandler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        approvalHandler.setClientDetailsService(clientDetailsService);
        return approvalHandler;
    }



    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("isAnonymous() || hasRole('ROLE_TRUSTED_CLIENT')") // permitAll()
                .checkTokenAccess("hasRole('ROLE_TRUSTED_CLIENT')"); // isAuthenticated() 用于resourceServer获取jwt public key【/oauth/token_key】，或者用于resourceserver校验token【/oauth/check_token】
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints

                // Which authenticationManager should be used for the password grant
                // If not provided, ResourceOwnerPasswordTokenGranter is not configured
                .authenticationManager(authenticationManager)

                // Use JwtTokenStore and our jwtAccessTokenConverter
                .tokenStore(tokenStore())
                .userApprovalHandler(approvalHandler())
                .accessTokenConverter(tokenEnhancer())
        ;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }



}
