package com.konkerlabs.platform.registry.api.web.controller;

import com.konkerlabs.platform.registry.api.exceptions.BadServiceResponseException;
import com.konkerlabs.platform.registry.api.exceptions.NotFoundResponseException;
import com.konkerlabs.platform.registry.api.model.HealthAlertInputVO;
import com.konkerlabs.platform.registry.api.model.HealthAlertVO;
import com.konkerlabs.platform.registry.business.model.*;
import com.konkerlabs.platform.registry.business.model.RestDestination.Validations;
import com.konkerlabs.platform.registry.business.services.api.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Scope("request")
@RequestMapping(value = "/{application}/triggers")
@Api(tags = "alert triggers")
public class HealthAlertRestController extends AbstractRestController implements InitializingBean {

    @Autowired
    private AlertTriggerService alertTriggerService;

    @Autowired
    private HealthAlertService healthAlertService;

    @Autowired
    private DeviceRegisterService deviceRegisterService;

    private Set<String> validationsCode = new HashSet<>();

    @PostMapping(path = "/{triggerName}/alerts")
    @PreAuthorize("hasAuthority('LIST_ALERT_TRIGGERS')")
    @ApiOperation(
            value = "Create a health alert for a trigger",
            response = HealthAlertVO.class)
    public HealthAlertVO createAlert(@PathVariable("application") String applicationId,
                                     @PathVariable("triggerName") String triggerName,
                                     @RequestBody HealthAlertInputVO form)
            throws BadServiceResponseException, NotFoundResponseException {

        Tenant tenant = user.getTenant();
        Application application = getApplication(applicationId);
        AlertTrigger alertTrigger = getAlertTrigger(tenant, application, triggerName);
        Device device = getDevice(tenant, application, form.getDeviceId());

        HealthAlert healthAlert = HealthAlert
                .builder()
                .alertTrigger(alertTrigger)
                .device(device)
                .build();

        ServiceResponse<HealthAlert> registerResponse = healthAlertService
                .register(tenant, application, healthAlert);

        if (!registerResponse.isOk()) {
            throw new BadServiceResponseException(user, registerResponse, validationsCode);
        } else {
            return new HealthAlertVO().apply(registerResponse.getResult());
        }

    }

    @GetMapping(path = "/{triggerName}/alerts/{alertId}")
    @PreAuthorize("hasAuthority('LIST_ALERT_TRIGGERS')")
    @ApiOperation(
            value = "Update a health alert for a trigger",
            response = HealthAlertVO.class)
    public HealthAlertVO readAlert(@PathVariable("application") String applicationId,
                                   @PathVariable("triggerName") String triggerName,
                                   @PathVariable("alertId") String alertId
    ) throws BadServiceResponseException, NotFoundResponseException {

        Tenant tenant = user.getTenant();
        Application application = getApplication(applicationId);
        AlertTrigger alertTrigger = getAlertTrigger(tenant, application, triggerName);

        ServiceResponse<HealthAlert> alertResponse = healthAlertService.findByTenantApplicationTriggerAndAlertId(
                tenant,
                application,
                alertTrigger,
                alertId
        );

        if (!alertResponse.isOk()) {
            throw new BadServiceResponseException(user, alertResponse, validationsCode);
        } else {
            return new HealthAlertVO().apply(alertResponse.getResult());
        }

    }
    
    @PutMapping(path = "/{triggerName}/alerts/{alertId}")
    @PreAuthorize("hasAuthority('LIST_ALERT_TRIGGERS')")
    @ApiOperation(
            value = "Read a health alert for a trigger",
            response = HealthAlertVO.class)
    public HealthAlertVO editAlert(@PathVariable("application") String applicationId,
                                   @PathVariable("triggerName") String triggerName,
                                   @PathVariable("alertId") String alertId,
                                   @RequestBody HealthAlertInputVO form
    ) throws BadServiceResponseException, NotFoundResponseException {

        Tenant tenant = user.getTenant();
        Application application = getApplication(applicationId);
        AlertTrigger alertTrigger = getAlertTrigger(tenant, application, triggerName);

        ServiceResponse<HealthAlert> alertResponse = healthAlertService.findByTenantApplicationTriggerAndAlertId(
                tenant,
                application,
                alertTrigger,
                alertId
        );
        if (!alertResponse.isOk()) {
            throw new BadServiceResponseException(user, alertResponse, validationsCode);
        }

        HealthAlert healthAlert = HealthAlert
                .builder()
                .severity(HealthAlert.HealthAlertSeverity.valueOf(form.getSeverity()))
                .description(form.getDescription())
                .build();

        ServiceResponse<HealthAlert> registerResponse = healthAlertService
                .update(tenant, application, healthAlert.getGuid(), healthAlert);

        if (!registerResponse.isOk()) {
            throw new BadServiceResponseException(user, registerResponse, validationsCode);
        } else {
            return new HealthAlertVO().apply(registerResponse.getResult());
        }

    }

