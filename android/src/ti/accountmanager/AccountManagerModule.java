package ti.accountmanager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.kroll.common.TiConfig;
import org.appcelerator.titanium.TiApplication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;

@Kroll.module(name = "AccountManager", id = "ti.accountmanager")
public class AccountManagerModule extends KrollModule {

	private static final String ACCOUNT_NAME = "name";
	private static final String ACCOUNT_TYPE = "type";
	private static final String ACCOUNT_TYPE_LABEL = "accountType";

	// Standard Debugging variables
	private static final String LCAT = "Accman2Module";
	private static AccountManager mAccountManager;

	private static Map<String, AuthenticatorDescription> _authenticatorLookup;
	private static Resources _resources;
	private static PackageManager mPackageManager;

	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;

	public AccountManagerModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {

		mPackageManager = app.getPackageManager();
		_resources = app.getResources();
		mAccountManager = AccountManager.get(app.getApplicationContext());
		AuthenticatorDescription[] accTypes = mAccountManager.getAuthenticatorTypes();
		_authenticatorLookup = new HashMap<String, AuthenticatorDescription>();
		for (AuthenticatorDescription authDesc : accTypes) {
			_authenticatorLookup.put(authDesc.type, authDesc);
		}
		Log.d(LCAT, "inside onAppCreate");
	}

	@Kroll.method
	public KrollDict[] getAccounts() {
		Account[] accounts = mAccountManager.getAccounts();
		KrollDict[] accountList = new KrollDict[accounts.length];
		int idx = 0;
		for (Account account : accounts) {
			KrollDict accountDict = new KrollDict();
			accountDict.put(ACCOUNT_NAME, account.name);
			accountDict.put(ACCOUNT_TYPE, account.type);
			accountDict.put(ACCOUNT_TYPE_LABEL,
					labelForAuthenticator(_authenticatorLookup
							.get(account.type)));
			accountList[idx] = accountDict;
			idx++;
		}
		return accountList;
	}

	@Kroll.method
	public String getAuthToken(String accountName, String accountType,
			HashMap<String,Object> params) {
		Account[] accounts = mAccountManager.getAccountsByType(accountType);
		for (Account account : accounts) {
			if (accountName.equals(account.name)) {
				String token = null;
				try {
					Log.e(LCAT, "Trying to get token for " + accountName);
					token = mAccountManager.blockingGetAuthToken(account, "", true);
					Log.e(LCAT, "After blocking call");
				} catch (Exception e) {
				
					e.printStackTrace();

				}
				Log.e(LCAT, "Token returned" + token);
				return token;
			}
		}
		return null;
	}

	private String labelForAuthenticator(
			AuthenticatorDescription authenticatorDescription) {
		Resources resources;
		try {
			resources = mPackageManager
					.getResourcesForApplication(authenticatorDescription.packageName);
		} catch (NameNotFoundException e) {
			return authenticatorDescription.type;
		}
		return resources.getString(authenticatorDescription.labelId);
	}

}
