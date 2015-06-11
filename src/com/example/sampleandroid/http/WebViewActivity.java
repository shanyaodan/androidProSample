package com.example.sampleandroid.http;

import com.example.sampleandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;

/**
 * Basic activity for displaying a WebView.
 */
public abstract class WebViewActivity extends Activity {
	/** Primary webview used to display content. */

	private static final String URL = "url";

	protected WebView mWebView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// clear title text so there's no title until actual web page title can
		// be shown
		// this is done here rather than in the manifest to automatically handle
		// descendants
		// such as AuthenticatedWebViewActivity
		setTitle("");

		setContentView(R.layout.webview);

		// note: do NOT call
		// mWebView.getSettings().setUserAgentString(WordPress.getUserAgent())
		// here since it causes problems with the browser-sniffing that some
		// sites rely on to
		// format the page for mobile display
		mWebView = (WebView) findViewById(R.id.webView);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

		// load URL if one was provided in the intent
		String url = getIntent().getStringExtra(URL);
		if (url != null) {
			loadUrl(url);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Flash video may keep playing if the webView isn't paused here
		pauseWebView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		resumeWebView();
	}

	private void pauseWebView() {
		if (mWebView != null) {
			mWebView.onPause();
		}
	}

	private void resumeWebView() {
		if (mWebView != null) {
			mWebView.onResume();
		}
	}

	/**
	 * Load the specified URL in the webview.
	 *
	 * @param url
	 *            URL to load in the webview.
	 */
	protected void loadUrl(String url) {
		mWebView.loadUrl(url);
	}

	@Override
	public void onBackPressed() {
		if (mWebView != null && mWebView.canGoBack())
			mWebView.goBack();
		else
			super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
