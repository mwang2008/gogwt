package com.gogwt.framework.server.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.gogwt.framework.arch.utils.StringUtils;

/**
 * <code><B>ClientInfo<code><B>
 * <p/>
 * Client information such as browser type, browserMajorVersion etc.
 * <p/>
 * 
 * @author Michael.Wang@ihg.com
 * @since Aug 16, 2007
 */

public final class ClientInfo implements Serializable {
	static final long serialVersionUID = -1L;

	// added constants for all browser codes.
	private static final String BROWSER_CODE_WEBTV = "WTV";
	private static final String BROWSER_CODE_AOL = "AOL";
	private static final String BROWSER_CODE_OPERA = "OP";
	private static final String BROWSER_CODE_NETSCAPE_4 = "N4";
	private static final String BROWSER_CODE_NETSCAPE_6 = "N6";
	private static final String BROWSER_CODE_FIREFOX = "FF";
	private static final String BROWSER_CODE_IE = "IE";
	private static final String BROWSER_CODE_SAFARI = "safari";

	private String mUserAgent;
	private String mBrowserCode;
	private String mBrowserName;
	private String mComment;
	private String mCommentType;
	private String mLayerStyle;
	private String mPlatformName;
	private String mPlatformCode;
	private float mBrowserVersion;
	private int mBrowserMajorVersion;
	private int mBrowserMinorVersion;
	private ArrayList<String> mHeaderInfo;

	private static Logger logger = Logger.getLogger(ClientInfo.class);

	private ClientInfo() {

	}

	private ClientInfo(final String pUserAgent) {
		mUserAgent = pUserAgent;
	}

	public ClientInfo(final HttpServletRequest pRequest) {
		mUserAgent = pRequest.getHeader("USER-AGENT");
		setBrowserCode(mUserAgent);
		setBrowserName(mUserAgent);
		setBrowserVersion(mUserAgent);
		setBrowserMajorVersion(getBrowserVersion());
		setBrowserMinorVersion(getBrowserVersion());
		setPlatformCode(mUserAgent);
		setPlatformName(mUserAgent);
	}

	private void createHeaderList(final String pUserAgent) {

		if (pUserAgent != null) {
			final int beg = pUserAgent.indexOf("(") + 1;
			final int end = pUserAgent.indexOf(")");
			mHeaderInfo = new ArrayList<String>();
			String tempStr = pUserAgent;
			if ((beg != -1) && (end != -1)) {
				try {
					tempStr = pUserAgent.substring(beg, end);
				} catch (final java.lang.StringIndexOutOfBoundsException e) {
					if (logger != null) {
						logger.fatal(
								"unable to get a substring from user agent = "
										+ pUserAgent, e);
					}
				}
			}
			if (tempStr != null) {
				final StringTokenizer agentTokens = new StringTokenizer(
						tempStr, "; ");
				while (agentTokens.hasMoreTokens()) {
					mHeaderInfo.add(agentTokens.nextToken());
				}
				mHeaderInfo.trimToSize();
			}
		}
	}

	/**
	 * Returns the browser code.
	 * 
	 * @return the browser code.
	 */
	public String getBrowserCode() {
		return mBrowserCode;
	}

	/**
	 * Returns the browser name.
	 * 
	 * @return the browser name.
	 */
	public String getBrowserName() {
		return mBrowserName;
	}

	// ------------------------------------

	/**
	 * Returns the browser version.
	 * 
	 * @return the browser version.
	 */
	public String getBrowserVersion() {
		return new Float(mBrowserVersion).toString();
	}

	// ------------------------------------

	/**
	 * Returns the browser major version.
	 * 
	 * @return the browser major version.
	 */
	public int getBrowserMajorVersion() {
		return mBrowserMajorVersion;
	}

	// ------------------------------------

	/**
	 * Returns the browser minor version.
	 * 
	 * @return the browser minor version.
	 */
	public int getBrowserMinorVersion() {
		return mBrowserMinorVersion;
	}

	// ------------------------------------

	/**
	 * Returns the comment.
	 * 
	 * @return the comment.
	 */
	public String getComment() {
		return mComment;
	}

	// ------------------------------------

