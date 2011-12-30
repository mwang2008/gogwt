package com.gogwt.apps.tracking.data;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Only used for adding fake data for testing.
 * @author michael.wang
 *
 */
public class GFakeLocation extends GLocation {
	private String name;
	private CommonsMultipartFile fileData;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

}
