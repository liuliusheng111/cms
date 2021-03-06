package com.jflyfox.modules.member;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.common.TbStatus;
import com.jflyfox.common.utils.Constants;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.jfinal.component.db.SQLUtils;
import com.jflyfox.modules.enter.TbUserFile;
import com.jflyfox.modules.enter.TbUserImg;
import com.jflyfox.modules.enter.UploadImgService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

@ControllerBind(controllerKey = "/member")
public class MemberController extends BaseProjectController {

	Logger logger =Logger.getLogger(MemberController.class);



	private static final String path = "/pages/member/member_";
//	private static final String IMG_PATH = "/upload/member/"; //图片上传到服务器的相对路径

	public void index() {
		TbMember attr = getModel(TbMember.class);
		setAttr("model", attr);
		render(path + "add.html");
	}

	/**
	 * 上传文件
	 */
	public void uploadFile() {
		HttpServletRequest request = getRequest();
		JSONObject result = UploadImgService.uploadFile("member",request);
		renderJson(result.toString());
	}

	/**
	 * 投稿
	 */
	public void save() {

		JSONObject json = new JSONObject();
		json.put("status", 2);// 失败

		//保存投稿信息
		TbMember model = getModel(TbMember.class);

		//重名检查
		TbMember db = TbMember.dao.findFirstByWhere(" where name= ? and status=0",model.getName());
		if (db!=null){
			json.put("msg","重名,请重新填写");
			renderJson(json.toJSONString());
			return;
		}

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
		userImg.setType(2);
		userImg.save();

		//保存照片,简历等信息
		TbUserFile userFile = new TbUserFile();
		userFile.setUserId(model.getId());
		userFile.setPhoto(getPara("photo"));
		userFile.setResume(getPara("resume"));
		userFile.setStatus(TbStatus.OK.ordinal());
		userFile.save();

		json.put("status", 1);// 成功
		renderJson(json.toJSONString());
	}

	/**
	 * 列表查询
	 */
	public void list() {

		TbMember model = getModelByAttr(TbMember.class);
		SQLUtils sql = new SQLUtils(" from tb_member t" //
				+ " where 1=1 and status!=1 ");
		if (model.getAttrValues().length != 0) {
			// 查询条件
			sql.whereLike("name", model.getStr("name"));
		}

		Page<TbMember> page = TbMember.dao.paginate(getPaginator(), "select t.* ", //
				sql.toString().toString());

		setAttr("page", page);
		setAttr("attr", model);
		render(path + "list.html");
	}

	public void view() {
		TbUserImg model = TbUserImg.dao.findFirst(" select * from tb_user_img " //
				+ " where type=2 and status=0 and user_id= "+getParaToInt());
		setAttr("model", model);

		TbUserFile file = TbUserFile.dao.findFirst(" select * from tb_user_file "
				+ " where  user_id= "+getParaToInt()+" and status=0 ");
		setAttr("model", model);
		setAttr("file", file);
		setAttr("url", Constants.IMAGE_PRIFIX_URL);
		render(path + "view.html");
	}

	/**
	 * 添加
	 */
	public void edit() {
		TbUserImg model = TbUserImg.dao.findFirst(" select * from tb_user_img " //
				+ " where type=2 and user_id= "+getParaToInt());
		setAttr("model", model);
		render(path + "edit.html");
	}

	public void delete() {
		// 日志添加
		TbMember model = new TbMember().findById(getParaToInt());
		model.setStatus(TbStatus.DEL.ordinal());
		model.update();

		TbUserImg userImg = TbUserImg.dao.findFirst(" select id from tb_user_img " //
				+ " where type=2 and user_id= "+getParaToInt());
		if (userImg!=null) {
			userImg.setStatus(TbStatus.DEL.ordinal());
			userImg.update();
		}

		TbUserFile userFile = TbUserFile.dao.findFirst(" select id from tb_user_file " //
				+ " where  user_id= "+getParaToInt());
		if (userFile!=null) {
			userFile.setStatus(TbStatus.DEL.ordinal());
			userFile.update();
		}

		list();
	}

}