	/**
	 * Returns the comment type.
	 * 
	 * @return the comment type.
	 */
	public String getCommentType() {
		return mCommentType;
	}

	// ------------------------------------

	/**
	 * Returns a string containing layer style.
	 * 
	 * @return a string containing layer style.
	 */
	public String getLayerStyle(final String pLayerName) {
		if (isN4()) {
			return "document.layers['" + pLayerName + "']";
		} else if (isN6()) {
			return "document.getElementsByTagName('div')." + pLayerName
					+ ".style";
		} else {
			return "document.all['" + pLayerName + "'].style";
		}
	}

	// ------------------------------------

	/**
	 * Returns the platform name.
	 * 
	 * @return the platform name.
	 */
	public String getPlatformName() {
		return mPlatformName;
	}

	// ------------------------------------

	/**
	 * Returns the platform code.
	 * 
	 * @return the platform code.
	 */
	public String getPlatformCode() {
		return mPlatformCode;
	}

	// ------------------------------------

	/**
	 * Returns true if the browser is Microsoft Internet Explorer.
	 * 
	 * @return true if the browser is Microsoft Internet Explorer.
	 */
	public boolean isIE() {
		if (this.getBrowserCode() != null) {
			return (getBrowserCode().equals(BROWSER_CODE_IE));
		}
		return false;
	}

	/**
	 * Returns true if the browser is Firefox.
	 * 
	 * @return true if the browser is Firefox.
	 */
	public boolean isFF() {
		if (this.getBrowserCode() != null) {
			return (getBrowserCode().equals(BROWSER_CODE_FIREFOX));
		}
		return false;
	}

	// ------------------------------------

	/**
	 * Returns true if the browser is Netscape Navigator.
	 * 
	 * @return true if the browser is Netscape Navigator.
	 */
	public boolean isNN() {
		if (this.getBrowserCode() != null) {
			return (getBrowserCode().equals(BROWSER_CODE_NETSCAPE_4) || getBrowserCode()
					.equals(BROWSER_CODE_NETSCAPE_6));
		}
		return false;
	}

	// ------------------------------------

	/**
	 * Returns true if the browser is Netscape Navigator 4.
	 * 
	 * @return true if the browser is Netscape Navigator 4.
	 */
	public boolean isN4() {
		if (this.getBrowserCode() != null) {
			return (getBrowserCode().equals(BROWSER_CODE_NETSCAPE_4));
		}
		return false;
	}

	// ------------------------------------

	/**
	 * Returns true if the browser is Netscape 6.
	 * 
	 * @return true if the browser is Netscape 6.
	 */
	public boolean isN6() {
		if (this.getBrowserCode() != null) {
			return (getBrowserCode().equals(BROWSER_CODE_NETSCAPE_6));
		}
		return false;
	}

	// ------------------------------------

	/**
	 * Returns true if the browser is Opera.
	 * 
	 * @return true if the browser is Opera.
	 */
	public boolean isOP() {
		if (this.getBrowserCode() != null) {
			return (getBrowserCode().equals(BROWSER_CODE_OPERA));
		}
		return false;
	}

	// ------------------------------------

	/**
	 * Returns true if the browser is America Online.
	 * 
	 * @return true if the browser is America Online.
	 */
	public boolean isAOL() {
		if (this.getBrowserCode() != null) {
			return (getBrowserCode().equals(BROWSER_CODE_AOL));
		}
		return false;
	}

	// ------------------------------------

	/**
	 * Returns true if the browser is WebTV.
	 * 
	 * @return true if the browser is WebTV.
	 */
	public boolean isWTV() {
		if (this.getBrowserCode() != null) {
			return (getBrowserCode().equals(BROWSER_CODE_WEBTV));
		}
		return false;
	}

	public boolean isSafari() {
		if (this.getBrowserCode() != null) {
			return (getBrowserCode().equals(BROWSER_CODE_SAFARI));
		}
		return false;
	}

	// ------------------------------------

	/**
	 * Returns true if the operating system is Microsoft Windows.
	 * 
	 * @return true if the operating system is Microsoft Windows.
	 */
	public boolean isWin() {
		if (mUserAgent == null) {
			return false;
		}
		return (mUserAgent.indexOf("Win") != -1);
	}

