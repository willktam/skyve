package modules.admin.SystemDashboard;

import java.util.Locale;

import org.skyve.CORE;
import org.skyve.cache.ConversationUtil;
import org.skyve.impl.util.UtilImpl;
import org.skyve.metadata.model.document.Bizlet;
import org.skyve.util.Util;

import modules.admin.Configuration.ConfigurationExtension;
import modules.admin.domain.Configuration;
import modules.admin.domain.Generic;
import modules.admin.domain.SystemDashboard;

public class SystemDashboardBizlet extends Bizlet<SystemDashboard> {

	private static final long serialVersionUID = -4784606165710946704L;

	@Override
	public SystemDashboard newInstance(SystemDashboard bean) throws Exception {

		// generate status information for display
		Locale locale = CORE.getUser().getLocale();
		String valTrue = Util.i18n("ui.true.valueIconStyleClass", locale);
		String valFalse = Util.i18n("ui.false.valueIconStyleClass", locale);
		String valDisabled = Util.i18n("ui.disabled.value", locale);
		String valNo = Util.i18n("ui.no.value", locale);
		String valUnavailable = Util.i18n("ui.unavailable.value", locale);

		// session count
		Generic sessionCount = Generic.newInstance();
		sessionCount.setMemo1(formatLabelHTML(Util.i18n("admin.systemDashboard.status.itemLabel.sessionCount", locale)));
		sessionCount.setText5001(formatStringValueHTML(Integer.toString(ConversationUtil.getSessionCount()), ""));
		bean.getStatus().add(sessionCount);

		// email configuration
		Generic emailConfig = Generic.newInstance();
		emailConfig.setMemo1(formatLabelHTML(Util.i18n("admin.systemDashboard.status.itemLabel.emailConfigured", locale)));
		emailConfig.setText5001(formatBooleanHTML(ConfigurationExtension.validSMTPHost(), valTrue, valFalse,
				Util.i18n("admin.systemDashboard.status.itemLabel.emailConfigured.suggestion", locale)));
		bean.getStatus().add(emailConfig);

		// backups configured
		Generic backupConfig = Generic.newInstance();
		backupConfig.setMemo1(formatLabelHTML(Util.i18n("admin.systemDashboard.status.itemLabel.backupsConfigured", locale)));
		backupConfig.setText5001(formatBooleanHTML(ConfigurationExtension.validBackupConfiguration(), valTrue, valFalse,
				Util.i18n("admin.systemDashboard.status.itemLabel.backupsConfigured.suggestion", locale)));
		bean.getStatus().add(backupConfig);

		final boolean jobScheduler = UtilImpl.JOB_SCHEDULER;

		// backup job scheduled
		Generic backupScheduled = Generic.newInstance();
		backupScheduled.setMemo1(formatLabelHTML(Util.i18n("admin.systemDashboard.status.itemLabel.backupsScheduled", locale)));
		if (jobScheduler) {
			backupScheduled.setText5001(formatBooleanHTML(ConfigurationExtension.validBackupSchedule(), valTrue, valFalse,
					Util.i18n("admin.systemDashboard.status.itemLabel.backupsScheduled.suggestion", locale)));
		} else {
			backupScheduled.setText5001(formatStringValueHTML(valUnavailable,
					Util.i18n("admin.systemDashboard.status.itemLabel.backupsScheduled.suggestion", locale)));
		}
		bean.getStatus().add(backupScheduled);

		// job scheduler enabled
		Generic schedulerEnabled = Generic.newInstance();
		schedulerEnabled.setMemo1(formatLabelHTML(Util.i18n("admin.systemDashboard.status.itemLabel.jobSchedulerEnabled", locale)));
		schedulerEnabled.setText5001(jobScheduler ? formatBooleanHTML(true, valTrue, valFalse, "")
				: formatStringValueHTML(valDisabled,
						Util.i18n("admin.systemDashboard.status.itemLabel.selfRegistrationConfigured.suggestion", locale)));
		bean.getStatus().add(schedulerEnabled);

		// self registration activated
		ConfigurationExtension config = Configuration.newInstance();
		Generic selfRegConfigured = Generic.newInstance();
		selfRegConfigured.setMemo1(formatLabelHTML(Util.i18n("admin.systemDashboard.status.itemLabel.selfRegistrationConfigured", locale)));
		selfRegConfigured.setText5001((config.validSelfRegistration() ? formatBooleanHTML(true, valTrue, valFalse, "")
				: formatStringValueHTML(valNo,
						Util.i18n("admin.systemDashboard.status.itemLabel.selfRegistrationConfigured.suggestion", locale))));
		bean.getStatus().add(selfRegConfigured);

		// anonymous user configured
		Generic anonymousUserConfigured = Generic.newInstance();
		anonymousUserConfigured.setMemo1(formatLabelHTML(Util.i18n("admin.systemDashboard.status.itemLabel.anonymousPublicUserConfigured", locale)));
		anonymousUserConfigured.setText5001((config.validAnonymousPublicUser() ? formatBooleanHTML(true, valTrue, valFalse, "")
				: formatStringValueHTML(valNo,
						Util.i18n("admin.systemDashboard.status.itemLabel.anonymousPublicUserConfigured.suggestion", locale))));
		bean.getStatus().add(anonymousUserConfigured);

		// Password self-reset configured
		Generic passwordSelfResetConfigured = Generic.newInstance();
		passwordSelfResetConfigured.setMemo1(formatLabelHTML(Util.i18n("admin.systemDashboard.status.itemLabel.passwordSelfResetConfigured", locale)));
		passwordSelfResetConfigured.setText5001(formatBooleanHTML(UtilImpl.GOOGLE_RECAPTCHA_SITE_KEY != null, valTrue, valFalse,
				Util.i18n("admin.systemDashboard.status.itemLabel.passwordSelfResetConfigured.suggestion", locale)));
		bean.getStatus().add(passwordSelfResetConfigured);

		return super.newInstance(bean);
	}

	private static String formatBooleanHTML(final boolean value, final String valTrue, final String valFalse, final String suggestion) {
		final String template = "<i style='color: %1$s;' class='fa %2$s' title='%3$s' ></i>";

		if (value == true) {
			return String.format(template, "#4bc0c0", valTrue, "");
		}
		return String.format(template, "#ff6385", valFalse, suggestion);
	}

	private static String formatStringValueHTML(final String value, final String suggestion) {
		return String.format("<div><i title='%1$s'>%2$s</i></div>", suggestion, value);
	}

	private static String formatLabelHTML(final String label) {
		return String.format("<div style='text-align: left'>%1$s<div>", label);
	}
}
