package com.example.sampleandroid.util;

import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;

public class DeviceUuidFactory {
	protected static final String PREFS_FILE = "device_id";
	protected static final String PREFS_DEVICE_ID = "device_id";
	protected static DeviceUuidFactory uuidFactory;
	protected static UUID uuid;
	protected String strUUID;

	public static DeviceUuidFactory getInstance() {
		if (uuidFactory == null) {
			return DeviceUuidFactory.uuidFactory = new DeviceUuidFactory();
		}
		return uuidFactory;
	}

	public String getUuid(Context context) {
		if (uuid == null) {
			synchronized (DeviceUuidFactory.class) {
				if (uuid == null) {
					SharedPreferences prefs = context.getSharedPreferences(
							PREFS_FILE, 0);
					String id = prefs.getString(PREFS_DEVICE_ID, null);
					if (id != null) {
						uuid = UUID.fromString(id);
						L.v("DeviceUuidFactory","getUuid", "uuid1 = " + uuid);
					} else {
						uuid = UUID.randomUUID();
						L.v("DeviceUuidFactory","getUuid" ,"uuid2 = " + uuid);
						prefs.edit().putString("device_id", uuid.toString()).commit();
					}
				}
			}
		}
		this.strUUID = uuid.toString().replace("-", "");
		L.v("DeviceUuidFactory","getUuid" ,"strUUID = " + strUUID);
		return this.strUUID;
	}
}
