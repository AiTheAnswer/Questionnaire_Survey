package com.allen.questionnaire.service.model;

import android.annotation.SuppressLint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Resp {
	private static final long serialVersionUID = 1L;
	private String status = "success";
	private String reason;
	private Map<String, String> attrs = new HashMap<>();

    private String videoService;

	public Resp() {
	}

	public Resp(String status) {
		this.status = status;
	}

	public Resp(String status, String reason) {
		this.status = status;
		this.reason = reason;
	}

	public boolean OK() {
		return "success".equals(this.status);
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Map<String, String> getAttrs() {
		return this.attrs;
	}

	public void setAttrs(Map<String, String> attrs) {
		this.attrs = attrs;
    }

    public String getVideoService() {
        return videoService;
    }

    public void setVideoService(String videoService) {
        this.videoService = videoService;
    }

	@SuppressLint("DefaultLocale")
	public String toString() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("status", this.status);
			jsonObject.put("reason", this.reason);
			return jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return "{\"status\":\"error\"}";
		}
	}
}