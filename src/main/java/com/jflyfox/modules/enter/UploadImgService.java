package com.jflyfox.modules.enter;

import com.alibaba.fastjson.JSONObject;
import com.jflyfox.common.service.threadpool.ExecutorProcessPool;
import com.jflyfox.common.utils.IdUtils;
import com.jflyfox.common.utils.QiniuUtils;
import com.jflyfox.system.file.util.FileUploadUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Created by root on 17-4-22.
 */
public class UploadImgService {

	private static final String ROOT_IMAGE_PATH = "upload";
	static Logger logger =Logger.getLogger(EnterController.class);


	public static JSONObject uploadFile(String subPath, HttpServletRequest request) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		JSONObject result = new JSONObject();
		result.put("status",2);
		try {
			List<FileItem> items = upload.parseRequest(request);
			if (items==null||items.isEmpty()){
				return result;
			}
			FileItem fileItem = items.get(0);
			if (fileItem.getSize() > 3 * 1024 * 1024){ //限制3M
				return result;
			}
			final byte[] bytes = fileItem.get();
			String pictureName =fileItem.getName();
			if (pictureName==null){
				return result;
			}
			final String imageName = IdUtils.getIdByUUId()+pictureName.substring(pictureName.lastIndexOf("."));

			//异步上传到七牛
			final String img_package = subPath;
			ExecutorProcessPool.getInstance().execute(new Runnable() {
				@Override
				public void run() {
					QiniuUtils.upload2Qiniu(bytes,imageName,img_package);
				}
			});

			//上传到本地
			String webRootPath = FileUploadUtils.getRootPath() ;
			String storePath = webRootPath + File.separator+ ROOT_IMAGE_PATH+File.separator+subPath;
			String path = storePath +File.separator+ imageName;
			fileItem.write(new File(path));

			result.put("url",request.getContextPath()+"/"+ ROOT_IMAGE_PATH+"/"+subPath+"/"+imageName);
			result.put("name",imageName);
			result.put("status",1);
			return result;
		}catch (Exception e){
			logger.error("upload file errer:" +e.getMessage());
			return result;
		}
	}
}
