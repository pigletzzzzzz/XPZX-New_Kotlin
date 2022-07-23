package com.czl.module_mine.fragment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.os.EnvironmentCompat
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.czl.lib_base.base.BaseFragment
import com.czl.lib_base.config.AppConstants
import com.czl.lib_base.data.bean.SexBean
import com.czl.lib_base.data.bean.UserInfoBean
import com.czl.lib_base.extension.loadCircleImage
import com.czl.lib_base.util.PermissionUtil
import com.czl.lib_base.util.ToastHelper
import com.czl.module_mine.BR
import com.czl.module_mine.R
import com.czl.module_mine.databinding.FragmentUserInfoBinding
import com.czl.module_mine.viewmodel.UserInfoViewModel
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSelectListener
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


@Route(path = AppConstants.Router.Mine.F_USER_INFO)
class UserInfoFragment : BaseFragment<FragmentUserInfoBinding, UserInfoViewModel>() {

    private val ACTION_CHOOSE_IMAGE = 0x201
    private val mBitmap: Bitmap? = null
    private var isAndroidQ = Build.VERSION.SDK_INT >= 29

    // 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    private var mCameraImagePath: String? = null

    //用于保存拍照图片的uri
    private var mCameraUri: Uri? = null
    private var imageUrl = ""
    private var imageUri: Uri? = null

    //拍照的请求码
    private val CAMERA_REQUEST_CODE = 1

    private val sexBeans = arrayListOf<SexBean>()
    private val sexStr = arrayListOf<String>()
    private var nations = arrayListOf<UserInfoBean.Nation>()
    private var nationStr = arrayListOf<String>()
    private var edus = arrayListOf<UserInfoBean.Edu>()
    private var eduStr = arrayListOf<String>()
    private var skillLevels = arrayListOf<UserInfoBean.SkillLevel>()
    private var skillLevelStr = arrayListOf<String>()
    private var posts = arrayListOf<UserInfoBean.Post>()
    private var postStr = arrayListOf<String>()
    private var deptl = arrayListOf<UserInfoBean.Deptl>()
    private var deptlStr = arrayListOf<String>()

    override fun initContentView(): Int {
        return R.layout.fragment_user_info
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        sexBeans.clear()
        sexStr.clear()
        nationStr.clear()

        var sexBean = SexBean(1, "男")
        sexBeans.add(sexBean)
        sexBean = SexBean(0, "女")
        sexBeans.add(sexBean)
        sexStr.add("男")
        sexStr.add("女")

        viewModel.apply {
            tvTitle.set("个人信息")
            getUserInfoDetail()
        }
    }

