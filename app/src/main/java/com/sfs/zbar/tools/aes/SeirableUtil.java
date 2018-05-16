package com.sfs.zbar.tools.aes;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SeirableUtil {
	public static void saveSeriableObjectIntoFile(Serializable serializableObject, String password, String fileName,
			Context context) {
		try {
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(AESEncryptionUtil.encryptObject(serializableObject, password));
			oos.close();
			oos = null;
			fos.close();
			fos = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Serializable loadSeriableObjectFromFile(String password, String fileName, Context context) {
		Serializable serializableObject = null;
		try {
			File f = context.getFileStreamPath(fileName);
			if (f.exists()) {
				FileInputStream fis = context.openFileInput(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Serializable sealedSerializableObject = (Serializable)ois.readObject();
				if (sealedSerializableObject != null) {
					serializableObject = (Serializable)AESEncryptionUtil.decryptObject(sealedSerializableObject,
							password);
				}
				ois.close();
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serializableObject;
	}

}
