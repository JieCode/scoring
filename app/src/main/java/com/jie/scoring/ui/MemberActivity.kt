package com.jie.scoring.ui

import android.os.Bundle
import com.jie.scoring.base.BaseActivity
import com.jie.scoring.databinding.ActivityMemberBinding

class MemberActivity : BaseActivity() {
    private var binding: ActivityMemberBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}