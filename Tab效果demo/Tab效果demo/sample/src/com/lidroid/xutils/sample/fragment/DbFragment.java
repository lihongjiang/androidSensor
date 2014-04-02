package com.lidroid.xutils.sample.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.sample.R;
import com.lidroid.xutils.sample.entities.Child;
import com.lidroid.xutils.sample.entities.Parent;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Author: wyouflf
 * Date: 13-9-14
 * Time: 下午3:35
 */
public class DbFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.db_fragment, container, false);
        ViewUtils.inject(this, view);

        return view;
    }

    @ViewInject(R.id.db_test_btn)
    private Button stopBtn;

    @ViewInject(R.id.result_txt)
    private TextView resultText;

    @OnClick(R.id.db_test_btn)
    public void download(View view) {

        String temp = "";

        Parent parent = new Parent();
        parent.name = "测试";
        parent.setAdmin(true);
        parent.setEmail("wyouflf@gmail.com");

        /*Parent parent2 = new Parent();
        parent2.name = "测试2";
        parent2.isVIP = false;*/

        try {

            //DbUtils db = DbUtils.create(this, "/sdcard/", "test");
            DbUtils db = DbUtils.create(this.getActivity());
            db.configAllowTransaction(true);
            db.configDebug(true);

            Child child = new Child();
            child.name = "child' name";
            //db.saveBindingId(parent);
            //child.parent = new ForeignLazyLoader<Parent>(Child.class, "parentId", parent.getId());
            //child.parent = parent;

            Parent test = db.findFirst(parent);//通过entity的属性查找
            if (test != null) {
                child.parent = test;
                temp += "first parent:" + test + "\n";
                resultText.setText(temp);
            } else {
                child.parent = parent;
            }

            parent.setTime(new Date());
            parent.setDate(new java.sql.Date(new Date().getTime()));

            db.saveBindingId(child);//保存对象关联数据库生成的id

            List<Child> children = db.findAll(Selector.from(Child.class));//.where(WhereBuilder.b("name", "=", "child' name")));
            temp += "children size:" + children.size() + "\n";
            resultText.setText(temp);
            if (children.size() > 0) {
                temp += "last children:" + children.get(children.size() - 1) + "\n";
                resultText.setText(temp);
            }

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -1);
            calendar.add(Calendar.HOUR, 3);

            List<Parent> list = db.findAll(
                    Selector.from(Parent.class)
                            .where(WhereBuilder.b("id", "<", 54)
                                    .append("time", ">", calendar.getTime()))
                            .orderBy("id")
                            .limit(10));
            temp += "find parent size:" + list.size() + "\n";
            resultText.setText(temp);
            if (list.size() > 0) {
                temp += "last parent:" + list.get(list.size() - 1) + "\n";
                resultText.setText(temp);
            }

            //parent.name = "hahaha123";
            //db.update(parent);

            Parent entity = db.findById(Parent.class, child.parent.getId());
            temp += "find by id:" + entity.toString() + "\n";
            resultText.setText(temp);

            List<DbModel> dbModels = db.findDbModelAll(Selector.from(Parent.class)
                    .groupBy("name")
                    .select("name", "count(name) as count"));
            temp += "group by result:" + dbModels.get(0).getDataMap() + "\n";
            resultText.setText(temp);

        } catch (DbException e) {
            temp += "error :" + e.getMessage() + "\n";
            resultText.setText(temp);
        }
    }
}
