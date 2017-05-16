package com.jflyfox.modules.enter;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.common.TbStatus;
import com.jflyfox.common.utils.Constants;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;

import javax.servlet.http.HttpServletRequest;

@ControllerBind(controllerKey = "/enter")
public class EnterController extends BaseProjectController {




	private static final String path = "/pages/enter/enter_";
	private static final String IMAGE_SUB_PATH = "enter"; //图片上传到服务器的相对路径

	public void index() {
		TbEnter attr = getModel(TbEnter.class);
		setAttr("model", attr);
		render(path + "add.html");
	}

	/**
	 * 上传文件
	 */
	public void uploadFile() {
		HttpServletRequest request = getRequest();
		renderJson(UploadImgService.uploadFile(IMAGE_SUB_PATH,request).toString());

	}



	/**
	 * 投稿
	 */
	public void save() {

		JSONObject json = new JSONObject();
		json.put("status", 2);// 失败

		//保存投稿信息
		TbEnter model = getModel(TbEnter.class);
		model.setCreateTime(getNow());
		model.setStatus(TbStatus.OK.ordinal());
		model.save();

		//保存图片信息
		TbUserImg userImg = new TbUserImg();
		userImg.setUserId(model.getId());
		userImg.setImage1Id(getPara("imageId1"));
		userImg.setImage2Id(getPara("imageId2"));
		userImg.setImage3Id(getPara("imageId3"));
		userImg.setStatus(TbStatus.OK.ordinal());
		userImg.setType(1);
		userImg.save();
		json.put("status", 1);// 成功
		renderJson(json.toJSONString());
	}

	/**
	 * 列表查询
	 */
	public void list() {

		TbEnter model = getModelByAttr(TbEnter.class);
		SQLUtils sql = new SQLUtils(" from tb_enter t " //
				+ " where 1=1 and status!=1 ");
		if (model.getAttrValues().length != 0) {
			// 查询条件
			sql.whereLike("name", model.getStr("name"));
		}

		Page<TbEnter> page = TbEnter.dao.paginate(getPaginator(), "select t.* ", //
				sql.toString().toString());

		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}

	public void view() {
		TbUserImg model = TbUserImg.dao.findFirst(" select * from tb_user_img " //
				+ " where type=1 and user_id= "+getParaToInt());
		setAttr("model", model);
		setAttr("url", Constants.IMAGE_PRIFIX_URL);
		render(path + "view.html");
	}

	/**
	 * 添加
	 */
	public void edit() {
		TbUserImg model = TbUserImg.dao.findFirst(" select * from tb_user_img " //
				+ " where type=1 and user_id= "+getParaToInt());
		setAttr("model", model);
		render(path + "edit.html");
	}

	public void delete() {
		// 日志添加
		TbEnter model = TbEnter.dao.findById(getParaToInt());
		model.setStatus(TbStatus.DEL.ordinal());
		model.update();


		TbUserImg userImg = TbUserImg.dao.findFirst(" select id from tb_user_img " //
				+ " where type=1 and user_id= "+getParaToInt());
		if (userImg!=null) {
			userImg.setStatus(TbStatus.DEL.ordinal());
			userImg.update();
		}

		list();
	}

}
