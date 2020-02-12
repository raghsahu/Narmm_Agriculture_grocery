package com.drraisingh.narmm.Config;

/**
 * Created by Rajesh Dabhi on 22/6/2017.
 */

public class BaseURL {

    public static final String PREFS_NAME = "GroceryLoginPrefs";

    public static final String PREFS_NAME2 = "GroceryLoginPrefs2";

    public static final String IS_LOGIN = "isLogin";

    public static final String KEY_NAME = "user_fullname";

    public static final String KEY_EMAIL = "user_email";

    public static final String KEY_ID = "user_id";

    public static final String KEY_MOBILE = "user_phone";

    public static final String KEY_IMAGE = "user_image";

    public static final String KEY_PINCODE = "pincode";

    public static final String KEY_SOCITY_ID = "Socity_id";

    public static final String KEY_SOCITY_NAME = "socity_name";

    public static final String KEY_HOUSE = "house_no";

    public static final String KEY_DATE = "date";

    public static final String KEY_TIME = "time";

    public static final String KEY_PASSWORD = "password";
    public static final String KEY_RES = "res";
    public static final String KEY_MSG = "msg";

    public static String BASE_URL = "";
    public static String IMG_PROFILE_URL = "https://ihisaab.in/NARMM/uploads/profile/";

    public static String IMG_SLIDER_URL = "https://ihisaab.in/NARMM/uploads/sliders/";
    public static String IMG_PRODUCT_URL =  "https://ihisaab.in/NARMM//uploads/products/";
    public static String IMG_CATEGORY_URL = "https://ihisaab.in/NARMM//uploads/category/";
    public static String GET_SLIDER_URL =  "https://ihisaab.in/NARMM/Api/slider";
    public static String GET_CATEGORY_URL = "https://ihisaab.in/NARMM/Api/get_category";
    public static String GET_PRODUCT_URL = "https://ihisaab.in/NARMM/Api/get_product";
    public static String GET_PRODUCT_DETAILS_URL = "https://ihisaab.in/NARMM/Api/product_detail";
    public static String farmer_pin_set = "https://ihisaab.in/NARMM/Api/farmer_pin_set";
    public static String FORGOT_URL = "https://ihisaab.in/NARMM/Api/forgetpassword";
    public static String LOGIN_URL = "https://ihisaab.in/NARMM/Api/farmer_login";
    public static String REGISTER_URL = "https://ihisaab.in/NARMM/Api/farmer_registration";
    public static String GET_SHOP_URL = "https://ihisaab.in/NARMM/Api/get_shop_list";
    public static String GET_TIME_SLOT_URL = "https://ihisaab.in/NARMM/Api/pickup_time";
    public static String company_profile = "https://ihisaab.in/NARMM/Api/company_profile";
    public static String get_crop = "https://ihisaab.in/NARMM/Api/get_crop";
    public static String GET_SUPPORT_URL = "https://ihisaab.in/NARMM/Api/support_page";
    public static String GET_ABOUT_URL = "https://ihisaab.in/NARMM/Api/about_page";
    public static String GET_TERMS_URL = "https://ihisaab.in/NARMM/Api/terms_and_condition_page";
    public static String send_order = "https://ihisaab.in/NARMM/Api/send_order";
    public static String payment = "https://ihisaab.in/NARMM/Api/payment";
    public static String GET_ORDER_URL = "https://ihisaab.in/NARMM/Api/my_orders";
    public static String GET_crop_trials = "https://ihisaab.in/NARMM/index.php/api/crop_trials";
    public static String GET_narmm_product = "https://ihisaab.in/NARMM/index.php/api/get_narmm_product";
    public static String GET_offer_productt = "https://ihisaab.in/NARMM/index.php/api/get_offer_product";
    public static String GET_party_product = "https://ihisaab.in/NARMM/index.php/api/get_party_product";
    public static String GET_current_location_shop_list = "https://ihisaab.in/NARMM/index.php/api/current_location_shop_list";
    public static String GET_send_complaint = "https://ihisaab.in/NARMM/index.php/api/send_complaint";
    public static String GET_send_enquiry = "https://ihisaab.in/NARMM/index.php/api/send_enquiry";
    public static String GET_get_complaint = "https://ihisaab.in/NARMM/index.php/api/get_complaint";
    public static String GET_get_enquiry = "https://ihisaab.in/NARMM/index.php/api/get_enquiry";
    public static String GET_change_password = "https://ihisaab.in/NARMM/index.php/api/change_password";
    public static String GET_addtocart = "https://ihisaab.in/NARMM/index.php/api/addtocart";
    public static String GET_get_cart = "https://ihisaab.in/NARMM/index.php/api/get_cart";
    public static String GET_remove_to_cart = "https://ihisaab.in/NARMM/index.php/api/remove_to_cart";
    public static String GET_update_cart = "https://ihisaab.in/NARMM/index.php/api/update_cart";
    public static String GET_farmer_diary = "https://ihisaab.in/NARMM/index.php/api/farmer_diary";
   // public static String GET_order_bill = "http://ihisaab.in/NARMM/index.php/Api/order_bill";
    public static String GET_order_bill = "https://ihisaab.in/NARMM/index.php/Front/order_bill_view/";
    public static String GET_cart_counting = "http://ihisaab.in/NARMM/index.php/Api/cart_counting";
    public static String GET_getprofile = "https://ihisaab.in/NARMM/Api/getprofile";
    public static String GET_update_profile = "http://ihisaab.in/NARMM/index.php/Api/update_profile";
    public static String GET_farmer_payment = "http://ihisaab.in/NARMM/index.php/Api/farmer_payment";
    public static String GET_get_state = "https://ihisaab.in/NARMM/Api/get_state";
    public static String GET_get_district= "https://ihisaab.in/NARMM/Api/get_district";
    public static String GET_get_tehsil= "https://ihisaab.in/NARMM/Api/get_tehsil";
    public static String GET_get_block= "https://ihisaab.in/NARMM/Api/get_block";
    public static String GET_farmer_acres_area_update= "https://ihisaab.in/NARMM/Api/farmer_acres_area_update";



//********************no use***********************************************************
    public static String PINCODE_Check = BASE_URL + "index.php/Api/check_Society";
    public static String MOBILE_OTP = BASE_URL + "index.php/Api/sentuserotp";


    public static String GET_SOCITY_URL = BASE_URL + "index.php/api/get_society";

    public static String EDIT_PROFILE_URL = BASE_URL + "index.php/api/update_userdata";

    public static String ADD_ORDER_URL = BASE_URL + "index.php/api/send_order";



    public static String ORDER_DETAIL_URL = BASE_URL + "index.php/api/order_details";

    public static String DELETE_ORDER_URL = BASE_URL + "index.php/api/cancel_order";

    public static String GET_LIMITE_SETTING_URL = BASE_URL + "index.php/api/get_limit_settings";

    public static String ADD_ADDRESS_URL = BASE_URL + "index.php/api/add_address";

    public static String JSON_RIGISTER_FCM = BASE_URL + "index.php/api/register_fcm";



    public static String DELETE_ADDRESS_URL = BASE_URL + "index.php/api/delete_address";

    public static String EDIT_ADDRESS_URL = BASE_URL + "index.php/api/edit_address";


    // global topic to receive app wide push notifications

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    public static final String PUSH_NOTIFICATION = "pushNotification";

}
