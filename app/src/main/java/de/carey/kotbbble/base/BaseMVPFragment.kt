package de.carey.kotbbble.base

abstract class BaseMVPFragment<V : BaseView, P : BasePresenter<V>> : BaseFragment() {

    lateinit var mPresenter: P

    override fun initPresenter() {
        mPresenter = TUtil.getT(this, 1)
        mPresenter.setView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetached()
    }
}