/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lidroid.xutils.sample.entities;

import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * Author: wyouflf
 * Date: 13-7-29
 * Time: 下午5:04
 */
public class Child extends EntityBase {

    public String name;

    private String email;

    //@Foreign(column = "parentId", foreign = "id")
    //public ForeignLazyLoader<Parent> parent;
    //@Foreign(column = "parentId", foreign = "isVIP")
    //public List<Parent> parent;
    @Foreign(column = "parentId", foreign = "id")
    public Parent parent;


    // Transient使这个列被忽略，不存入数据库
    @Transient
    public String willIgnore;

    public static String staticFieldWillIgnore; // 静态字段也不会存入数据库

    private String text;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", parent=" + parent +
                ", willIgnore='" + willIgnore + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
