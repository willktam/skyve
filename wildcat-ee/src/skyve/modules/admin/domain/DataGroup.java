package modules.admin.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.skyve.CORE;
import org.skyve.impl.domain.AbstractPersistentBean;

/**
 * DataGroup
 * 
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public class DataGroup extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "admin";
	/** @hidden */
	public static final String DOCUMENT_NAME = "DataGroup";

	/** @hidden */
	public static final String namePropertyName = "name";
	/** @hidden */
	public static final String descriptionPropertyName = "description";

	private String name;
	private String description;

	@Override
	@XmlTransient
	public String getBizModule() {
		return DataGroup.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return DataGroup.DOCUMENT_NAME;
	}

	public static DataGroup newInstance() throws Exception {
		return CORE.getUser().getCustomer().getModule(MODULE_NAME).getDocument(CORE.getUser().getCustomer(), DOCUMENT_NAME).newInstance(CORE.getUser());
	}

	@Override
	@XmlTransient
	public String getBizKey() {
		try {
			return org.skyve.util.Binder.formatMessage(org.skyve.CORE.getUser().getCustomer(),
														"{name}",
														this);
		}
		catch (Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof DataGroup) && 
					this.getBizId().equals(((DataGroup) o).getBizId()));
	}

	/**
	 * {@link #name} accessor.
	 **/
	public String getName() {
		return name;
	}

	/**
	 * {@link #name} mutator.
	 * 
	 * @param name	The new value to set.
	 **/
	@XmlElement
	public void setName(String name) {
		preset(namePropertyName, name);
		this.name = name;
	}

	/**
	 * {@link #description} accessor.
	 **/
	public String getDescription() {
		return description;
	}

	/**
	 * {@link #description} mutator.
	 * 
	 * @param description	The new value to set.
	 **/
	@XmlElement
	public void setDescription(String description) {
		preset(descriptionPropertyName, description);
		this.description = description;
	}
}
