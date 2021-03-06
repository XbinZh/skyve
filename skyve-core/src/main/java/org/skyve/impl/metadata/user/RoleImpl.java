package org.skyve.impl.metadata.user;

import java.util.ArrayList;
import java.util.List;

import org.skyve.impl.metadata.repository.module.ContentPermission;
import org.skyve.impl.metadata.repository.module.ContentRestriction;
import org.skyve.metadata.module.Module;
import org.skyve.metadata.user.Role;
import org.skyve.impl.metadata.user.Privilege;

public class RoleImpl implements Role {
	/**
	 * For Serialization
	 */
	private static final long serialVersionUID = -2841351233211789543L;

	private Module owningModule;
	private String name;
	private String description;
	private List<Privilege> privileges = new ArrayList<>();
	private List<ContentRestriction> contentRestrictions = new ArrayList<>();
	private List<ContentPermission> contentPermissions = new ArrayList<>();
	private String documentation;
	
	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Privilege> getPrivileges() {
		return privileges;
	}

	public List<ContentRestriction> getContentRestrictions() {
		return contentRestrictions;
	}

	public List<ContentPermission> getContentPermissions() {
		return contentPermissions;
	}

	@Override
	public Module getOwningModule() {
		return owningModule;
	}

	public void setOwningModule(Module owningModule) {
		this.owningModule = owningModule;
	}

	@Override
	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
}
