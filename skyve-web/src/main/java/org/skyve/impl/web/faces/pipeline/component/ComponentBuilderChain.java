package org.skyve.impl.web.faces.pipeline.component;

import java.util.List;

import javax.faces.component.UIComponent;

import org.skyve.domain.Bean;
import org.skyve.domain.types.converters.Converter;
import org.skyve.domain.types.converters.Format;
import org.skyve.impl.metadata.view.container.Tab;
import org.skyve.impl.metadata.view.container.TabPane;
import org.skyve.impl.metadata.view.widget.Blurb;
import org.skyve.impl.metadata.view.widget.Button;
import org.skyve.impl.metadata.view.widget.DynamicImage;
import org.skyve.impl.metadata.view.widget.Link;
import org.skyve.impl.metadata.view.widget.Spacer;
import org.skyve.impl.metadata.view.widget.StaticImage;
import org.skyve.impl.metadata.view.widget.bound.Label;
import org.skyve.impl.metadata.view.widget.bound.input.CheckBox;
import org.skyve.impl.metadata.view.widget.bound.input.ColourPicker;
import org.skyve.impl.metadata.view.widget.bound.input.Combo;
import org.skyve.impl.metadata.view.widget.bound.input.ContentImage;
import org.skyve.impl.metadata.view.widget.bound.input.ContentLink;
import org.skyve.impl.metadata.view.widget.bound.input.HTML;
import org.skyve.impl.metadata.view.widget.bound.input.ListMembership;
import org.skyve.impl.metadata.view.widget.bound.input.LookupDescription;
import org.skyve.impl.metadata.view.widget.bound.input.Password;
import org.skyve.impl.metadata.view.widget.bound.input.Radio;
import org.skyve.impl.metadata.view.widget.bound.input.RichText;
import org.skyve.impl.metadata.view.widget.bound.input.Spinner;
import org.skyve.impl.metadata.view.widget.bound.input.TextArea;
import org.skyve.impl.metadata.view.widget.bound.input.TextField;
import org.skyve.impl.metadata.view.widget.bound.tabular.AbstractDataWidget;
import org.skyve.impl.metadata.view.widget.bound.tabular.DataGrid;
import org.skyve.impl.metadata.view.widget.bound.tabular.DataGridBoundColumn;
import org.skyve.impl.metadata.view.widget.bound.tabular.DataGridContainerColumn;
import org.skyve.impl.metadata.view.widget.bound.tabular.DataRepeater;
import org.skyve.impl.metadata.view.widget.bound.tabular.ListGrid;
import org.skyve.impl.web.UserAgentType;
import org.skyve.impl.web.faces.beans.FacesView;
import org.skyve.metadata.controller.ImplicitActionName;
import org.skyve.metadata.module.query.QueryDefinition;
import org.skyve.metadata.view.Action;
import org.skyve.metadata.view.model.list.ListModel;
import org.skyve.metadata.view.widget.bound.FilterParameter;

/**
 * Delegates to a list of other builders that actually produce the components.
 */
public class ComponentBuilderChain extends ComponentBuilder {
	private ComponentBuilder[] builders;
	
	public ComponentBuilderChain(ComponentBuilder... builders) {
		this.builders = builders;
	}
	
	@Override
	public void setManagedBeanName(String managedBeanName) {
		// Set the state of the chain too so that utility methods in AbstractFacesBuilder can work
		super.setManagedBeanName(managedBeanName);
		// Now set the state on all builders in the chain
		for (ComponentBuilder builder : builders) {
			builder.setManagedBeanName(managedBeanName);
		}
	}
	
	@Override
	public void setSAILManagedBean(FacesView<?> managedBean) {
		// Set the state of the chain too so that utility methods in AbstractFacesBuilder can work
		super.setSAILManagedBean(managedBean);
		// Now set the state on all builders in the chain
		for (ComponentBuilder builder : builders) {
			builder.setSAILManagedBean(managedBean);
		}
	}

	@Override
	public void setProcess(String process) {
		// Set the state of the chain too so that utility methods in AbstractFacesBuilder can work
		super.setProcess(process);
		// Now set the state on all builders in the chain
		for (ComponentBuilder builder : builders) {
			builder.setProcess(process);
		}
	}

	@Override
	public void setUpdate(String update) {
		// Set the state of the chain too so that utility methods in AbstractFacesBuilder can work
		super.setUpdate(update);
		// Now set the state on all builders in the chain
		for (ComponentBuilder builder : builders) {
			builder.setUpdate(update);
		}
	}

