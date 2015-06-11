package com.example.sampleandroid.http;

import android.os.Bundle;
import android.webkit.WebSettings;

/**
 * Display details of a SSL cert
 */
public class SSLCertsViewActivity extends WebViewActivity {
	public static final String CERT_DETAILS_KEYS = "CertDetails";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.containsKey(CERT_DETAILS_KEYS)) {
			String certDetails = extras.getString(CERT_DETAILS_KEYS);
			StringBuilder sb = new StringBuilder("<html><body>");
			sb.append(certDetails);
			sb.append("</body></html>");
			WebSettings settings = mWebView.getSettings();
			settings.setDefaultTextEncodingName("utf-8");
			mWebView.loadDataWithBaseURL(null, sb.toString(), "text/html",
					"utf-8", null);
		}
	}
}
