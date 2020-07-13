package com.kadmiv.random_user_app.base.interfaces

import android.view.View

interface IPresenter<V> {

    fun onStart() {}
    fun onPause() {}
    fun onRestart() {}
    fun onStop() {}
    fun onSaveInstanceState() {}
    fun onDestroy() {}

    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     *
     * @param view the view associated with this presenter
     */
    fun onAttach(view: V)

    /**
     * Drops the reference to the view when destroyed
     */
    fun onDetach()

    var isAttached: Boolean

}