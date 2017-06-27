package com.jflyfox.modules.export;

import com.jflyfox.modules.enter.TbEnter;
import com.jflyfox.modules.member.TbMember;

import java.util.ArrayList;
import java.util.List;

/** 导出会员或者投稿
 * Created by liuls on 17/6/27.
 */
public class ExportService {

    /**
     * 导出投稿
     * @return
     */

    public List<List<String>> exportEnter() {
        List<TbEnter> enters = TbEnter.dao.find("select * from tb_enter where status=0");
        List<List<String>> values = new ArrayList();
        if (!enters.isEmpty()) {

            //String[] names = { "用户名", "电话", "地址", "年龄","注册时间"};
            for (TbEnter enter : enters) {
                List<String> row = new ArrayList();
                row.add(String.valueOf(enter.getId()));
                row.add(enter.getName());
                row.add(enter.getPhone());
                row.add(enter.getAddress());
                row.add(String.valueOf(enter.getAge()));
                row.add(enter.getGender()==null?"无":enter.getGender()==0?"女":"男");
                row.add(enter.getCreateTime());
                values.add(row);
            }
        }
        return values;
    }

    /**
     * 导出会员
     * @return
     */
    public List<List<String>> exportMember() {
        List<TbMember> enters = TbMember.dao.find("select * from tb_member where status=0");
        List<List<String>> values = new ArrayList();
        if (!enters.isEmpty()) {

            //String[] names = {"ID", "用户名", "电话", "地址", "年龄","性别","类别","身份证","注册时间"};
            for (TbMember member : enters) {
                List<String> row = new ArrayList();
                row.add(String.valueOf(member.getId()));
                row.add(member.getName());
                row.add(member.getPhone());
                row.add(member.getAddress());
                row.add(String.valueOf(member.getAge()));
                row.add(member.getGender()==null?"无":member.getGender()==0?"女":"男");
                row.add(member.getType()==null?"无":member.getType().equals("grownup")?"成人":"儿童");
                row.add(member.getIdcard());
                row.add(member.getCreateTime());
                values.add(row);
            }
        }
        return values;
    }
}
