package org.skyve.impl.metadata.view.reference;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.skyve.impl.util.XMLMetaData;
import org.skyve.metadata.controller.ImplicitActionName;

@XmlType(namespace = XMLMetaData.VIEW_NAMESPACE)
@XmlRootElement(namespace = XMLMetaData.VIEW_NAMESPACE)
public class ImplicitActionReference implements Reference {
	private static final long serialVersionUID = 2492612188970314035L;

	private ImplicitActionName implicitActionName;

	public ImplicitActionName getImplicitActionName() {
		return implicitActionName;
	}

	@XmlAttribute(required = true)
	public void setImplicitActionName(ImplicitActionName implicitActionName) {
		this.implicitActionName = implicitActionName;
	}
}