	@Override
	public void setUserAgentType(UserAgentType userAgentType) {
		// Set the state of the chain too so that utility methods in AbstractFacesBuilder can work
		super.setUserAgentType(userAgentType);
		// Now set the state on all builders in the chain
		for (ComponentBuilder builder : builders) {
			builder.setUserAgentType(userAgentType);
		}
	}
	
	@Override
	public UIComponent view(UIComponent component, String invisibleConditionName) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.view(result, invisibleConditionName);
		}
		return result;
	}

	@Override
	public List<UIComponent> toolbars(List<UIComponent> components, String widgetId) {
		List<UIComponent> result = components;
		for (ComponentBuilder builder : builders) {
			result = builder.toolbars(result, widgetId);
		}
		return result;
	}

	@Override
	public UIComponent tabPane(UIComponent component, TabPane tabPane) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.tabPane(result, tabPane);
		}
		return result;
	}

	@Override
	public UIComponent tab(UIComponent component, Tab tab) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.tab(result, tab);
		}
		return result;
	}

	@Override
	public UIComponent border(UIComponent component, String title, String invisibileConditionName, Integer pixelWidth) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.border(result, title, invisibileConditionName, pixelWidth);
		}
		return result;
	}

	@Override
	public UIComponent label(UIComponent component, String value) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.label(result, value);
		}
		return result;
	}

	@Override
	public UIComponent spacer(UIComponent component, Spacer spacer) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.spacer(result, spacer);
		}
		return result;
	}

	@Override
	public UIComponent actionButton(UIComponent component, 
										String listBinding,
										String listVar,
										Button button,
										Action action) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.actionButton(result, listBinding, listVar, button, action);
		}
		return result;
	}

	@Override
	public UIComponent reportButton(UIComponent component, Button button, Action action) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.reportButton(result, button, action);
		}
		return result;
	}

	@Override
	public UIComponent downloadButton(UIComponent component,
										Button button,
										Action action,
										String moduleName,
										String documentName) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.downloadButton(result, button, action, moduleName, documentName);
		}
		return result;
	}

	@Override
	public UIComponent staticImage(UIComponent component, StaticImage image) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.staticImage(result, image);
		}
		return result;
	}

	@Override
	public UIComponent dynamicImage(UIComponent component, DynamicImage image, String moduleName, String documentName) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.dynamicImage(result, image, moduleName, documentName);
		}
		return result;
	}

	@Override
	public UIComponent blurb(UIComponent component, String listVar, String value, String binding, Blurb blurb) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.blurb(result, listVar, value, binding, blurb);
		}
		return result;
	}

	@Override
	public UIComponent label(UIComponent component, String listVar, String value, String binding, Label label) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.label(result, listVar, value, binding, label);
		}
		return result;
	}

	@Override
	public UIComponent dataGrid(UIComponent component, String listVar, DataGrid grid) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.dataGrid(result, listVar, grid);
		}
		return result;
	}

	@Override
	public UIComponent dataRepeater(UIComponent component, String listVar, DataRepeater repeater) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.dataRepeater(result, listVar, repeater);
		}
		return result;
	}

	@Override
	public UIComponent addDataGridBoundColumn(UIComponent component,
												UIComponent current,
												AbstractDataWidget widget,
												DataGridBoundColumn column,
												String listVar,
												String columnTitle,
												String columnBinding,
												StringBuilder gridColumnExpression) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.addDataGridBoundColumn(result, 
														current,
														widget,
														column,
														listVar,
														columnTitle,
														columnBinding,
														gridColumnExpression);
		}
		return result;
	}

	@Override
	public UIComponent addedDataGridBoundColumn(UIComponent component, UIComponent current) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.addedDataGridBoundColumn(result, current);
		}
		return result;
	}

	@Override
	public UIComponent addDataGridContainerColumn(UIComponent component,
													UIComponent current,
													AbstractDataWidget widget,
													DataGridContainerColumn column) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.addDataGridContainerColumn(result, current, widget, column);
		}
		return result;
	}

	@Override
	public UIComponent addedDataGridContainerColumn(UIComponent component, UIComponent current) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.addedDataGridContainerColumn(result, current);
		}
		return result;
	}

	@Override
	public UIComponent addDataGridActionColumn(UIComponent component,
												UIComponent current,
												DataGrid grid,
												String listVar,
												String gridColumnExpression,
												String singluarDocumentAlias,
												boolean inline) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.addDataGridActionColumn(result,
														current,
														grid,
														listVar,
														gridColumnExpression,
														singluarDocumentAlias,
														inline);
		}
		return result;
	}

	@Override
	public UIComponent listGrid(UIComponent component,
									String modelDocumentName,
									String modelName,
									ListModel<? extends Bean> model,
									ListGrid listGrid,
									boolean canCreateDocument) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.listGrid(result,
										modelDocumentName,
										modelName,
										model,
										listGrid,
										canCreateDocument);
		}
		return result;
	}

	@Override
	public UIComponent listRepeater(UIComponent component,
										String modelDocumentName,
										String modelName,
										ListModel<? extends Bean> model,
										List<FilterParameter> filterParameters,
										String title,
										boolean showColumnHeaders,
										boolean showGrid) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.listRepeater(result,
											modelDocumentName,
											modelName,
											model,
											filterParameters,
											title,
											showColumnHeaders,
											showGrid);
		}
		return result;
	}

	@Override
	public UIComponent listMembership(UIComponent component, ListMembership membership) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.listMembership(result, membership);
		}
		return result;
	}

	@Override
	public UIComponent checkBox(UIComponent component,
									String listVar,
									CheckBox checkBox,
									String title,
									boolean required) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.checkBox(result, listVar, checkBox, title, required);
		}
		return result;
	}

	@Override
	public UIComponent colourPicker(UIComponent component,
										String listVar,
										ColourPicker colour,
										String title,
										boolean required) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.colourPicker(result, listVar, colour, title, required);
		}
		return result;
	}

	@Override
	public UIComponent combo(UIComponent component, String listVar, Combo combo, String title, boolean required) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.combo(result, listVar, combo, title, required);
		}
		return result;
	}

	@Override
	public UIComponent contentImage(UIComponent component,
										String listVar,
										ContentImage image,
										String title,
										boolean required) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.contentImage(result, listVar, image, title, required);
		}
		return result;
	}

	@Override
	public UIComponent contentLink(UIComponent component,
									String listVar,
									ContentLink link,
									String title,
									boolean required) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.contentLink(result, listVar, link, title, required);
		}
		return result;
	}

	@Override
	public UIComponent html(UIComponent component, String listVar, HTML html, String title, boolean required) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.html(result, listVar, html, title, required);
		}
		return result;
	}

	@Override
	public UIComponent lookupDescription(UIComponent component,
											String listVar,
											LookupDescription lookup,
											String title,
											boolean required,
											String displayBinding,
											QueryDefinition query) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.lookupDescription(result, listVar, lookup, title, required, displayBinding, query);
		}
		return result;
	}

	@Override
	public UIComponent password(UIComponent component,
									String listVar,
									Password password,
									String title,
									boolean required) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.password(result, listVar, password, title, required);
		}
		return result;
	}

	@Override
	public UIComponent radio(UIComponent component, String listVar, Radio radio, String title, boolean required) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.radio(result, listVar, radio, title, required);
		}
		return result;
	}

	@Override
	public UIComponent richText(UIComponent component, String listVar, RichText text, String title, boolean required) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.richText(result, listVar, text, title, required);
		}
		return result;
	}

	@Override
	public UIComponent spinner(UIComponent component, String listVar, Spinner spinner, String title, boolean required) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.spinner(result, listVar, spinner, title, required);
		}
		return result;
	}

	@Override
	public UIComponent textArea(UIComponent component,
									String listVar,
									TextArea text,
									String title,
									boolean required,
									Integer length) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.textArea(result, listVar, text, title, required, length);
		}
		return result;
	}

	@Override
	public UIComponent text(UIComponent component,
								String listVar,
								TextField text,
								String title,
								boolean required,
								Integer length,
								Converter<?> converter,
								Format<?> format,
								javax.faces.convert.Converter facesConverter) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.text(result, listVar, text, title, required, length, converter, format, facesConverter);
		}
		return result;
	}

	@Override
	public UIComponent actionLink(UIComponent component,
									String listBinding,
									String listVar,
									Link link,
									String actionName) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.actionLink(result, listBinding, listVar, link, actionName);
		}
		return result;
	}

	@Override
	public UIComponent actionLink(UIComponent component,
								  String listBinding,
								  String listVar,
								  Link link,
								  Action action) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.actionLink(result, listBinding, listVar, link, action);
		}
		return result;
	}

	@Override
	public UIComponent report(UIComponent component, Action action) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.report(result, action);
		}
		return result;
	}

	@Override
	public UIComponent download(UIComponent component, Action action, String moduleName, String documentName) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.download(result, action, moduleName, documentName);
		}
		return result;
	}

	@Override
	public UIComponent action(UIComponent component,
								String listBinding,
								String listVar,
								Action action,
								ImplicitActionName name,
								String title) {
		UIComponent result = component;
		for (ComponentBuilder builder : builders) {
			result = builder.action(result, listBinding, listVar, action, name, title);
		}
		return result;
	}
}