	// ------------------------------------

	/**
	 * Returns true if the operating system is Apple Macintosh OS.
	 * 
	 * @return true if the operating system is Apple Macintosh OS.
	 */
	public boolean isMac() {
		if (mUserAgent == null) {
			return false;
		}
		return (mUserAgent.indexOf("Mac") != -1);
	}

	// ------------------------------------

	/**
	 * Returns true if the operating system is Linux based.
	 * 
	 * @return true if the operating system is Linux based.
	 */
	public boolean isLinux() {
		if (mUserAgent == null) {
			return false;
		}
		return (mUserAgent.indexOf("inux") != -1);
	}

	// ------------------------------------

	/**
	 * Set the browser name.
	 * 
	 * @param pUserAgent
	 *            - the UserAgent.
	 */
	public void setBrowserName(final String pUserAgent) {
		if (pUserAgent != null) {
			if (isIE()) {
				mBrowserName = "Microsoft Internet Explorer";
			} else if (isN4()) {
				mBrowserName = "Netscape Navigator";
			} else if (isN6()) {
				mBrowserName = "Netscape";
			} else if (isOP()) {
				mBrowserName = "Opera";
			} else if (isAOL()) {
				mBrowserName = BROWSER_CODE_AOL;
			} else if (isWTV()) {
				mBrowserName = "WebTV";
			} else if (isFF()) {
				mBrowserName = "Firefox";
			}
		}
	}

	// ------------------------------------

	/**
	 * Set the browser code.
	 * 
	 * @param pUserAgent
	 *            - the UserAgent.
	 */
	public void setBrowserCode(final String pUserAgent) {
		if (pUserAgent != null) {
			final String userAgent = pUserAgent.toLowerCase();
			if (userAgent.indexOf("firefox") != -1) {
				mBrowserCode = BROWSER_CODE_FIREFOX;
			} else if (userAgent.indexOf("opera") != -1) {
				mBrowserCode = BROWSER_CODE_OPERA;
			} else if (userAgent.indexOf("msie") != -1) {
				mBrowserCode = BROWSER_CODE_IE;
			} else if (userAgent.indexOf("netscape") != -1
					&& userAgent.indexOf("gecko") != -1) {
				mBrowserCode = BROWSER_CODE_NETSCAPE_6;
			} else if (userAgent.indexOf("mozilla") == 0) {
				mBrowserCode = BROWSER_CODE_NETSCAPE_4;
			} else if (userAgent.indexOf("aol") != -1) {
				mBrowserCode = BROWSER_CODE_AOL;
			} else if (userAgent.indexOf("webtv") != -1) {
				mBrowserCode = BROWSER_CODE_WEBTV;
			} else if (userAgent.indexOf("webkit") != -1) {
				mBrowserCode = BROWSER_CODE_SAFARI;
			} else {
				mBrowserCode = BROWSER_CODE_IE;
			}
		}
	}

	// ------------------------------------

	/**
	 * Set the browser major version.
	 * 
	 * @param pBrowserVersion
	 *            - the browser version.
	 */
	public void setBrowserMajorVersion(final String pBrowserVersion) {
		mBrowserMajorVersion = Integer.parseInt(pBrowserVersion.substring(0,
				pBrowserVersion.indexOf(".")));
	}

	// ------------------------------------

	/**
	 * Set the browser minor version.
	 * 
	 * @param pBrowserVersion
	 *            - the browser version.
	 */
	public void setBrowserMinorVersion(final String pBrowserVersion) {
		try {
			mBrowserMinorVersion = Integer
					.parseInt(pBrowserVersion.substring(
							pBrowserVersion.indexOf(".") + 1,
							pBrowserVersion.length()));
		} catch (final Exception e) {
		}
	}

	// ------------------------------------

	/**
	 * Set the comment.
	 * 
	 * @param pComment
	 *            - the comment.
	 */
	public void setComment(final String pComment) {
		mComment = pComment;
	}

	// ------------------------------------

	/**
	 * Set the comment type.
	 * 
	 * @param pCommentType
	 *            - the comment type.
	 */
	public void setCommentType(final String pCommentType) {
		mCommentType = pCommentType;
	}

	// ------------------------------------

