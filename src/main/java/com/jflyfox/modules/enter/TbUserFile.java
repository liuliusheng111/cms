package com.jflyfox.modules.enter;

import com.jflyfox.component.base.BaseProjectModel;
import com.jflyfox.jfinal.component.annotation.ModelBind;

@ModelBind(table = "tb_user_file", key = "id")
public class TbUserFile extends BaseProjectModel<TbUserFile> {

    private static final long serialVersionUID = 1L;
    public static final TbUserFile dao = new TbUserFile();

    private String ID = "id"; // 主键
    private String USER_ID = "user_id"; // 用户ID
    private String PHOTO = "photo"; // 照片
    private String RESUME = "resume"; //简历
    private String STATUS = "status"; //0表示正常，1删除


    public TbUserFile setId(Integer value) {
        set(ID, value);
        return this;
    }

    public Integer getId() {
        return get(ID);
    }

    public TbUserFile setUserId(Integer value) {
        set(USER_ID, value);
        return this;
    }

    public Integer getUserId() {
        return get(USER_ID);
    }


    public TbUserFile setResume(String value) {
        set(RESUME, value);
        return this;
    }

    public String  getResume() {
        return get(RESUME);
    }

    public TbUserFile setPhoto(String value) {
        set(PHOTO, value);
        return this;
    }

    public String getPhoto() {
        return get(PHOTO);
    }

    public TbUserFile setStatus(java.lang.Integer value) {
        set(STATUS, value);
        return this;
    }

    public java.lang.Integer getStatus() {
        return get(STATUS);
    }



}
