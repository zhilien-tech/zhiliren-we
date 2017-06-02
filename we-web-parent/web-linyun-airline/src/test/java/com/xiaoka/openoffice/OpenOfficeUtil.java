/**
 * OpenOfficeUtil.java
 * com.xiaoka.openoffice
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.openoffice;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.sun.star.awt.Size;
import com.sun.star.beans.PropertyValue;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.view.PaperFormat;
import com.sun.star.view.XPrintable;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年5月31日 	 
 */
public class OpenOfficeUtil extends OpenOfficeDocumentConverter {
	public OpenOfficeUtil(OpenOfficeConnection connection) {
		/*  64 */super(connection);
		/*     */}

	public final static Size A5, A4, A3;
	public final static Size B4, B5, B6;
	public final static Size KaoqinReport;

	static {
		A5 = new Size(14800, 21000);
		A4 = new Size(21000, 29700);
		A3 = new Size(29700, 42000);

		B4 = new Size(25000, 35300);
		B5 = new Size(17600, 25000);
		B6 = new Size(12500, 17600);

		KaoqinReport = new Size(25400, 27940);
	}

	/* 
	     * XComponent:xCalcComponent 
	     *  
	     * @seecom.artofsolving.jodconverter.openoffice.converter. 
	     * AbstractOpenOfficeDocumentConverter 
	     * #refreshDocument(com.sun.star.lang.XComponent) 
	     */
	@Override
	protected void refreshDocument(XComponent document) {
		super.refreshDocument(document);

		// The default paper format and orientation is A4 and portrait. To  
		// change paper orientation  
		// re set page size  
		XPrintable xPrintable = (XPrintable) UnoRuntime.queryInterface(XPrintable.class, document);
		PropertyValue[] printerDesc = new PropertyValue[2];

		// Paper Orientation  
		//      printerDesc[0] = new PropertyValue();  
		//      printerDesc[0].Name = "PaperOrientation";  
		//      printerDesc[0].Value = PaperOrientation.PORTRAIT;  

		// Paper Format  
		printerDesc[0] = new PropertyValue();
		printerDesc[0].Name = "PaperFormat";
		printerDesc[0].Value = PaperFormat.USER;

		// Paper Size  
		printerDesc[1] = new PropertyValue();
		printerDesc[1].Name = "PaperSize";
		printerDesc[1].Value = KaoqinReport;

		try {
			xPrintable.setPrinter(printerDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
