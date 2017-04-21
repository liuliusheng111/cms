package com.jflyfox.modules.enter;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.common.service.threadpool.ExecutorProcessPool;
import com.jflyfox.common.utils.*;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.modules.admin.article.TbArticle;
import com.jflyfox.modules.admin.comment.TbComment;
import com.jflyfox.system.file.util.FileUploadUtils;
import com.jflyfox.util.StrUtils;
import com.qiniu.util.Auth;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpRequest;
import org.apache.log4j.Logger;
import org.omg.CORBA.Request;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@ControllerBind(controllerKey = "/enter")
public class EnterController extends BaseProjectController {

	Logger logger =Logger.getLogger(EnterController.class);



	private static final String path = "/pages/enter/enter_";
	private static final String IMG_PATH = "/upload/enter/"; //图片上传到服务器的相对路径

	public void index() {
		TbEnter attr = getModel(TbEnter.class);
		setAttr("model", attr);
		render(path + "add.html");
	}

	/**
	 * 上传文件
	 */
	public void uploadFile() {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		JSONObject result = new JSONObject();
		result.put("status",2);
		try {
			HttpServletRequest request = getRequest();
			List<FileItem> items = upload.parseRequest(request);
			if (items==null||items.isEmpty()){
				renderJson(result);
				return;
			}
			FileItem fileItem = items.get(0);
			final byte[] bytes = fileItem.get();
			String pictureName =fileItem.getName();
			if (pictureName==null){
				renderJson(result);
				return;
			}
			final String imageName = IdUtils.getIdByUUId()+pictureName.substring(pictureName.lastIndexOf("."));

			//异步上传到七牛
			ExecutorProcessPool.getInstance().execute(new Runnable() {
				@Override
				public void run() {
					QiniuUtils.upload2Qiniu(bytes,imageName);
				}
			});

			//上传到本地
			String webRootPath = FileUploadUtils.getRootPath() ;
			String storePath = webRootPath + IMG_PATH;
			String path = storePath + imageName;
			fileItem.write(new File(path));

			result.put("url",request.getContextPath()+IMG_PATH+imageName);
			result.put("name",imageName);
			result.put("status",1);
			renderJson(result.toJSONString());
		}catch (Exception e){
			logger.error("upload file errer:"+path +e.getMessage());
			renderJson(result);
		}
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
		TbEnterImg enterImg = new TbEnterImg();
		enterImg.setEnterId(model.getId());
		enterImg.setImage1Id(getPara("imageId1"));
		enterImg.setImage2Id(getPara("imageId2"));
		enterImg.setImage3Id(getPara("imageId3"));
		enterImg.save();
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
		TbEnterImg model = TbEnterImg.dao.findFirst(" select * from tb_enter_img " //
				+ " where enter_id= "+getParaToInt());
		setAttr("model", model);
		render(path + "view.html");
	}

	/**
	 * 添加
	 */
	public void edit() {
		TbEnterImg model = TbEnterImg.dao.findFirst(" select * from tb_enter_img " //
				+ " where enter_id= "+getParaToInt());
		setAttr("model", model);
		render(path + "edit.html");
	}

	public void delete() {
		// 日志添加
		TbEnter model = new TbEnter();
		Integer userid = getSessionUser().getUserID();
		HttpServletRequest request = this.getRequest();
		String now = getNow();
		model.put("update_id", userid);
		model.put("update_time", now);
		model.deleteById(getParaToInt());

		TbEnterImg enterImg = TbEnterImg.dao.findFirst(" select id from tb_enter_img " //
				+ " where enter_id= "+getParaToInt());
		if (enterImg!=null) {
			enterImg.delete();
		}

		list();
	}

}
