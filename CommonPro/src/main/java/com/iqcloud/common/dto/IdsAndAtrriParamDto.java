package com.iqcloud.common.dto;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class IdsAndAtrriParamDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3219275638317520073L;

	private List<String> Ids;
	private List<String> fields;
	private List<Integer> intValues1;
	private List<Integer> intValues2;
	private String sortField;
	private Integer sortType;
	private String groupCode;

	private Boolean boolParam1;

	public List<String> getIds() {
		return Ids;
	}

	public void setIds(List<String> ids) {
		Ids = ids;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public List<Integer> getIntValues1() {
		return intValues1;
	}

	public void setIntValues1(List<Integer> intValues1) {
		this.intValues1 = intValues1;
	}

	public List<Integer> getIntValues2() {
		return intValues2;
	}

	public void setIntValues2(List<Integer> intValues2) {
		this.intValues2 = intValues2;
	}

	public Boolean getBoolParam1() {
		return boolParam1;
	}

	public void setBoolParam1(Boolean boolParam1) {
		this.boolParam1 = boolParam1;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
