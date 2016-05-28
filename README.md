Ti.AccountManger
================

This is Titanium version of Android's AccountManager. Thanks to Stefan.

USING YOUR MODULE IN CODE
-------------------------

~~~~
var accountmodule = require('ti.accountmanager');
var myAccounts = accountmodule.getAccounts();
var authToken = accountmodule.getAuthToken(String accountName, String accountType, Object params);

~~~~