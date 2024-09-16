package com.seogaemo.toss_payments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.seogaemo.toss_payments.databinding.ActivityMainBinding
import com.tosspayments.paymentsdk.PaymentWidget
import com.tosspayments.paymentsdk.TossPayments
import com.tosspayments.paymentsdk.model.PaymentCallback
import com.tosspayments.paymentsdk.model.PaymentWidgetStatusListener
import com.tosspayments.paymentsdk.model.TossPaymentResult
import com.tosspayments.paymentsdk.model.paymentinfo.TossCardPaymentInfo
import com.tosspayments.paymentsdk.view.PaymentMethod

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val tossPaymentActivityResult: ActivityResultLauncher<Intent> =
        TossPayments.getPaymentResultLauncher(
            this,
            { success ->
                Log.d("확인1", success.toString())
                Log.i("success", success.paymentKey)
                Log.i("success", success.orderId)
                Log.i("success", success.amount.toString())
            },
            { fail ->
                Log.d("확인2", fail.toString())
            }
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tossPayments = TossPayments("test_ck_D5GePWvyJnrK0W0k6q8gLzN97Eoq")
        val tossPaymentInfo = TossCardPaymentInfo(orderId = "wBWO9RJXO0U132313123123YqJMV4er8J", orderName = "orderName", 1000)

        binding.button.setOnClickListener {
            tossPayments.requestCardPayment(
                this,
                tossPaymentInfo,
                tossPaymentActivityResult
            )
        }
    }
}