    override fun initViewObservable() {
        viewModel.uc.getUserInfoData.observe(this, {
            binding.userData = it
            it.apply {

                viewModel.userName.set(userName)
                viewModel.phone.set(phone)
                viewModel.loginCode.set(loginCode)
                viewModel.userno.set(userNO)
                viewModel.idcode.set(idCode)
                viewModel.portait.set(portait)

                if (portait != null) {
                    if (portait.isNotEmpty()) {
                        binding.imgHeard.loadCircleImage(
                            AppConstants.Url.IMG_UPLOAD_URL + portait.substring(
                                1
                            )
                        )
                    }
                }

                if (sex != null) {
                    if (sex?.isNotEmpty()!!) {
                        if (sex == "1") {
                            viewModel.sex.set("1")
                            binding.tvSex.text = "男"
                        } else {
                            viewModel.sex.set("0")
                            binding.tvSex.text = "女"
                        }
                    }
                }

                if (edu != null) {
                    if (edu?.isNotEmpty()!!) {
                        viewModel.edu.set(edu)
                        for (e: UserInfoBean.Edu in edus) {
                            if (e.code == edu) {
                                binding.tvEducation.text = e.title
                            }
                        }
                    }
                }

                if (deptId != null) {
                    if (deptId?.isNotEmpty()!!) {
                        viewModel.deptid.set(deptId)
                        for (d: UserInfoBean.Deptl in deptlList) {
                            if (d.id == deptId) {
                                binding.tvEmployer.text = d.name
                            }
                        }
                    }
                }

                if (nation != null) {
                    if (nation?.isNotEmpty()!!) {
                        viewModel.nation.set(nation)
                        for (n: UserInfoBean.Nation in nations) {
                            if (n.code == nation) {
                                binding.tvNation.text = n.title
                            }
                        }
                    }
                }

                if (post != null) {
                    viewModel.post.set(post)
                    for (p: UserInfoBean.Post in posts) {
                        if (p.code == post) {
                            binding.tvPost.text = p.title
                        }
                    }
                }

                if (skillLevel != null) {
                    if (skillLevel?.isNotEmpty()!!) {
                        viewModel.skillLevel.set(skillLevel)
                        for (s: UserInfoBean.SkillLevel in skillLevels) {
                            if (s.code == skillLevel) {
                                binding.tvSkillLevel.text = s.title
                            }
                        }
                    }
                }


                if (edus.isNotEmpty()) {
                    viewModel.model.saveCacheListData(edus)
                }

                if (skillLevels.isNotEmpty()) {
                    viewModel.model.saveCacheListData(skillLevels)
                }

                if (nations.isNotEmpty()) {
                    viewModel.model.saveCacheListData(nations)
                }

                if (posts.isNotEmpty()) {
                    viewModel.model.saveCacheListData(posts)
                }

                if (deptlList.isNotEmpty()) {
                    viewModel.model.saveCacheListData(deptlList)
                }

            }
        })

        viewModel.uc.changeAvatarEvent.observe(this, {
            XPopup.Builder(context)
                .isDarkTheme(false)
                .hasShadowBg(true)
                .asBottomList("请选择", arrayOf("从相册选择", "拍照")
                ) { position, text ->
                    if (position == 0) {
                        PermissionUtil.reqStorage(
                            fragment = this@UserInfoFragment,
                            callback = { allGranted, _, _ ->
                                if (allGranted) {
                                    val intent = Intent(Intent.ACTION_PICK)
                                    intent.setDataAndType(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        "image/*"
                                    )
                                    startActivityForResult(intent, ACTION_CHOOSE_IMAGE)
                                }
                            })
                    }

                    if (position == 1) {
                        PermissionUtil.reqCamera(
                            fragment = this@UserInfoFragment,
                            callback = { allGranted, _, _ ->
                                if (allGranted) {
                                    openCamera()
                                }
                            })
                    }
                }.show()
        })

        viewModel.uc.changeBirthdayEvent.observe(this, {
            showBirthdayPicker()
        })

        viewModel.uc.changeSexEvent.observe(this, {
            showSexPicker()
        })

        viewModel.uc.changeNationEvent.observe(this, {
            showNationPicker()
        })

        viewModel.uc.changeEducationEvent.observe(this, {
            showEduPicker()
        })

        viewModel.uc.changeSkillLevelEvent.observe(this, {
            showSkillLevelPicker()
        })

        viewModel.uc.changePostEvent.observe(this, {
            showPostPicker()
        })

        viewModel.uc.changeEmployerEvent.observe(this, {
            showDeptlPicker()
        })

        viewModel.uc.submitInfoEvent.observe(this, {
            viewModel.email.set(binding.etEmail.toString().trim())

            viewModel.updateUserInfo()
        })

        viewModel.uc.uploadAvatarEvent.observe(this,{
            binding.imgHeard.loadCircleImage(
                AppConstants.Url.IMG_UPLOAD_URL + it.substring(
                    1
                )
            )
        })

    }

    private fun showSexPicker() {
        var sexPicker = OptionsPickerBuilder(
            context,
            OnOptionsSelectListener { options1, options2, options3, v ->
                binding.tvSex.text = sexStr[options1]
                viewModel.sex.set(sexBeans[options1].id.toString())
            })
            .setSelectOptions(0)
            .setOutSideCancelable(true)
            .build<String>()
        sexPicker.setPicker(sexStr)
        sexPicker.show()
    }

