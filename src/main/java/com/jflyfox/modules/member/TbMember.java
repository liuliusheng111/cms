package com.jflyfox.modules.member;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

@ModelBind(table = "tb_member", key = "id")
public class TbMember extends BaseProjectModel<TbMember> {

	private static final long serialVersionUID = 1L;
	public static final TbMember dao = new TbMember();

	private String ID = "id"; // 主键
	private String NAME = "name"; // 姓名
	private String PHONE = "phone"; // 电话
	private String ADDRESS = "address"; //地址
	private String AGE = "age"; // 年龄
	private String GENDER = "gender"; // 性别
	private String CREATE_TIME = "create_time"; // 创建时间
	private String STATUS = "status"; //0正常，1 删除
	private String IDCARD = "idcard"; //身份证号码
	private String TYPE = "type"; //类别 少儿child,成人grownup




	public TbMember setId(Integer value) {
		set(ID, value);
		return this;
	}

	public Integer getId() {
		return get(ID);
	}

	public TbMember setName(String value) {
		set(NAME, value);
		return this;
	}

	public String getName() {
		return get(NAME);
	}

	public TbMember setAddress(String value) {
		set(ADDRESS, value);
		return this;
	}

	public String getAddress() {
		return get(ADDRESS);
	}

	public TbMember setPhone(String value) {
		set(PHONE, value);
		return this;
	}

	public String getPhone() {
		return get(PHONE);
	}


	public TbMember setAge(Integer value) {
		set(AGE, value);
		return this;
	}

	public Integer getAge() {
		return get(AGE);
	}


	public TbMember setGender(Integer value) {
		set(GENDER, value);
		return this;
	}

	public Integer getGender() {
		return get(GENDER);
	}

	public TbMember setCreateTime(String value) {
		set(CREATE_TIME, value);
		return this;
	}

	public String getCreateTime() {
		return get(CREATE_TIME);
	}

	public TbMember setStatus(java.lang.Integer value) {
		set(STATUS, value);
		return this;
	}

	public java.lang.Integer getStatus() {
		return get(STATUS);
	}

	public TbMember setType(String value) {
		set(TYPE, value);
		return this;
	}

	public String getType() {
		return get(TYPE);
	}

	public TbMember setIdcard(String value) {
		set(IDCARD, value);
		return this;
	}

	public String getIdcard() {
		return get(IDCARD);
	}


}
