package com.jflyfox.modules.enter;

import com.jfinal.plugin.activerecord.Page;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.base.Paginator;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.modules.CommonController;
import com.jflyfox.modules.admin.article.TbArticle;
import com.jflyfox.modules.admin.comment.TbComment;
import com.jflyfox.modules.admin.pageview.TbPageView;
import com.jflyfox.system.user.SysUser;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerBind(controllerKey = "/enter")
public class EnterController extends BaseProjectController {

	Logger logger =Logger.getLogger(EnterController.class);

	private static final String path = "/pages/enter/";

	public void index() {
		render(path + "enter.html");
	}

	public void upload() {
		Zone z = Zone.autoZone();
		Configuration c = new Configuration(z);
		//创建上传对象
		UploadManager uploadManager = new UploadManager(c);

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			HttpServletRequest request = getRequest();
			List<FileItem> items = upload.parseRequest(request);
			System.out.println("1:=========" + items.size());
			Map param = new HashMap();
			for (Object object : items) {
				FileItem fileItem = (FileItem) object;
				System.out.println("2:=========" + fileItem.toString());
				System.out.println("3：fileItem.getFieldName():==" + fileItem.getFieldName());

				if (fileItem.isFormField()) {
					param.put(fileItem.getFieldName(),
							fileItem.getString("utf-8"));// 如果你页面编码是utf-8的
					System.out.println("4: param.get(fileItem.getFieldName())==="
							+ param.get(fileItem.getFieldName()));
				} else {

					String picturename =fileItem.getName();

					String path = "/tmp/" + picturename;
					System.out.println("======================="+path+"==========");
					fileItem.write(new File(path));
				}
			}
			renderText("ok");
		}catch (Exception e){
			logger.error("upload file errer:"+path +e.getMessage());
			renderText("error");
		}
	}
}
