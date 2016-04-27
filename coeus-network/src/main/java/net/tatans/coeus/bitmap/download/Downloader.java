
package net.tatans.coeus.bitmap.download;


public interface Downloader  {
	
	/**
	 * ÇëÇóÍøÂçµÄinputStreamÌî³äoutputStream
	 * @param urlString
	 * @param outputStream
	 * @return
	 */
	public byte[] download(String urlString);
}
