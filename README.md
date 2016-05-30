Ti.AccountManger
================

This is Titanium version of [Android's AccountManager](https://developer.android.com/reference/android/accounts/AccountManager.html). Thanks to Stefan.

USING YOUR MODULE IN CODE
-------------------------

~~~~
var accountmodule = require('ti.accountmanager');
var myAccounts = accountmodule.getAccounts();
console.log(myAccounts);

var tokens = myAccounts.map(function(a){
return accountmodule.getAuthToken(a.name,a.type,{});
});
~~~~

Example:
~~~
[{
"name":"kont****chmied","accountType":"Skype™","type":"com.skype.contacts.sync"
},{
"name":"sync","accountType":"ARTE","type":"tv.arte.plus7"
},{
"name":"kontak*****glemail.com","accountType":"Google","type":"com.google"
},{
"name":"appwerft","accountType":"Twitter","type":"com.twitter.android.auth.login"
},{
"name":"rain*****asterei-hamburg.de","accountType":"Meetup","type":"com.meetup.auth"
}]
~~~

Dont forget to request the <uses-permission android:name="android.permission.GET_ACCOUNTS"/> permission! 

For Marshmellow you need the new runtime permissions. Currently it only works with a patched version of 5.0.2.GA. You can ind the android.jar under [android/modules](https://github.com/AppWerft/Ti.AccountManager/blob/master/android/modules/android/titanium.jar)