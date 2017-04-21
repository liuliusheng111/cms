package com.jflyfox.modules.enter;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

@ModelBind(table = "tb_user_img", key = "id")
public class TbUserImg extends BaseProjectModel<TbUserImg> {

	private static final long serialVersionUID = 1L;
	public static final TbUserImg dao = new TbUserImg();

	private String ID = "id"; // 主键
	private String USER_ID = "user_id"; // 用户ID
	private String TYPE = "type"; // 用户分类下1.投稿 2.会员
	private String IMAGE1_ID = "image1_id"; // 第一图片ID
	private String IMAGE2_ID = "image2_id"; //第二图片ID
	private String IMAGE3_ID = "image3_id"; //第三图片ID
	private String STATUS = "status"; //0表示正常，1删除


	public TbUserImg setId(Integer value) {
		set(ID, value);
		return this;
	}

	public Integer getId() {
		return get(ID);
	}

	public TbUserImg setUserId(Integer value) {
		set(USER_ID, value);
		return this;
	}

	public Integer getUserId() {
		return get(USER_ID);
	}

	public TbUserImg setImage1Id(String value) {
		set(IMAGE1_ID, value);
		return this;
	}

	public String getImage1Id() {
		return get(IMAGE1_ID);
	}

	public TbUserImg setImage2Id(String value) {
		set(IMAGE2_ID, value);
		return this;
	}

	public String getImage2Id() {
		return get(IMAGE2_ID);
	}

	public TbUserImg setImage3Id(String value) {
		set(IMAGE3_ID, value);
		return this;
	}

	public String getImage3Id() {
		return get(IMAGE3_ID);
	}


	public TbUserImg setType(Integer value) {
		set(TYPE, value);
		return this;
	}

	public Integer getType() {
		return get(TYPE);
	}

	public TbUserImg setStatus(java.lang.Integer value) {
		set(STATUS, value);
		return this;
	}

	public java.lang.Integer getStatus() {
		return get(STATUS);
	}



}
