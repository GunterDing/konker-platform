package com.konkerlabs.platform.registry.business.services.api;

import com.konkerlabs.platform.registry.business.model.AlertTrigger;
import com.konkerlabs.platform.registry.business.model.Application;
import com.konkerlabs.platform.registry.business.model.HealthAlert;
import com.konkerlabs.platform.registry.business.model.HealthAlert.Solution;
import com.konkerlabs.platform.registry.business.model.Tenant;

import java.util.List;

public interface HealthAlertService {

    enum Validations {
		HEALTH_ALERT_NULL("service.healthalert.null"),
        HEALTH_ALERT_NULL_ALERT_ID("service.healthalert.null_alert_id"),
        HEALTH_ALERT_ALREADY_EXISTS("service.healthalert.already_exists"),
		HEALTH_ALERT_DOES_NOT_EXIST("service.healthalert.does.not.exist"),
		HEALTH_ALERT_GUID_IS_NULL("service.healthalert.guid.null"),
		HEALTH_ALERT_NOT_FOUND("service.healthalert.not.found"),
        HEALTH_ALERT_DEVICE_NULL("service.healthalert.device.null"),
		HEALTH_ALERT_TRIGGER_NULL("service.healthalert.trigger.null"),
		HEALTH_ALERT_TRIGGER_GUID_NULL("service.healthalert.trigger.guid.null"),
		HEALTH_ALERT_TRIGGER_NOT_EXIST("service.healthalert.trigger.not.exist"),
		HEALTH_ALERT_WITH_STATUS_OK("service.healthalert.with_status_ok");

		public String getCode() {
			return code;
		}

		private String code;

		Validations(String code) {
			this.code = code;
		}
	}

	enum Messages {
		HEALTH_ALERT_REMOVED_SUCCESSFULLY("controller.healthalert.removed.succesfully");

		public String getCode() {
			return code;
		}

		private String code;

		Messages(String code) {
			this.code = code;
		}
	}

	ServiceResponse<HealthAlert> findByTenantApplicationTriggerAndAlertId(Tenant tenant, Application application, AlertTrigger alertTrigger, String alertId);
	ServiceResponse<HealthAlert> getByTenantApplicationAndHealthAlertGuid(Tenant tenant, Application application, String healthAlertGuid);
	ServiceResponse<HealthAlert> getLastHighestSeverityByDeviceGuid(Tenant tenant, Application application, String deviceGuid);
	ServiceResponse<HealthAlert> register(Tenant tenant, Application application, HealthAlert healthAlert);
	ServiceResponse<HealthAlert> remove(Tenant tenant, Application application, String healthAlertGuid, Solution solution);
	ServiceResponse<HealthAlert> update(Tenant tenant, Application application, String healthAlertGuid, HealthAlert healthAlert);
    ServiceResponse<HealthAlert> getCurrentHealthByGuid(Tenant tenant, Application application, String deviceGuid);
	ServiceResponse<List<HealthAlert>>  removeAlertsFromTrigger(Tenant tenant, Application application, AlertTrigger alertTrigger);
	ServiceResponse<List<HealthAlert>> findAllByTenantAndApplication(Tenant tenant, Application application);
    ServiceResponse<List<HealthAlert>> findAllByTenantApplicationAndDeviceGuid(Tenant tenant, Application application, String deviceGuid);
    ServiceResponse<List<HealthAlert>> findAllByTenantApplicationAndTrigger(Tenant tenant, Application application, AlertTrigger alertTrigger);

}