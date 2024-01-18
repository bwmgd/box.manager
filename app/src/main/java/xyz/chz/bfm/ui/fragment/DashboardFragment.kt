package xyz.chz.bfm.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import xyz.chz.bfm.databinding.FragmentDashboardBinding
import xyz.chz.bfm.dialog.IMakeDialog
import xyz.chz.bfm.dialog.MakeDialog
import xyz.chz.bfm.util.Util
import xyz.chz.bfm.util.command.SettingCmd
import xyz.chz.bfm.util.command.TermCmd

@AndroidEntryPoint
class DashboardFragment : Fragment(), IMakeDialog {

    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Util.isClashOrSing) {
            showRes()
        } else {
            MakeDialog(
                "Wowo :( ",
                "Your core is not clash or sing-box so dashboard not available",
                false,
                false
            ).show(requireActivity().supportFragmentManager, "")
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showRes() = with(binding) {
        var linkDB: String = if (SettingCmd.core == "clash") {
            TermCmd.linkDBClash
        } else {
            TermCmd.linkDBSing
        }
        if (linkDB.startsWith(":")) {
            linkDB = "0.0.0.0$linkDB"
        }
        dbWebview.loadUrl("${linkDB}/ui/#/proxies")

        with(dbWebview.settings) {
            domStorageEnabled = true
            databaseEnabled = true
            allowContentAccess = true
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            dbWebview.webViewClient = WebViewClient()
        }
    }

    override fun onDialogPositiveButton(dialog: DialogFragment) {
    }
}