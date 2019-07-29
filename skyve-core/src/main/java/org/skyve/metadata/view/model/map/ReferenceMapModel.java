package org.skyve.metadata.view.model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.locationtech.jts.geom.Envelope;
import org.skyve.domain.Bean;
import org.skyve.util.Binder;

/**
 * A class to extend to make a quick model based on a collection or an association within the edited bean.
 * The referenceBinding can be compound.
 * 
 * @author mike
 *
 * @param <T>	The type of the bean under edit.
 */
public class ReferenceMapModel<T extends Bean> extends DefaultMapModel<T> {
	private static final long serialVersionUID = -6315233078102862916L;

	private String referenceBinding;

	public ReferenceMapModel(String referenceBinding) {
		this.referenceBinding = referenceBinding;
	}

	@Override
	public MapResult getResult(Envelope mapExtents) throws Exception {
		List<MapItem> items = Collections.emptyList();
		Object value = Binder.get(getBean(), referenceBinding);
		if (value instanceof List) {
			@SuppressWarnings("unchecked")
			List<Bean> collection = (List<Bean>) value;
			items = new ArrayList<>(collection.size());
			for (Bean element : collection) {
				addItem(element, items, mapExtents);
			}
		}
		else if (value instanceof Bean) {
			items = new ArrayList<>(1);
			addItem((Bean) value, items, mapExtents);
		}
		
		return new MapResult(items, null);
	}
}