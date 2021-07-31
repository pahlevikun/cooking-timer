package id.pahlevikun.cookingtimer.common.base.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import id.pahlevikun.cookingtimer.R
import id.pahlevikun.cookingtimer.common.dialog.LoadingDialog
import id.pahlevikun.cookingtimer.di.presentation.PresentationComponent
import id.pahlevikun.cookingtimer.di.getPresentationComponent
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    private val component by lazy { requireContext().getPresentationComponent() }
    private val loadingV2 by lazy { LoadingDialog(requireActivity()) }

    protected val compositeDisposable = CompositeDisposable()

    abstract fun attach()
    abstract fun detach()

    @LayoutRes
    abstract fun contentLayoutRes(): Int
    abstract fun injectComponent(component: PresentationComponent)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(contentLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attach()
    }

    override fun onAttach(context: Context) {
        injectComponent(component)
        super.onAttach(context)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        detach()
        super.onDestroy()
    }

    protected fun showLoadingModal(
        messageToShow: String,
        title: String = getString(R.string.progress_loading),
    ) {
        loadingV2.apply {
            setTitle(title)
            setDesc(messageToShow)
            show()
        }
    }

    protected fun hideLoadingModal() {
        loadingV2.dismiss()
    }

}