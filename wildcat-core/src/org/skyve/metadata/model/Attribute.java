package org.skyve.metadata.model;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

import org.skyve.domain.Bean;
import org.skyve.domain.types.DateOnly;
import org.skyve.domain.types.DateTime;
import org.skyve.domain.types.Decimal10;
import org.skyve.domain.types.Decimal2;
import org.skyve.domain.types.Decimal5;
import org.skyve.domain.types.TimeOnly;
import org.skyve.domain.types.Timestamp;
import org.skyve.metadata.NamedMetaData;
import org.skyve.metadata.model.document.DomainType;
import org.skyve.wildcat.metadata.view.widget.bound.input.InputWidget;

import com.vividsolutions.jts.geom.Geometry;

/**
 * 
 */
public interface Attribute extends NamedMetaData {
	/**
	 * 
	 */
	@XmlType
	public enum AttributeType {
		text(String.class), 
		date(DateOnly.class), 
		time(TimeOnly.class), 
		dateTime(DateTime.class), 
		timestamp(Timestamp.class), 
		integer(Integer.class), 
		longInteger(Long.class), 
		decimal2(Decimal2.class), 
		decimal5(Decimal5.class), 
		decimal10(Decimal10.class), 
		bool(Boolean.class),
		enumeration(Enum.class),
		memo(String.class), 
		markup(String.class),
		colour(String.class), 
		content(String.class), 
		association(Bean.class), 
		collection(List.class),
		inverse(List.class),
		geometry(Geometry.class),
		id(String.class);

		private Class<?> implementingType;

		/**
		 * 
		 * @param implementingType
		 */
		private AttributeType(Class<?> implementingType) {
			this.implementingType = implementingType;
		}

		/**
		 * 
		 * @return
		 */
		public Class<?> getImplementingType() {
			return implementingType;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDisplayName();
	
	/**
	 * 
	 * @return
	 */
	public String getShortDescription();
	
	/**
	 * 
	 * @return
	 */
	public AttributeType getAttributeType();

	/**
	 * 
	 * @return
	 */
	public boolean isPersistent();
	
	/**
	 * 
	 * @return
	 */
	public boolean isRequired();
	
	/**
	 * 
	 * @return
	 */
	public DomainType getDomainType();

	/**
	 * 
	 * @return
	 */
	public boolean isDeprecated();
	
	/**
	 * 
	 * @return
	 */
	public boolean isTrackChanges();

	/**
	 * 
	 * @return
	 */
	public InputWidget getDefaultInputWidget();
	
	/**
	 * 
	 * @return
	 */
	public String getDocumentation();
}
