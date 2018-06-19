package util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.skyve.CORE;
import org.skyve.domain.PersistentBean;
import org.skyve.domain.messages.ValidationException;
import org.skyve.impl.metadata.model.document.field.Enumeration;
import org.skyve.impl.metadata.repository.AbstractRepository;
import org.skyve.impl.util.TestUtil;
import org.skyve.metadata.customer.Customer;
import org.skyve.metadata.model.Attribute;
import org.skyve.metadata.model.Attribute.AttributeType;
import org.skyve.metadata.model.document.Bizlet;
import org.skyve.metadata.model.document.Document;
import org.skyve.metadata.module.Module;
import org.skyve.util.Binder;
import org.skyve.util.Util;

public abstract class AbstractDomainTest<T extends PersistentBean> extends AbstractH2Test {

	protected abstract T getBean() throws Exception;

	private static final SecureRandom random = new SecureRandom();

	@Test
	@SuppressWarnings("boxing")
	public void testDelete() throws Exception {
		// create the test data
		T bean = getBean();

		assertThat(bean.isPersisted(), is(false));

		T result = CORE.getPersistence().save(bean);

		// validate the test data
		assertThat(result, is(notNullValue()));
		assertThat(result.isPersisted(), is(true));
		assertThat(result.getBizId(), is(notNullValue()));

		// perform the method under test
		CORE.getPersistence().delete(result);

		// verify the results
		CORE.getPersistence().evictAllCached();
		T deleted = CORE.getPersistence().retrieve(bean.getBizModule(), bean.getBizDocument(), bean.getBizId(), false);

		assertThat(deleted, is(nullValue()));
	}

	@Test
	@SuppressWarnings("boxing")
	public void testFindAll() throws Exception {
		// create the test data
		T b1 = getBean();
		T b2 = getBean();

		int beanCount = CORE.getPersistence().newDocumentQuery(b1.getBizModule(), b1.getBizDocument()).beanResults().size();

		CORE.getPersistence().save(b1);
		CORE.getPersistence().save(b2);

		// perform the method under test
		List<T> results = CORE.getPersistence().newDocumentQuery(b1.getBizModule(), b1.getBizDocument()).beanResults();

		// verify the results
		assertThat(results.size(), is(beanCount + 2));
	}

	@Test
	public void testFindById() throws Exception {
		// create the test data
		T bean = getBean();

		CORE.getPersistence().save(bean);

		// perform the method under test
		T result = CORE.getPersistence().retrieve(bean.getBizModule(), bean.getBizDocument(), bean.getBizId(), false);

		// verify the results
		assertThat(result, is(notNullValue()));
		assertThat(result.getBizId(), is(bean.getBizId()));
		assertThat(result, is(bean));
	}

	@Test
	public void testGetConstantDomainValues() throws Exception {
		assumeTrue(getBizlet() != null);

		// create the test data
		ArrayList<? extends Attribute> allAttributes = getAllAttributes(getBean());

		// perform the method under test
		for (Attribute attribute : allAttributes) {
			AttributeType type = attribute.getAttributeType();

			// skip updating content, an association or collection, try find a scalar attribute to update
			if (AttributeType.content.equals(type) || AttributeType.collection.equals(type)
					|| AttributeType.association.equals(type)
					|| AttributeType.inverseOne.equals(type) || AttributeType.inverseMany.equals(type)) {
				continue;
			}

			try {
				getBizlet().getConstantDomainValues(attribute.getName());
			} catch (ValidationException e) {
				// pass - action handled incorrect input
			}
		}
	}

	@Test
	public void testGetDynamicDomainValues() throws Exception {
		assumeTrue(getBizlet() != null);

		// create the test data
		ArrayList<? extends Attribute> allAttributes = getAllAttributes(getBean());

		// perform the method under test
		for (Attribute attribute : allAttributes) {
			AttributeType type = attribute.getAttributeType();

			// skip updating content, an association or collection, try find a scalar attribute to update
			if (AttributeType.content.equals(type) || AttributeType.collection.equals(type)
					|| AttributeType.association.equals(type)
					|| AttributeType.inverseOne.equals(type) || AttributeType.inverseMany.equals(type)) {
				continue;
			}

			try {
				getBizlet().getDynamicDomainValues(attribute.getName(), getBean());
			} catch (ValidationException e) {
				// pass - action handled incorrect input
			}
		}
	}

