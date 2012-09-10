
============================= Android Google map local development setting ======================
C:\Users\czhao\.android\debug.keystore
keytool -list -alias androiddebugkey -keystore C:\Users\czhao\.android\debug.keystore -storepass android -keypass android
http://code.google.com/android/maps-api-signup.html
<com.google.android.maps.MapView
                 android:layout_width="fill_parent"
                 android:layout_height="fill_parent"
                 android:apiKey="0pMwFTdixXK-a2cyjPHDeeO7HmVHdvLlhdcl7sA"
                 />

---
keytool -list -alias androiddebugkey -keystore C:\Users\michael.wang\.android\debug.keystore -storepass android -keypass android
http://code.google.com/android/maps-api-signup.html
<com.google.android.maps.MapView
                 android:layout_width="fill_parent"
                 android:layout_height="fill_parent"
                 android:apiKey="0pMwFTdixXK8Or1NSmw_l2wlAjnHv_TvEpNi-Sg"
                 />
                 
==============================
Step 1:
C:\keytool -genkey -v -keystore gogwt.keystore -alias gogwt -keyalg RSA -validity 10000
Enter keystore password: gogwt123
Re-enter new password:  gogwt123
What is your first and last name?
 [Unknown]:  Go GWT
What is the name of your organizational unit?
 [Unknown]:  gogwt org unit
What is the name of your organization?
 [Unknown]:  gogwt org
What is the name of your City or Locality?
 [Unknown]:  gogwt city
What is the name of your State or Province?
 [Unknown]:  gogwt state
What is the two-letter country code for this unit?
 [Unknown]:  US
Is CN=Go GWT, OU=gogwt org unit, O=gogwt org, L=gogwt city, ST=gogwt state, C=US correct?
 [no]:  y

Generating 1,024 bit RSA key pair and self-signed certificate (SHA1withRSA) with a validity of 10,000 days
       for: CN=Go GWT, OU=gogwt org unit, O=gogwt org, L=gogwt city, ST=gogwt state, C=US
Enter key password for <gogwt>
       (RETURN if same as keystore password):
[Storing gogwt.keystore]

Step 2:
C:\trial\gogwt\keystore>keytool -list -alias gogwt -keystore gogwt.keystore -storepass gogwt123 -keypass gogwt123
gogwt, Sep 10, 2011, PrivateKeyEntry,
Certificate fingerprint (MD5): 56:59:CF:1F:8E:B9:B6:CE:49:23:20:9C:4E:7F:8A:78

http://code.google.com/android/maps-api-signup.html

  <com.google.android.maps.MapView
                 android:layout_width="fill_parent"
                 android:layout_height="fill_parent"
                 android:apiKey="0pMwFTdixXK9Q7HsSwt-Y7t0SW9ru0iPhrJC5tA"
                 />


=====================================================================
=== DEPLOY apk file to device
===
Step 1: Prepard, 
   1.1 turn off debug by change GwtConfig.DEBUG = false;
   1.2 change google map key in layout/tracking_location_tab_layout.xml

Step 2: export apk file
Right click Android Tools -> Export unsigned apk and save to root directory

Step 3: Sign apk file
jarsigner -verbose -keystore gogwt.keystore GoGWTrackingAndroid02242012.apk gogwt
password: gogwt123

$ jarsigner -verify -verbose -certs GoGWTrackingAndroid02242012.apk

Step 4:
zipalign -v -f 4 GoGWTrackingAndroid02242012.apk out.apk

Step 5: 
adb install GoGWTGpsTracking_Android.apk
adb install out.apk

======================================

