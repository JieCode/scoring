package com.jie.scoring.bean

import java.io.Serializable

/**
 * @CreateDate: 2024/7/8
 * @author: jingjie
 * @desc:
 */
class RedBlueScore : Serializable {
    var redScore: Int = 0
    var blueScore: Int = 0
    var isRedServe: Boolean = true
    var winCount: Int = 0

    constructor(redScore: Int, blueScore: Int, isRedServe: Boolean, winCount: Int) {
        this.redScore = redScore
        this.blueScore = blueScore
        this.isRedServe = isRedServe
        this.winCount = winCount
    }
}