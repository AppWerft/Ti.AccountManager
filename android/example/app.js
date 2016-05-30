! function() {
	var win = Ti.UI.createWindow({
		backgroundColor : 'white'
	});
	win.addEventListener('open', function() {
		require('vendor/permissions').requestPermissions('GET_ACCOUNTS', function(_success) {
			if (_success == true) {
				var AcMan = require('ti.accountmanager');
				var mAccounts = AcMan.getAccounts();
				console.log(mAccounts);
				var mTokens = myAccounts.map(function(a) {
					return accountmodule.getAuthToken(a.name, a.type, {});
				});
			}
		});

	});
	win.open();
}();
