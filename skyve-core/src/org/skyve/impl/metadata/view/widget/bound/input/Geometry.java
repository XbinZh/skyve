package org.skyve.impl.metadata.view.widget.bound.input;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.skyve.impl.metadata.view.AbsoluteWidth;
import org.skyve.impl.util.XMLMetaData;
import org.skyve.impl.metadata.view.widget.bound.input.ChangeableInputWidget;

/**
 * Editor for a geometry field
 */
@XmlType(namespace = XMLMetaData.VIEW_NAMESPACE)
@XmlRootElement(namespace = XMLMetaData.VIEW_NAMESPACE)
public class Geometry extends ChangeableInputWidget implements AbsoluteWidth {
	private static final long serialVersionUID = 7902784327466913291L;
	
	private Integer pixelWidth;
	
	@Override
	public Integer getPixelWidth() {
		return pixelWidth;
	}

	@Override
	@XmlAttribute(required = false)
	public void setPixelWidth(Integer pixelWidth) {
		this.pixelWidth = pixelWidth;
	}
}
