package com.example.e_commerce_app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce_app.R
import com.example.e_commerce_app.adapter.CartItemAdapter
import com.example.e_commerce_app.database.Database
import com.example.e_commerce_app.model.Address
import com.example.e_commerce_app.model.Cart
import com.example.e_commerce_app.model.Order
import com.example.e_commerce_app.model.Product
import kotlinx.android.synthetic.main.activity_checkout.*
import java.util.*
import kotlin.collections.ArrayList


class CheckoutActivity : UiComponentsActivity() {

    private var lastAddressDetails: Address? = null
    private lateinit var lastProductList: ArrayList<Product>
    private lateinit var lastCartItemList: ArrayList<Cart>
    private var lastSubTotal: Double = 0.0
    private var lastTotal: Double = 0.0
    private var charge: Double = 0.0
    private lateinit var orderDetails: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        setupActionBar()
        getProductList()

        btn_place_order.setOnClickListener {
            placeOrder()
        }

        if(intent.hasExtra("extra_selected_address")){
            lastAddressDetails = intent.getParcelableExtra<Address>("extra_selected_address")
        }

        if(lastAddressDetails != null){
            tv_checkout_address_type.text = lastAddressDetails?.type
            tv_checkout_full_name.text = lastAddressDetails?.fullName
            tv_checkout_address.text = "${lastAddressDetails!!.address}, ${lastAddressDetails!!.zipCode}"
            tv_checkout_additional_note.text = lastAddressDetails?.additionalNote

            if (lastAddressDetails?.otherDetails!!.isNotEmpty()) {
                tv_checkout_other_details.text = lastAddressDetails?.otherDetails
            }
            tv_checkout_mobile_number.text = lastAddressDetails?.mobileNumber

        }


    }

    private fun placeOrder(){
        showProgressBar(getString(R.string.please_wait))
        val uuid = UUID.randomUUID()
        orderDetails = Order(
            Database().getUserID(),
            lastCartItemList,
            lastAddressDetails!!,
            "Order-$uuid",
            lastCartItemList[0].image, //first image will be the order image.
            lastSubTotal.toString(),
            charge.toString(),
            lastTotal.toString(),
            System.currentTimeMillis()

        )
        Database().createOrder(this,orderDetails)


    }


    fun orderCreatedSuccess(){
        Database().updateProductCartDetails(this,lastCartItemList,orderDetails)
    }

    fun cartDetailsUpdatedSuccessfully(){
        hideProgressBar()
        Toast.makeText(this,getString(R.string.your_order_has_placed_success),Toast.LENGTH_LONG).show()
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


    private fun getProductList(){
        showProgressBar(resources.getString(R.string.please_wait))
        Database().getAllProductsList(this@CheckoutActivity)
    }

    fun successProductListFromFS(productList: ArrayList<Product>){
        lastProductList = productList
        getCartItemList()
    }

    private fun getCartItemList(){
        Database().getCartList(this@CheckoutActivity)
    }

    fun successCartItemList(cartList: ArrayList<Cart>){
        hideProgressBar()
        lastCartItemList = cartList

        //rechecking the product stock amount.
        for (product in lastProductList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {
                    cart.stock_quantity = product.stock_quantity
                }
            }
        }

        rv_cart_list_items.layoutManager = LinearLayoutManager(this@CheckoutActivity)
        rv_cart_list_items.setHasFixedSize(true)

        val cartListAdapter = CartItemAdapter(this@CheckoutActivity,lastCartItemList,false)
        rv_cart_list_items.adapter = cartListAdapter

        var quantity2: Int = 0


        for(item in lastCartItemList){
            val availableQuantity = item.stock_quantity.toInt()
            if(availableQuantity>0){
                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()
                quantity2 = quantity
                lastSubTotal += (price*quantity)
            }
        }

        tv_checkout_sub_total.text = "${lastSubTotal} DH"
        if (quantity2 <  3) {
            charge = 30.0
        }
        else {
            charge = 30.0 + (10 * (quantity2-2))
        }
        tv_checkout_shipping_charge.text = "$charge DH"
        if(lastSubTotal>0){
            ll_checkout_place_order.visibility = View.VISIBLE
            lastTotal = lastSubTotal+ charge
            tv_checkout_total_amount.text = "$lastTotal DH"
        }
        else{
            ll_checkout_place_order.visibility = View.GONE
        }

    }



    private fun setupActionBar() {

        setSupportActionBar(toolbar_checkout_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        }

        toolbar_checkout_activity.setNavigationOnClickListener { onBackPressed() }
    }





}








