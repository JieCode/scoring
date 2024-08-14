package com.jie.scoring.util

/**
 * @CreateDate: 2024/7/29
 * @author: jingjie
 * @desc:
 */
class AvoidDoubleClick {
    companion object{
        const val KEY_CLICK_REDO = "key_click_redo"

        private var map:Map<String ,Long> = mapOf()

        /**
         * 如果两次点击事件超过1s，则可以响应点击事件 true,否则不响应点击事件 false
         */
        fun responseDoubleClick(key: String): Boolean {
            var currentTime = System.currentTimeMillis()
            return if (map.containsKey(key)) {
                var lastTime = map[key]!!
                if (currentTime - lastTime > 500) {
                    map = map.plus(key to currentTime)
                    true
                } else {
                    false
                }
            } else {
                map = map.plus(key to currentTime)
                true
            }
        }
    }
}