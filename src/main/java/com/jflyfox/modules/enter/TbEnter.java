package com.jflyfox.modules.enter;

import com.jflyfox.jfinal.component.annotation.ModelBind;
import com.jflyfox.component.base.BaseProjectModel;

@ModelBind(table = "tb_enter", key = "id")
public class TbEnter extends BaseProjectModel<TbEnter> {

	private static final long serialVersionUID = 1L;
	public static final TbEnter dao = new TbEnter();

	private String ID = "id"; // 主键
	private String NAME = "name"; // 姓名
	private String PHONE = "phone"; // 电话
	private String ADDRESS = "address"; //地址
	private String AGE = "age"; // 年龄
	private String GENDER = "gender"; // 性别
	private String CREATE_TIME = "create_time"; // 创建时间
	private String STATUS = "status"; //0表示正常，1删除



	public TbEnter setId(java.lang.Integer value) {
		set(ID, value);
		return this;
	}

	public java.lang.Integer getId() {
		return get(ID);
	}

	public TbEnter setName(java.lang.String value) {
		set(NAME, value);
		return this;
	}

	public java.lang.String getName() {
		return get(NAME);
	}

	public TbEnter setAddress(java.lang.String value) {
		set(ADDRESS, value);
		return this;
	}

	public java.lang.String getAddress() {
		return get(ADDRESS);
	}

	public TbEnter setPhone(java.lang.String value) {
		set(PHONE, value);
		return this;
	}

	public java.lang.String getPhone() {
		return get(PHONE);
	}


	public TbEnter setAge(java.lang.Integer value) {
		set(AGE, value);
		return this;
	}

	public java.lang.Integer getAge() {
		return get(AGE);
	}


	public TbEnter setGender(java.lang.Integer value) {
		set(GENDER, value);
		return this;
	}

	public java.lang.Integer getGender() {
		return get(GENDER);
	}

	public TbEnter setCreateTime(java.lang.String value) {
		set(CREATE_TIME, value);
		return this;
	}

	public java.lang.String getCreateTime() {
		return get(CREATE_TIME);
	}

	public TbEnter setStatus(java.lang.Integer value) {
		set(STATUS, value);
		return this;
	}

	public java.lang.Integer getStatus() {
		return get(STATUS);
	}


}
