package com.konkerlabs.platform.registry.api.test.config;

import com.konkerlabs.platform.registry.business.model.*;
import com.konkerlabs.platform.registry.business.model.enumerations.DateFormat;
import com.konkerlabs.platform.registry.business.model.enumerations.Language;
import com.konkerlabs.platform.registry.business.model.enumerations.TimeZone;
import com.konkerlabs.platform.registry.business.services.api.*;
import com.konkerlabs.platform.registry.idm.services.OAuth2AccessTokenService;
import com.konkerlabs.platform.registry.idm.services.OAuthClientDetailsService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import java.util.Arrays;

@Configuration
public class WebGatewayTestConfiguration {

    @Bean
    public Tenant tenant() {
        return Tenant.builder().name("konker").domainName("konker").id("id").build();
    }

    @Bean
    public Application application() {
        return Application.builder().name("konker").build();
    }

    @Bean
    public OauthClientDetails user() {
        User user = User.builder()
                .email("user@domain.com")
                .zoneId(TimeZone.AMERICA_SAO_PAULO)
                .language(Language.EN)
                .avatar("default.jpg")
                .dateFormat(DateFormat.YYYYMMDD)
                .tenant(tenant()).build();

        Location br =
                Location.builder()
                        .application(application())
                        .guid("guid-br")
                        .id("br")
                        .name("br")
                        .description("br")
                        .build();

        Location room0  =
                Location.builder()
                        .application(application())
                        .name("sp")
                        .description("desc-sp")
                        .guid("guid-sp")
                        .defaultLocation(false).build();

        Location room1 =
                Location.builder()
                        .application(application())
                        .guid("guid-rj")
                        .id("rj")
                        .name("rj")
                        .description("rj")
                        .parent(br)
                        .build();

        Location room101Roof = Location.builder()
                .tenant(tenant())
                .application(application())
                .parent(room1)
                .name("room-101-roof")
                .guid("guid-room-101-roof")
                .parent(room1)
                .build();

        room1.setChildren(Arrays.asList(room101Roof));
        br.setChildren(Arrays.asList(room0, room1));


        return OauthClientDetails
                .builder()
                .parentGateway(
                        Gateway
                                .builder()
                                .name("konker")
                                .active(true)
                                .application(application())
                                .location(br)
                                .build())
                .build()
                .setUserProperties(user);
    }

    @Bean
    public DeviceRegisterService deviceRegistryService() {
        return Mockito.mock(DeviceRegisterService.class);
    }

    @Bean
    public EventRouteService eventRouteService() {
        return Mockito.mock(EventRouteService.class);
    }

    @Bean
    public TransformationService transformationService() {
        return Mockito.mock(TransformationService.class);
    }

    @Bean
    public RestDestinationService restDestinationService() {
    	return Mockito.mock(RestDestinationService.class);
    }

    @Bean
    public DeviceEventService deviceEventService() {
        return Mockito.mock(DeviceEventService.class);
    }

    @Bean
    public UserService userService() {
    	return Mockito.mock(UserService.class);
    }

    @Bean
    public RoleService roleService() {
    	return Mockito.mock(RoleService.class);
    }

    @Bean
    public ApplicationService applicationService() {
    	return Mockito.mock(ApplicationService.class);
    }

    @Bean
    public LocationSearchService locationSearchService() {
        return Mockito.mock(LocationSearchService.class);
    }

    @Bean
    public LocationService locationService() {
        return Mockito.mock(LocationService.class);
    }

    @Bean
    public DeviceConfigSetupService deviceConfigSetupService() {
        return Mockito.mock(DeviceConfigSetupService.class);
    }

    @Bean
    public DeviceModelService deviceModelService() {
    	return Mockito.mock(DeviceModelService.class);
    }

    @Bean
    public AlertTriggerService alertTriggerService() {
        return Mockito.mock(AlertTriggerService.class);
    }

    @Bean
    public HealthAlertService healthAlertService() {
        return Mockito.mock(HealthAlertService.class);
    }

    @Bean
    public DeviceCustomDataService deviceCustomDataService() {
        return Mockito.mock(DeviceCustomDataService.class);
    }

    @Bean
    public GatewayService gatewayService() {
        return Mockito.mock(GatewayService.class);
    }

    @Bean
    public OAuth2AccessTokenService oAuth2AccessTokenService() {
        return Mockito.mock(OAuth2AccessTokenService.class);
    }

    @Bean
    public DefaultTokenServices defaultTokenServices() {
        return Mockito.mock(DefaultTokenServices.class);
    }

    @Bean
    public OAuthClientDetailsService oAuthClientDetailsService() {
        return Mockito.mock(OAuthClientDetailsService.class);
    }

    @Bean
    public ApplicationDocumentStoreService applicationDocumentStoreService() {
        return Mockito.mock(ApplicationDocumentStoreService.class);
    }

    @Bean
    public DeviceFirmwareService deviceFirmwareServiceeviceFirmwareService() {
        return Mockito.mock(DeviceFirmwareService.class);
    }

    @Bean
    public Gateway gateway() {
        return Gateway.builder().location(
                Location.builder()
                        .application(application())
                        .guid("f06d9d2d-f5ce-4cc6-8637-348743e8acad")
                        .id("br")
                        .name("br")
                        .description("br")
                        .build()
        ).name("konker").build();
    }

}
