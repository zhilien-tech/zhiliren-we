/**
 * Test.java
 * com.xiaoka.address
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.xiaoka.address;

import com.linyun.airline.common.util.grabmail.DownLoadPdfTransfer;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2017年5月18日 	 
 */
public class Test {
	public static void main(String[] args) {

		String resourceFile = "http://click.email.tigerair.com.au/?qs=5e61e47d7ad9a7196f05b426b6cca88969ee42cf836db1b86ed7c6cd78e76320bc7fd89b60b379510c291625f1688bcd0d822ade088d2889";
		String targetFile = "e://13.pdf";
		DownLoadPdfTransfer.downloadpdf(resourceFile, targetFile);

		/*String resourceFile = "http://click.email.tigerair.com.au/?qs=5e61e47d7ad9a7196f05b426b6cca88969ee42cf836db1b86ed7c6cd78e76320bc7fd89b60b379510c291625f1688bcd0d822ade088d2889";
		InputStream is = null;
		OutputStream os = null;
		if (!Util.isEmpty(resourceFile)) {
			try {

				URL url = new URL(resourceFile);
				URLConnection connection = url.openConnection();
				is = connection.getInputStream();
				os = new FileOutputStream(new File("E://myfile.pdf"));

				//new String(fileName.getBytes("utf-8"), "ISO8859-1")

				byte[] buffer = new byte[4096];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					os.write(buffer, 0, count);
				}
				os.flush();

			} catch (Exception e) {

				e.printStackTrace();

			} finally {
				try {
					if (!Util.isEmpty(is)) {

						is.close();
					}
				} catch (IOException e) {

					e.printStackTrace();

				}
				try {
					if (!Util.isEmpty(os)) {

						os.close();
					}
				} catch (IOException e) {

					e.printStackTrace();

				}
			}
		}*/

	}
}
