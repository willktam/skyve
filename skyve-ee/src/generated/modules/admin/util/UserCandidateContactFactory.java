package modules.admin.util;

import modules.admin.domain.UserCandidateContact;
import org.skyve.CORE;
import org.skyve.metadata.customer.Customer;
import org.skyve.metadata.model.document.Document;
import org.skyve.metadata.module.Module;
import org.skyve.util.Util;
import org.skyve.util.test.SkyveFactory;
import util.AbstractDomainFactory;

/**
 * Generated - local changes will be overwritten.
 * Create class src/skyve/modules/admin/UserCandidateContact/UserCandidateContactFactoryExtension.java
 * to extend this class and customise specific values for this document.
 */
@SkyveFactory
public class UserCandidateContactFactory extends AbstractDomainFactory<UserCandidateContact > {

	@Override
	public UserCandidateContact getInstance() throws Exception {
		Customer customer = CORE.getUser().getCustomer();
		Module module = customer.getModule(UserCandidateContact.MODULE_NAME);
		Document document = module.getDocument(customer, UserCandidateContact.DOCUMENT_NAME);

		UserCandidateContact userCandidateContact = Util.constructRandomInstance(CORE.getPersistence().getUser(), module, document, 1);

		return userCandidateContact;
	}
}