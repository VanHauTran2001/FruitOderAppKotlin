package com.cuongpq.basemvvm.ui.main.fragment.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.cuongpq.basemvvm.R
import com.cuongpq.basemvvm.databinding.FragmentProfileBinding
import com.cuongpq.basemvvm.ui.base.fragment.BaseMvvmFragment
import com.cuongpq.basemvvm.ui.base.viewmodel.BaseViewModel
import com.cuongpq.basemvvm.ui.main.activity.login.LoginActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import java.io.IOException

class ProfileFragment : BaseMvvmFragment<ProfileCallBack, ProfileViewModel>(), ProfileCallBack {
    private var mUri: Uri? = null
    private val MY_REQUEST_CODE = 10
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getLayoutMain() = R.layout.fragment_profile

    override fun setEvents() {

    }

    override fun initComponents() {
        getBindingData().profileViewModel = mModel
        setUserInformation()
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finishActivity()
                ProfileViewModel.CLICK_LOGOUT -> startActivityLogout()
                ProfileViewModel.CLICK_IMAGE -> onClickAvata()
                ProfileViewModel.CLICK_UPDATE -> onClickUpdateAvata()
            }
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun onClickUpdateAvata() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser ?: return
        val profileChangeRequest = UserProfileChangeRequest.Builder()
            .setPhotoUri(mUri)
            .build()
        firebaseUser.updateProfile(profileChangeRequest)
            .addOnCompleteListener { task: Task<Void?> ->
                if (task.isSuccessful) {
                    getBindingData().imgAvata.visibility = View.GONE
                    Toast.makeText(context, "Upload avata onsuccessfull", Toast.LENGTH_SHORT)
                        .show()
                    Glide.with(context!!).load(firebaseUser.photoUrl)
                        .error(R.drawable.avatar)
                        .into(getBindingData().imgAvata)
                }
            }
    }
    @SuppressLint("UseRequireInsteadOfGet")
    fun setUserInformation() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            return
        } else {
            val photoUrl = user.photoUrl
            getBindingData().txtEmailAccount.text = user.email
            Glide.with(context!!).load(photoUrl).error(R.drawable.avatar).into(getBindingData().imgAvata)
        }
    }
    private fun onClickAvata() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery()
            return
        }
        if (activity?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) === PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            val permison = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            activity?.requestPermissions(permison,MY_REQUEST_CODE)
        }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun startActivityLogout() {
        val dialog = AlertDialog.Builder(activity!!)
            .setTitle("Confirm Dialog")
            .setMessage("Are you sure you want to exit ?")
            .setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(activity,LoginActivity::class.java)
                activity?.startActivity(intent)
            }
            .setNegativeButton("No") { _: DialogInterface?, _: Int -> }
            .create()
        dialog.show()

    }


    override fun getBindingData() = mBinding as FragmentProfileBinding

    override fun getViewModel(): Class<ProfileViewModel> {
        return ProfileViewModel::class.java
    }
    private val activityResultLauncher = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode === Activity.RESULT_OK) {
            val intent = result.data ?: return@registerForActivityResult
            val uri = intent.data
            setUri(uri)
            try {
                val bitmap =MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
                setBitmapImageView(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun setUri(mUri: Uri?) {
        this.mUri = mUri
    }
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }
    private fun setBitmapImageView(bitmapImageView: Bitmap?) {
        Glide.with(getBindingData().imgAvata).load(bitmapImageView).into(getBindingData().imgAvata)
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }
}
