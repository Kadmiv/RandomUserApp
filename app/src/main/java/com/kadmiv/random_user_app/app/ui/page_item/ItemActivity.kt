package com.kadmiv.random_user_app.app.ui.page_item

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.kadmiv.random_user_app.R
import com.kadmiv.random_user_app.databinding.ActivityItemBinding
import com.kadmiv.random_user_app.repo.api.models.user.response.User
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_item.*
import org.koin.android.ext.android.get
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val USER_EXTRAS = "USER_EXTRAS"

class ItemActivity : AppCompatActivity(), IPageItem {

    var LOG: Logger = LoggerFactory.getLogger(ItemActivity::class.java)

    val mPresenter: ItemActivityPresenter = get()

    lateinit var binding: ActivityItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        mPresenter.onAttach(this)

        val userID = intent.getStringExtra(USER_EXTRAS)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_item)
        binding.presenter = mPresenter
        mPresenter.getUserByID(userID)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun setUserModel(selectedUser: User) {
        binding.model = selectedUser
    }

    override fun callToPhone(number: String) {

        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CALL_PHONE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        LOG.debug("have permissions for call")

                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:$number")
                        startActivity(callIntent)
                        return
                    }

                    showSnackBar("Not have permissions!")
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    LOG.debug("")
                    token.continuePermissionRequest()
                }
            }).check()

    }

    override fun createEmailFor(email: String) {
        val emailIntent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null
            )
        )
        startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    private fun showSnackBar(text: String) {
        Snackbar.make(scroll_view, text, Snackbar.LENGTH_LONG)
            .setAction(R.string.open_app_settings) { mPresenter.onAppSettingsBtnClicked() }
            .show()
    }

    override fun showAppSettings() {
        LOG.debug("")

        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${packageName}")
        )
        startActivityForResult(appSettingsIntent, 911)
    }

}