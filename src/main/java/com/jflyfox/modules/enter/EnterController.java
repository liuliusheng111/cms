package com.jflyfox.modules.enter;

import com.alibaba.fastjson.JSONObject;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.modules.admin.image.model.TbImage;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerBind(controllerKey = "/enter")
public class EnterController extends BaseProjectController {

	Logger logger =Logger.getLogger(EnterController.class);

	//设置好账号的ACCESS_KEY和SECRET_KEY
	private static final String ACCESS_KEY = "btdWf0hTxxlamaZeKKB3bWslQZzfTqvaJhTjepBe";
	private static final String SECRET_KEY = "8tpn4xlgdCYXpit3Qo7PcoASw82jmigzs9qTc6BG";
	//要上传的空间
	private static final String BUCKET_NAME = "ybsf-image";

	private static final String path = "/pages/enter/";

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
		try {
			HttpServletRequest request = getRequest();
			List<FileItem> items = upload.parseRequest(request);
			for (Object object : items) {
				FileItem fileItem = (FileItem) object;
				//上传七牛
				Zone z = Zone.autoZone();
				Configuration c = new Configuration(z);
				//创建上传对象
				UploadManager uploadManager = new UploadManager(c);
				try {
					String key = "enter/aa"+fileItem.getName();
					uploadManager.put(fileItem.get(),key,getUpToken());
					//调用put方法上传
					Response res = uploadManager.put(fileItem.get(), key, getUpToken());
					//打印返回的信息
					System.out.println(res.bodyString());
				} catch (QiniuException e) {
					Response r = e.response;
					//响应的文本信息
					System.out.println(r.bodyString());
				}

				//上传到本地
//				if (fileItem.isFormField()) {
//					param.put(fileItem.getFieldName(),
//							fileItem.getString("utf-8"));// 如果你页面编码是utf-8的
//					System.out.println("4: param.get(fileItem.getFieldName())==="
//							+ param.get(fileItem.getFieldName()));
//				} else {
//
//					String picturename =fileItem.getName();
//
//					String path = "/tmp/" + picturename;
//					System.out.println("======================="+path+"==========");
//					fileItem.write(new File(path));
//				}
			}
			renderText("ok");
		}catch (Exception e){
			logger.error("upload file errer:"+path +e.getMessage());
			renderText("error");
		}
	}

	/**
	 * 投稿
	 */
	public void save() {

		JSONObject json = new JSONObject();
		json.put("status", 2);// 失败

		TbEnter model = getModel(TbEnter.class);
		model.save();

//		if (!user.getStr("password").equals(JFlyFoxUtils.passwordEncrypt(oldPassword))) {
//			json.put("msg", "密码错误！");
//			renderJson(json.toJSONString());
//			return;
//		}
//		if (StrUtils.isNotEmpty(newPassword) && !newPassword.equals(newPassword2)) {
//			json.put("msg", "两次新密码不一致！");
//			renderJson(json.toJSONString());
//			return;
//		} else if (StrUtils.isNotEmpty(newPassword)) { // 输入密码并且一直
//			model.set("password", JFlyFoxUtils.passwordEncrypt(newPassword));
//		}

		json.put("status", 1);// 成功
		renderJson(json.toJSONString());
	}


	//简单上传，使用默认策略，只需要设置上传的空间名就可以了
	public String getUpToken() {
		//密钥配置
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		return auth.uploadToken(BUCKET_NAME);
	}
}
