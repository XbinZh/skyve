package org.skyve.wildcat.metadata.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.skyve.metadata.model.Attribute;
import org.skyve.metadata.model.Extends;
import org.skyve.metadata.model.Persistent;
import org.skyve.wildcat.metadata.AbstractMetaDataMap;

public abstract class Model extends AbstractMetaDataMap implements org.skyve.metadata.model.Model {
	/**
	 * For Serialization
	 */
	private static final long serialVersionUID = -9075615768687125545L;

	private String owningModuleName;

	/**
	 * The list of attributes for this model in the order they are added.
	 */
	private List<Attribute> attributes = new ArrayList<>();
	private String singularAlias;
	private Persistent persistent;
	private Extends inherits;
	private String pluralAlias;
	private String icon16x16RelativeFileName;
	private String icon32x32RelativeFileName;
	private String name;
	private String shortDescription;

	@Override
	public String getOwningModuleName() {
		return owningModuleName;
	}

	public void setOwningModuleName(String owningModuleName) {
		this.owningModuleName = owningModuleName;
	}

	@Override
	public Attribute getAttribute(@SuppressWarnings("hiding") String name) {
		return (Attribute) getMetaData(name);
	}

	public void putAttribute(Attribute attribute) {
		putMetaData(attribute.getName(), attribute);
		attributes.add(attribute);
	}

	@Override
	public List<? extends Attribute> getAttributes() {
		return Collections.unmodifiableList(attributes);
	}

	@Override
	public String getPluralAlias() {
		return pluralAlias;
	}

	public void setPluralAlias(String pluralAlias) {
		this.pluralAlias = pluralAlias;
	}

	@Override
	public String getSingularAlias() {
		return singularAlias;
	}

	public void setSingularAlias(String singularAlias) {
		this.singularAlias = singularAlias;
	}

	@Override
	public String getIcon16x16RelativeFileName() {
		return icon16x16RelativeFileName;
	}

	public void setIcon16x16RelativeFileName(String icon16x16RelativeFileName) {
		this.icon16x16RelativeFileName = icon16x16RelativeFileName;
	}

	@Override
	public String getIcon32x32RelativeFileName() {
		return icon32x32RelativeFileName;
	}

	public void setIcon32x32RelativeFileName(String icon32x32RelativeFileName) {
		this.icon32x32RelativeFileName = icon32x32RelativeFileName;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Override
	public Persistent getPersistent() {
		return persistent;
	}

	public void setPersistent(Persistent persistent) {
		this.persistent = persistent;
	}

	@Override
	public Extends getExtends() {
		return inherits;
	}

	public void setExtends(Extends inherits) {
		this.inherits = inherits;
	}
}
