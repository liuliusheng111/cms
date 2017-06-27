package com.jflyfox.modules.export;

import com.jflyfox.common.utils.XlsUtils;
import com.jflyfox.component.base.BaseProjectController;
import com.jflyfox.jfinal.component.annotation.ControllerBind;
import com.jflyfox.util.DateUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/** 导出
 * Created by admin on 17/6/27.
 */

@ControllerBind(controllerKey = "/export")
public class ExportController extends BaseProjectController {


    /**
     *导出投稿
     */
    public void exportEnter() throws IOException {
        HttpServletResponse response =getResponse();

        //序号，用户昵称，用户ID、位置、内容和时间
        String[] names = { "编号","用户名", "电话", "地址", "年龄","性别","注册时间"};
        String date = DateUtils.format(new Date(),"yyyy-MM-dd-HH-mm");
        response.setHeader("Content-Disposition", "attachment; filename= tougao" +date+ ".xlsx");
        response.setContentType("application/octet-stream");
        ServletOutputStream out = response.getOutputStream();
        XlsUtils.export(out, "投稿", names, new ExportService().exportEnter());
    }

    public void exportMember() throws IOException {
        HttpServletResponse response =getResponse();

        //序号，用户昵称，用户ID、位置、内容和时间
        String[] names = {"ID", "用户名", "电话", "地址", "年龄","性别","类别","身份证","注册时间"};        String date = DateUtils.format(new Date(),"yyyy-MM-dd-HH-mm");
        response.setHeader("Content-Disposition", "attachment; filename= huiyuan" +date+ ".xlsx");
        response.setContentType("application/octet-stream");
        ServletOutputStream out = response.getOutputStream();
        XlsUtils.export(out, "会员", names, new ExportService().exportMember());
    }


}
