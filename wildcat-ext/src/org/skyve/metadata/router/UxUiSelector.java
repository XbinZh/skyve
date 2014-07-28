package org.skyve.metadata.router;

import javax.servlet.http.HttpServletRequest;

import org.skyve.wildcat.metadata.repository.router.TaggingUxUiSelector;

public interface UxUiSelector extends TaggingUxUiSelector {
	public String select(HttpServletRequest request);
}