    private fun showNationPicker() {
        nations = viewModel.getNationsCache() as ArrayList<UserInfoBean.Nation>
        for (name: UserInfoBean.Nation in nations) {
            nationStr.add(name.title)
        }

        var nationPicker = OptionsPickerBuilder(
            context,
            OnOptionsSelectListener { options1, options2, options3, v ->
                binding.tvNation.text = nationStr[options1]
                viewModel.nation.set(nations[options1].code)
            })
            .setSelectOptions(0)
            .setOutSideCancelable(true)
            .build<String>()
        nationPicker.setPicker(nationStr)
        nationPicker.show()
    }

    private fun showEduPicker() {
        edus = viewModel.getEdusCaChe() as ArrayList<UserInfoBean.Edu>

        for (name: UserInfoBean.Edu in edus) {
            eduStr.add(name.title)
        }

        var eduPicker = OptionsPickerBuilder(
            context,
            OnOptionsSelectListener { options1, options2, options3, v ->
                binding.tvEducation.text = eduStr[options1]
                viewModel.edu.set(edus[options1].code)
            })
            .setSelectOptions(0)
            .setOutSideCancelable(true)
            .build<String>()
        eduPicker.setPicker(eduStr)
        eduPicker.show()
    }

    private fun showSkillLevelPicker() {
        skillLevels = viewModel.getSkillLevelsCache() as ArrayList<UserInfoBean.SkillLevel>

        for (name: UserInfoBean.SkillLevel in skillLevels) {
            skillLevelStr.add(name.title)
        }

        var skillLevelPicker = OptionsPickerBuilder(
            context,
            OnOptionsSelectListener { options1, options2, options3, v ->
                binding.tvSkillLevel.text = skillLevelStr[options1]
                viewModel.skillLevel.set(skillLevels[options1].code)
            })
            .setSelectOptions(0)
            .setOutSideCancelable(true)
            .build<String>()
        skillLevelPicker.setPicker(skillLevelStr)
        skillLevelPicker.show()
    }

    private fun showPostPicker() {
        posts = viewModel.getPostsCache() as ArrayList<UserInfoBean.Post>

        for (name: UserInfoBean.Post in posts) {
            postStr.add(name.title)
        }

        var postsPicker = OptionsPickerBuilder(
            context,
            OnOptionsSelectListener { options1, options2, options3, v ->
                binding.tvPost.text = postStr[options1]
                viewModel.post.set(posts[options1].code)
            })
            .setSelectOptions(0)
            .setOutSideCancelable(true)
            .build<String>()
        postsPicker.setPicker(postStr)
        postsPicker.show()
    }

    private fun showDeptlPicker() {
        deptl = viewModel.getDeptlListCache() as ArrayList<UserInfoBean.Deptl>

        for (name: UserInfoBean.Deptl in deptl) {
            deptlStr.add(name.name)
        }

        var deptlPicker = OptionsPickerBuilder(
            context,
            OnOptionsSelectListener { options1, options2, options3, v ->
                binding.tvEmployer.text = deptlStr[options1]
                viewModel.deptid.set(deptl[options1].id)
            })
            .setSelectOptions(0)
            .setOutSideCancelable(true)
            .build<String>()
        deptlPicker.setPicker(deptlStr)
        deptlPicker.show()
    }

    private fun showBirthdayPicker() {
        var birthdayPicker = TimePickerBuilder(context, OnTimeSelectListener { date, v ->
            viewModel.birthday.set(getTime(date))
            binding.tvBirthday.text = getTime(date)
        })
            .build()
        birthdayPicker.show()
    }