    @DeleteMapping(path = "/{triggerName}/alerts/{alertId}")
    @PreAuthorize("hasAuthority('LIST_ALERT_TRIGGERS')")
    @ApiOperation(
            value = "Update a health alert for a trigger",
            response = HealthAlertVO.class)
    public void deleteAlert(@PathVariable("application") String applicationId,
                                         @PathVariable("triggerName") String triggerName,
                                         @PathVariable("alertId") String alertId)
            throws BadServiceResponseException, NotFoundResponseException {

        Tenant tenant = user.getTenant();
        Application application = getApplication(applicationId);
        AlertTrigger alertTrigger = getAlertTrigger(tenant, application, triggerName);
        HealthAlert healthAlert = getHealthAlert(alertId, tenant, application, alertTrigger);

        ServiceResponse<HealthAlert> registerResponse = healthAlertService
                .remove(tenant, application, healthAlert.getGuid(), HealthAlert.Solution.TRIGGER_DELETED);

        if (!registerResponse.isOk()) {
            throw new BadServiceResponseException(user, registerResponse, validationsCode);
        }

    }

    @GetMapping(path = "/{triggerName}/alerts/")
    @PreAuthorize("hasAuthority('LIST_ALERT_TRIGGERS')")
    @ApiOperation(
            value = "List the health alerts for a trigger",
            response = HealthAlertVO.class)
    public List<HealthAlertVO> listAlerts(@PathVariable("application") String applicationId,
                                          @PathVariable("triggerName") String triggerName
    ) throws BadServiceResponseException, NotFoundResponseException {

        Tenant tenant = user.getTenant();
        Application application = getApplication(applicationId);
        AlertTrigger alertTrigger = getAlertTrigger(tenant, application, triggerName);

        ServiceResponse<List<HealthAlert>> registerResponse = healthAlertService
                .findAllByTenantApplicationAndTrigger(
                        tenant,
                        application,
                        alertTrigger
                );

        if (!registerResponse.isOk()) {
            throw new BadServiceResponseException(user, registerResponse, validationsCode);
        } else {
            return new HealthAlertVO().apply(registerResponse.getResult());
        }

    }

    private AlertTrigger getAlertTrigger(Tenant tenant, Application application, String triggerName) throws BadServiceResponseException {
        ServiceResponse<AlertTrigger> serviceResponse = alertTriggerService.findByTenantAndApplicationAndName(tenant, application, triggerName);
        if (!serviceResponse.isOk()) {
            throw new BadServiceResponseException(user, serviceResponse, validationsCode);
        }

        return serviceResponse.getResult();
    }

    private HealthAlert getHealthAlert(String alertId, Tenant tenant, Application application, AlertTrigger alertTrigger) throws BadServiceResponseException {
        ServiceResponse<HealthAlert> alertResponse = healthAlertService.findByTenantApplicationTriggerAndAlertId(
                tenant,
                application,
                alertTrigger,
                alertId
        );
        if (!alertResponse.isOk()) {
            throw new BadServiceResponseException(user, alertResponse, validationsCode);
        }

        return alertResponse.getResult();
    }

    private Device getDevice(Tenant tenant, Application application, String deviceId) throws BadServiceResponseException {
        ServiceResponse<Device> deviceResponse = deviceRegisterService.findByDeviceId(
                tenant,
                application,
                deviceId
        );

        if (!deviceResponse.isOk()) {
            throw new BadServiceResponseException(user, deviceResponse, validationsCode);
        }

        return deviceResponse.getResult();
    }

    @Override
    public void afterPropertiesSet() {
        for (Validations value : Validations.values()) {
            validationsCode.add(value.getCode());
        }
    }

}
