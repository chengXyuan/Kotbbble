package de.carey.kotbbble.base

abstract class BaseMVPActivity<V : BaseView, P : BasePresenter<V>> : BaseActivity() {

    lateinit var mPresenter: P

    override fun initPresenter() {
        mPresenter = TUtil.getT(this, 1)
        if (this is BaseView) {
            mPresenter.setView(this as V)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetached()
    }
}
