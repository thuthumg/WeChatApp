package com.padcmyanmar.ttm.wechatapp.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.DialogFragment
import com.google.zxing.WriterException
import com.padcmyanmar.ttm.wechatapp.R
import kotlinx.android.synthetic.main.qr_code_dialog_popup.*
import kotlinx.android.synthetic.main.qr_code_dialog_popup.ivQrCode

class QRCodePopUpDialog: DialogFragment(){

    companion object{
        const val TAG_QR_CODE_DIALOG = "TAG_QR_CODE_DIALOG"
        const val LOGIN_USER_NAME = "LOGIN_USER_NAME"
        const val LOGIN_USER_CODE = "LOGIN_USER_CODE"

        fun newFragment():QRCodePopUpDialog{
            return QRCodePopUpDialog()
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner_bg);
        return inflater.inflate(R.layout.qr_code_dialog_popup, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvAccountName.text = arguments?.getString(LOGIN_USER_NAME)
        arguments?.getString(LOGIN_USER_CODE)?.let { generateQRCode(it) }

        ivClosePopup.setOnClickListener {
            dismiss()
        }
    }

    private fun generateQRCode(loginUserQRCode: String) {
        val windowManager: WindowManager = context?.getSystemService(Context.WINDOW_SERVICE)  as WindowManager

        // on below line we are initializing a
        // variable for our default display
        val display: Display = windowManager.defaultDisplay

        // on below line we are creating a variable
        // for point which is use to display in qr code
        val point: Point = Point()
        display.getSize(point)

        // on below line we are getting
        // height and width of our point
        val width = point.x
        val height = point.y

        // on below line we are generating
        // dimensions for width and height
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4
        Log.d("ProfileFragment","test qrcode$loginUserQRCode")

        val qrgEncoder = QRGEncoder(loginUserQRCode, null, QRGContents.Type.TEXT, dimen)
        qrgEncoder.colorBlack = Color.WHITE
        qrgEncoder.colorWhite = Color.BLACK
        try {
            // Getting QR-Code as Bitmap
            var    bitmap = qrgEncoder.bitmap
            // Setting Bitmap to ImageView
            ivQrCode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            Log.v("ProfileFragment", e.toString())
        }


    }

}