package com.jflyfox.modules.enter;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

@ModelBind(table = "tb_enter_img", key = "id")
public class TbEnterImg extends BaseProjectModel<TbEnterImg> {

	private static final long serialVersionUID = 1L;
	public static final TbEnterImg dao = new TbEnterImg();

	private String ID = "id"; // 主键
	private String ENTER_ID = "enter_id"; // 投稿人ID
	private String IMAGE1_ID = "image1_id"; // 第一图片ID
	private String IMAGE2_ID = "image2_id"; //第二图片ID
	private String IMAGE3_ID = "image3_id"; //第三图片ID

	public TbEnterImg setId(Integer value) {
		set(ID, value);
		return this;
	}

	public Integer getId() {
		return get(ID);
	}

	public TbEnterImg setEnterId(Integer value) {
		set(ENTER_ID, value);
		return this;
	}

	public Integer getEnterId() {
		return get(ENTER_ID);
	}

	public TbEnterImg setImage1Id(String value) {
		set(IMAGE1_ID, value);
		return this;
	}

	public String getImage1Id() {
		return get(IMAGE1_ID);
	}

	public TbEnterImg setImage2Id(String value) {
		set(IMAGE2_ID, value);
		return this;
	}

	public String getImage2Id() {
		return get(IMAGE2_ID);
	}

	public TbEnterImg setImage3Id(String value) {
		set(IMAGE3_ID, value);
		return this;
	}

	public String getImage3Id() {
		return get(IMAGE3_ID);
	}

}
