package de.carey.kotbbble.base

abstract class BaseMVPActivity<P : BasePresenter> : BaseActivity() {

    lateinit var mPresenter: P

    override fun initPresenter() {
        mPresenter = TUtil.getT(this, 0)
        if (this is BaseView) {
            mPresenter.setView(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetached()
    }
}
