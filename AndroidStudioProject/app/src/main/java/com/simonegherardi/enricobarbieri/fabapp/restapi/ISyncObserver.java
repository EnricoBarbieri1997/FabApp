package com.simonegherardi.enricobarbieri.fabapp.restapi;

public interface ISyncObserver {
    public void UploadStart();
    public void UpdateStart();
    public void DownloadStart();
    public void DeleteStart();
    public void UploadComplete(boolean result);
    public void UpdateComplete(boolean result);
    public void DownloadComplete(boolean result);
    public void DeleteComplete(boolean result);
}
