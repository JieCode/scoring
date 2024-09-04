package com.jie.scoring.bean

import java.io.Serializable

/**
 * @CreateDate: 2024/9/3
 * @author: jingjie
 * @desc:
 */
class MemberBean : Serializable {
    var id: Int = 0
    private var username: String = ""
    private var gender: String = ""

    constructor(username: String, gender: String) {
        this.username = username
        this.gender = gender
    }
}