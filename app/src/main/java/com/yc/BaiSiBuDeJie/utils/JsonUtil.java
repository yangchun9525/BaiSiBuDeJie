package com.yc.BaiSiBuDeJie.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Json 工具类
 */
public class JsonUtil {

	/**
	 * 将 params 拼装到 object得到的 jsonObject
	 * @param params
	 * @return		拼装好的string
	 */
	public static String genarateJsonStr(HashMap<String, String> params) {
		return genarateJsonStr(null, params);
	}

	/**
	 * 将 params 拼装到 object得到的 jsonObject
	 * @param object
	 * @param params
	 * @return		拼装好的string
	 */
	public static String genarateJsonStr(Object object, HashMap<String, String> params) {
		
		JSONObject jsonObject = null;
		try {
			if (object != null) {
				String jsonStr = new Gson().toJson(object);
				jsonObject = new JSONObject(jsonStr);
			} else {
				jsonObject = new JSONObject();
			}
			for (Map.Entry<String, String> entry : params.entrySet()) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}

	/**
	 * 讲String转换为HashMap
	 * @param jsonStr
	 * @return
	 */
	public static HashMap<String, String> getMapFromJson(String jsonStr) {
		HashMap<String, String> data = null;
		try {
			data = new HashMap<>();
			JSONObject jsonObject = new JSONObject(jsonStr);

			Iterator it = jsonObject.keys();
			while (it.hasNext())
			{
				String key = String.valueOf(it.next());
				Object value = jsonObject.get(key);
				data.put(key, (String) value);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;
	}


	/**
	 * 从 jsonStr 中获取指定 element 对应的字符串
	 * @param jsonStr
	 * @param element
	 * @return
	 */
	public static String detectElement(String jsonStr, String element) {
		String elementStr = "";
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonStr);
			if(jsonObject.has(element)){
				elementStr = jsonObject.getString(element);
			}
		} catch (JSONException e) {
			elementStr = null;
			e.printStackTrace();
		} finally {
			return elementStr;
		}
	}

}
