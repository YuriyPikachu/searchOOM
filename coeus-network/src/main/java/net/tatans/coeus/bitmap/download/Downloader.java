
package net.tatans.coeus.bitmap.download;


public interface Downloader  {
	
	/**
	 * ���������inputStream���outputStream
	 * @param urlString
	 * @param outputStream
	 * @return
	 */
	public byte[] download(String urlString);
}
