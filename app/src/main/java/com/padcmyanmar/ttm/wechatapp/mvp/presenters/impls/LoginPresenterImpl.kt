package com.padcmyanmar.ttm.wechatapp.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.ttm.wechatapp.activities.LoginActivity
import com.padcmyanmar.ttm.wechatapp.data.models.WeChatAppModelImpl
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.ttm.wechatapp.mvp.presenters.LoginPresenter
import com.padcmyanmar.ttm.wechatapp.mvp.views.LoginView

class LoginPresenterImpl:LoginPresenter,AbstractBasePresenter<LoginView>() {

    private val mWeChatAppModel = WeChatAppModelImpl

    override fun onTapLogin(
        loginActivity: LoginActivity,
        phoneNo: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {

        mWeChatAppModel.getUsers(
            phoneNo = phoneNo,
            password = password,
            onSuccess = {
                var checkSuccessFlag = false
                for(userData in it)
                {
                    if(phoneNo == userData.phoneNumber && password == userData.password)
                    {
                        checkSuccessFlag = true
                        break
                    }else{
                        checkSuccessFlag = false
                    }
                }

                if(checkSuccessFlag)
                {
                    onSuccess("Login Successfully.")

                }
                else{
                    onSuccess("Login Fail")
                }

            },
            onFailure = {
                onFailure(it)
            }
        )

    }

    override fun onTapBackFunction() {
        mView.navigateToBackFunction()
    }
    override fun goToMainPage() {
        mView.loginFunction()
    }
    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }


}