package com.jflyfox.modules.enter;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.common.Status;
import com.jflyfox.common.service.threadpool.ExecutorProcessPool;
import com.jflyfox.common.utils.*;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.system.file.util.FileUploadUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

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
		model.save();

		//保存图片信息
		TbUserImg userImg = new TbUserImg();
		userImg.setUserId(model.getId());
		userImg.setImage1Id(getPara("imageId1"));
		userImg.setImage2Id(getPara("imageId2"));
		userImg.setImage3Id(getPara("imageId3"));
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
				+ " where 1=1 ");
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
		model.setStatus(Status.DEL.ordinal());
		model.save();


		TbUserImg userImg = TbUserImg.dao.findFirst(" select id from tb_user_img " //
				+ " where type=1 and user_id= "+getParaToInt());
		if (userImg!=null) {
			userImg.setStatus(Status.DEL.ordinal());
			userImg.save();
		}

		list();
	}

}
