package net.tatans.coeus.network.tools;

public abstract class TatansSpeakerCallback {
	public void onSpeakBegin() {}
	public void onSpeakPaused() {}
	public void onSpeakResumed() {}
	public void onSpeakProgress(int percent, int beginPos, int endPos) {}
	public void onCompleted() {}
	public void onCompletedError() {}
}
