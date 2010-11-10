package com.gogwt.framework.server.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class ParamTag extends TagSupport {

	private String name;
	private String value;

	/**
	 * Process the tag.
	 */
	public int doStartTag() throws JspException {
		Tag parentTag = getParent();

		if (!(parentTag instanceof NameValueTag)) {
			throw new JspException(
					"param will not working without parent tag.");
		}

		NameValueTag tag = (NameValueTag) parentTag;

		tag.addParam(name, value);

		return SKIP_BODY;

	}

	/**
	 * release
	 */
	public void release() {
		super.release();
		setName(null);
		setValue(null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