	/**
	 * Set the browser version.
	 * 
	 * @param pUserAgent
	 *            - the UserAgent.
	 */
	public void setBrowserVersion(final String pUserAgent) {
		createHeaderList(pUserAgent);
		String temp = "0.0";
		Float tempFloat;
		if (pUserAgent != null) {
			if (isIE()) {
				final int arrayListSize = mHeaderInfo.size();
				for (int i = 0; i < arrayListSize; i++) {
					final String cur = mHeaderInfo.get(i);
					if ((cur.indexOf("MSIE") != -1)
							&& ((i + 1) < arrayListSize)) {
						temp = mHeaderInfo.get(i + 1);
					}
				}
			} else if (isN4()) {
				if (pUserAgent.length() > 8 && pUserAgent.indexOf(" ") != -1) {
					temp = pUserAgent.substring(8, pUserAgent.indexOf(" "));
				}
			} else if (isN6() || isFF()) {
				if (pUserAgent.lastIndexOf("/") != -1) {
					temp = pUserAgent.substring(
							pUserAgent.lastIndexOf("/") + 1,
							pUserAgent.length());
				}
			}
		}
		if (StringUtils.isSet(temp)) {
			if (!StringUtils.hasOnlyDigitsWithDot(temp)) {
				temp = StringUtils.removeNonDigitsExceptDot(temp);
			}
			try {
				if (StringUtils.isSet(temp)) {
					tempFloat = Float.valueOf(temp);
					mBrowserVersion = tempFloat.floatValue();
				}
			} catch (final NumberFormatException nfe) {
				// ignore, need to load the page
			}
		}
	}

	// ------------------------------------

	/**
	 * Set the platform name.
	 * 
	 * @param pUserAgent
	 *            - the UserAgent.
	 */
	public void setPlatformName(final String pUserAgent) {
		if (pUserAgent != null) {
			if (pUserAgent.indexOf("NT 5.0") != -1) {
				mPlatformName = "Microsoft Windows 2000";
			} else if (pUserAgent.indexOf("NT 4.0") != -1) {
				mPlatformName = "Microsoft Windows NT";
			} else if (pUserAgent.indexOf("NT 5.1") != -1) {
				mPlatformName = "Microsoft Windows XP";
			}
		}
	}

	// ------------------------------------

	/**
	 * Set the platform code.
	 * 
	 * @param pUserAgent
	 *            - the UserAgent.
	 */
	public void setPlatformCode(final String pUserAgent) {
		if (pUserAgent != null) {
			if (pUserAgent.indexOf("NT 5.0") != -1) {
				mPlatformCode = "2K";
			} else if (pUserAgent.indexOf("NT 4.0") != -1) {
				mPlatformCode = "NT";
			} else if (pUserAgent.indexOf("NT 5.1") != -1) {
				mPlatformCode = "XP";
			} else if (pUserAgent.indexOf("Windows 98") != -1) {
				mPlatformCode = "98";
			} else if (pUserAgent.indexOf("Win32") != -1) {
				mPlatformCode = "95";
			} else if (pUserAgent.indexOf("9x 4.9") != -1) {
				mPlatformCode = "ME";
			}
		}
	}

	@Override
	public String toString() {
		final StringBuffer toStringBuffer = new StringBuffer();
		toStringBuffer.append("mUserAgent: " + mUserAgent);
		toStringBuffer.append("mBrowserCode: " + mBrowserCode);
		toStringBuffer.append("mBrowserName: " + mBrowserName);
		toStringBuffer.append("mComment: " + mComment);
		toStringBuffer.append("mCommentType: " + mCommentType);
		toStringBuffer.append("mLayerStyle: " + mLayerStyle);
		toStringBuffer.append("mPlatformName: " + mPlatformName);
		toStringBuffer.append("mPlatformCode: " + mPlatformCode);
		toStringBuffer.append("mBrowserVersion: " + mBrowserVersion);
		toStringBuffer.append("mBrowserMajorVersion: " + mBrowserMajorVersion);
		toStringBuffer.append("mBrowserMinorVersion: " + mBrowserMinorVersion);
		toStringBuffer.append("mHeaderInfo: " + mHeaderInfo);
		return toStringBuffer.toString();
	}
}