	@Test
	public void testGetVariantDomainValues() throws Exception {
		assumeTrue(getBizlet() != null);
		// create the test data
		T bean = getBean();
		ArrayList<? extends Attribute> allAttributes = getAllAttributes(bean);

		// perform the method under test
		for (Attribute attribute : allAttributes) {
			AttributeType type = attribute.getAttributeType();

			// skip updating content, an association or collection, try find a scalar attribute to update
			if (AttributeType.content.equals(type) || AttributeType.collection.equals(type)
					|| AttributeType.association.equals(type)
					|| AttributeType.inverseOne.equals(type) || AttributeType.inverseMany.equals(type)) {
				continue;
			}

			try {
				getBizlet().getVariantDomainValues(attribute.getName());
			} catch (ValidationException e) {
				// pass - action handled incorrect input
			}
		}
	}

	@Test
	@SuppressWarnings("boxing")
	public void testSave() throws Exception {
		// create the test data
		T bean = getBean();

		// validate the test data
		assertThat(bean.isPersisted(), is(false));

		// perform the method under test
		T result = CORE.getPersistence().save(bean);

		// verify the results
		assertThat(result, is(notNullValue()));
		assertThat(result.isPersisted(), is(true));
		assertThat(result.getBizId(), is(notNullValue()));
	}

	@Test
	@SuppressWarnings("boxing")
	public void testUpdate() throws Exception {
		// create the test data
		T bean = getBean();

		// validate the test data
		assertThat(bean.isPersisted(), is(false));

		// perform the method under test
		T result = CORE.getPersistence().save(bean);

		// verify the results
		assertThat(result, is(notNullValue()));
		assertThat(result.isPersisted(), is(true));
		assertThat(result.getBizId(), is(notNullValue()));

		// perform an update
		Attribute attributeToUpdate = getRandomAttribute(result);

		if (attributeToUpdate != null) {
			Object originalValue = Binder.get(result, attributeToUpdate.getName());

			Customer customer = CORE.getUser().getCustomer();
			Module module = customer.getModule(getBean().getBizModule());
			Document document = module.getDocument(customer, getBean().getBizDocument());

			TestUtil.updateAttribute(module, document, result, attributeToUpdate);
			T uResult = CORE.getPersistence().save(result);

			// verify the results
			assertThat("Error updating " + attributeToUpdate.getName(), Binder.get(uResult, attributeToUpdate.getName()),
					is(not(originalValue)));
		} else {
			Util.LOGGER.fine(String.format("Skipping update test for %s, no scalar attribute found", bean.getBizDocument()));
		}
	}

	private ArrayList<? extends Attribute> getAllAttributes(T bean) {
		Customer customer = CORE.getUser().getCustomer();
		Module module = customer.getModule(bean.getBizModule());
		Document document = module.getDocument(customer, bean.getBizDocument());
		ArrayList<? extends Attribute> allAttributes = new ArrayList<>(document.getAllAttributes());
		return allAttributes;
	}

	private Bizlet<T> getBizlet() throws Exception {
		Customer customer = CORE.getUser().getCustomer();
		Module module = customer.getModule(getBean().getBizModule());
		Document document = module.getDocument(customer, getBean().getBizDocument());

		return AbstractRepository.get().getBizlet(customer, document);
	}

	private Attribute getRandomAttribute(T bean) {
		Customer customer = CORE.getUser().getCustomer();
		Module module = customer.getModule(bean.getBizModule());
		Document document = module.getDocument(customer, bean.getBizDocument());
		Attribute transientAttribute = null;

		ArrayList<? extends Attribute> allAttributes = new ArrayList<>(document.getAllAttributes());

		// randomise the attributes in the collection
		Collections.shuffle(allAttributes, random);

		for (Attribute attribute : allAttributes) {
			AttributeType type = attribute.getAttributeType();

			// skip updating content, an association or collection, try find a scalar attribute to update
			if (AttributeType.content.equals(type) || AttributeType.collection.equals(type)
					|| AttributeType.association.equals(type)
					|| AttributeType.inverseOne.equals(type) || AttributeType.inverseMany.equals(type)) {
				continue;
			}

			// if this enum only has 1 value, use a different attribute
			if (AttributeType.enumeration.equals(type)) {
				Enumeration e = (Enumeration) attribute;
				if (e.getValues().size() == 1) {
					continue;
				}
			}

			// try use a persistent attribute if we can
			if (!attribute.isPersistent()) {
				transientAttribute = attribute;
				continue;
			}

			return attribute;
		}

		return transientAttribute != null ? transientAttribute : null;
	}
}