    private fun getTime(date: Date): String? { //可根据需要自行截取数据显示
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    //调起相机拍照
    private fun openCamera() {
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (captureIntent.resolveActivity(requireActivity().packageManager) != null) {
            var photoFile: File
            lateinit var photoUri: Uri
            if (isAndroidQ) {
                // 适配android 10
                photoUri = createImageUri()
            } else {//10以下
                photoFile = createImageFile()!!
                if (photoFile != null) {
                    mCameraImagePath = photoFile.absolutePath
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        photoUri = FileProvider.getUriForFile(
                            requireActivity(),
                            requireActivity().packageName + ".fileprovider",
                            photoFile
                        )
                    } else {
                        photoUri = Uri.fromFile(photoFile)
                    }
                }
            }
            mCameraUri = photoUri
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTION_CHOOSE_IMAGE) {
            if (data?.data == null) {
                ToastHelper.showErrorToast("获取照片数据失败！")
                return
            }
            val uri = data.data
            val file = uri?.let { getImagePath(it, null)?.let { saveFile(it) } }
            if (file != null) {
                uploadImage(file)
//                imageUrl = uploadImage(file)
//                if (imageUri.toString().isNotEmpty()) {//上传头像成功
//                    viewModel.portait.set(imageUrl)
//                    viewModel.saveUserAvatar()
//                }
                Log.d(tag, "相册imageUrl=$imageUrl")
            }

        }
        if (requestCode == CAMERA_REQUEST_CODE) {
            Log.d(tag, "take success")
            try {
                val cameraFile = BitmapFactory.decodeStream(mCameraUri?.let {
                    context?.contentResolver?.openInputStream(
                        it
                    )
                })?.let { saveFile(it) }
                uploadImage(cameraFile!!)
//                imageUrl = uploadImage(cameraFile!!)
//                Log.e("拍照地址----->", "imageUrl=$imageUrl")
//                viewModel.portait.set(imageUrl)
//                viewModel.saveUserAvatar()

                Log.d(tag, "拍照imageUrl=$imageUrl")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
    private fun createImageUri(): Uri {
        val status: String = Environment.getExternalStorageState()
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        return if (status == Environment.MEDIA_MOUNTED) {
            requireActivity().contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues()
            )!!
        } else {
            requireActivity().contentResolver.insert(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                ContentValues()
            )!!
        }
    }

    @SuppressLint("Range")
    private fun getImagePath(uri: Uri, Selection: String?): String? {
        var path: String? = null
        val cursor = context?.contentResolver?.query(uri, null, Selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    //创建保存图片的文件
    private fun createImageFile(): File? {
        val imageName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (!storageDir!!.exists()) {
            storageDir.mkdir()
        }
        val tempFile = File(storageDir, imageName)
        if (Environment.MEDIA_MOUNTED != EnvironmentCompat.getStorageState(tempFile)) {
            return null
        }
        return tempFile
    }

    private fun saveFile(imagePath: String): File {
        val bitmap = BitmapFactory.decodeFile(imagePath)
        return saveFile(bitmap)
    }

    private fun saveFile(bitmap: Bitmap): File {
        val filepath = requireActivity().filesDir.absolutePath
        val fileParent = File(filepath)
        if (!fileParent.exists()) {
            fileParent.mkdirs()
        }
        Log.d(tag, filepath)
        val file = File("$filepath/image.jpg")
        if (file.exists()) {
            file.delete()
        }
        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            bos.flush()
            bos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d(tag, "filename=${file.absolutePath}")
        return file
    }

    private fun uploadImage(file: File){
        val client = OkHttpClient()
        var result = ""
        val url = AppConstants.Url.BASE_URL + AppConstants.Url.IMG_UPLOAD_AVATAR_URL
        val image = RequestBody.create(MediaType.parse("image/*"), file) //所有图片类型
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", file.name, image)
            .build()
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(tag, e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        var jsonObject = JSONObject(response.body()!!.string())
                        result = jsonObject.getString("url")

                        viewModel.portait.set(result)
                        viewModel.saveUserAvatar()
                    }
                    Log.d(tag, "upload success---${result}")
                }
            }

        })
    }
}