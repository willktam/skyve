package org.skyve.wildcat.metadata.module.query;

import org.skyve.metadata.module.Module;
import org.skyve.metadata.module.query.QueryDefinition;

public abstract class QueryDefinitionImpl implements QueryDefinition {
	private static final long serialVersionUID = 1867738351262041832L;

	private Module owningModule;

	private String displayName;

	private String name;

	private String description;

	private String documentation;

	@Override
	public Module getOwningModule() {
		return owningModule;
	}
	
	public void setOwningModule(Module owningModule) {
		this.owningModule = owningModule;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
}