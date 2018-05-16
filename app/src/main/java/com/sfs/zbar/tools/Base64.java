package com.sfs.zbar.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Base64 {
	private static final char[] base64 = new String(
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/")
			.toCharArray();

	private static char pad1 = '=';

	private static String pad2 = "==";

	private static final byte[] reverse = { -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1,
			-1, 64, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
			-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41,
			42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };

	public static String encode(byte[] input) {
		if (input.length == 0) {
			return "";
		}
		int i = (input.length + 2) / 3 << 2;
		StringBuffer output = new StringBuffer(i);
		i = input.length / 3;
		int j = 0;
		for (; i > 0; --i) {
			int a = input[(j++)];
			int b = input[(j++)];
			int c = input[(j++)];
			int m = a >>> 2 & 0x3F;
			output.append(base64[m]);
			m = (a & 0x3) << 4 | b >>> 4 & 0xF;
			output.append(base64[m]);
			m = (b & 0xF) << 2 | c >>> 6 & 0x3;
			output.append(base64[m]);
			m = c & 0x3F;
			output.append(base64[m]);
		}

		i = input.length % 3;
		int a, m;
		switch (i) {
		case 1:
			a = input[(j++)];
			m = a >>> 2 & 0x3F;
			output.append(base64[m]);
			m = (a & 0x3) << 4;
			output.append(base64[m]);
			output.append(pad2);
			break;
		case 2:
			a = input[(j++)];
			int b = input[(j++)];
			m = a >>> 2 & 0x3F;
			output.append(base64[m]);
			m = (a & 0x3) << 4 | b >>> 4 & 0xF;
			output.append(base64[m]);
			m = (b & 0xF) << 2;
			output.append(base64[m]);
			output.append(pad1);
		}

		return output.toString();
	}

	public static byte[] decode(String input) throws Exception {
		if (input.length() == 0) {
			return new byte[0];
		}
		byte[] b = new byte[input.length()];
		for (int i = input.length() - 1; i >= 0; --i) {
			b[i] = (byte) input.charAt(i);
		}

		return decode(b);
	}

	public static byte[] decode(byte[] code) throws Exception {
		int l = code.length;
		boolean end = false;
		int i = 0;
		int j = 0;
		for (; i < l; ++i) {
			byte m = reverse[code[i]];
			if (m == 64) {
				if (end) {
					break;
				}

				end = true;
			} else {
				if (end) {
					throw new Exception("Cannot found second char!");
				}
				if (m == -1)
					continue;
				code[(j++)] = m;
			}

		}

		l = j >> 2;
		i = l * 3;
		int k = j & 0x3;
		if (k == 1) {
			throw new Exception("Cannot found first char!");
		}
		if (k > 0) {
			i = i + k - 1;
		}
		byte[] output = new byte[i];
		i = 0;
		j = 0;
		byte b = 0;
		for (; l > 0; --l) {
			byte a = code[(i++)];
			b = code[(i++)];
			byte c = code[(i++)];
			byte d = code[(i++)];
			output[(j++)] = (byte) (a << 2 | b >>> 4 & 0x3);
			output[(j++)] = (byte) ((b & 0xF) << 4 | c >>> 2 & 0xF);
			output[(j++)] = (byte) ((c & 0x3) << 6 | d);
		}

		if (k >= 2) {
			byte a = code[(i++)];
			b = code[(i++)];
			output[(j++)] = (byte) (a << 2 | b >>> 4 & 0x3);
		}
		if (k >= 3) {
			byte c = code[(i++)];
			output[(j++)] = (byte) ((b & 0xF) << 4 | c >>> 2 & 0xF);
		}
		return output;
	}

	public static String toHex(byte[] b) {
		StringBuffer buf = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; ++i) {
			buf.append("0123456789abcdef".charAt(b[i] >> 4 & 0xF));
			buf.append("0123456789abcdef".charAt(b[i] & 0xF));
		}

		return buf.toString();
	}

	public static byte[] readFileToByteArray(File file) throws IOException {
		InputStream in = null;
		try {
			if (file.exists()) {
				if (file.isDirectory()) {
					throw new IOException("File '" + file
							+ "' exists but is a directory");
				}
				if (!file.canRead())
					throw new IOException("File '" + file + "' cannot be read");
			} else {
				throw new FileNotFoundException("File '" + file
						+ "' does not exist");
			}
			in = new FileInputStream(file);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = in.read(buffer))) {
				output.write(buffer, 0, n);
			}
			return output.toByteArray();
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
		}
	}
}