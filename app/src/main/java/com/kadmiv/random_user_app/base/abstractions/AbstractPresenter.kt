package com.kadmiv.random_user_app.base.abstractions

import com.kadmiv.random_user_app.base.interfaces.IPresenter
import com.kadmiv.random_user_app.base.interfaces.IView


open class AbstractPresenter<V : IView> : IPresenter<V> {

    protected var mView: V? = null
        private set

    override var isAttached = mView != null

    override fun onAttach(view: V) {
        this.mView = view
    }

    override fun onDetach() {
        this.mView = null
    }


}