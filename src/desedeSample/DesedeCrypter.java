package desedeSample;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encrypt/Decrypt by using Triple-DES
 *
 * @author ueD
 */
public class DesedeCrypter {

	private static final String CRYPT_ALGORITHM = "DESede";
	private static final String PADDING = "DESede/CBC/NoPadding";

	private static final byte[] SECRET_KEY = "6ydjai8dnfy4ud9d8dud7dud".getBytes();//24-byte
	private static final byte[] IV = "ud8dks7s".getBytes();//8-byte

	public static void main(String[] args) throws Exception {

		// 8バイトブロックに対し半角スペースで補完
		String srcText = "DesedeTest      ";
		System.out.println("srcText: " + srcText);

		DesedeCrypter crypter = new DesedeCrypter();
		// 秘密鍵
		SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY, CRYPT_ALGORITHM);
		// 初期化ベクトル
		IvParameterSpec ivSpec = new IvParameterSpec(IV);


		// 暗号化処理
		byte[] encrypted = crypter.encrypt(srcText, secretKeySpec, ivSpec);
		System.out.println("3DES: " + new String(encrypted));

		//BASE64でエンコード
		String base64EncodedText = new String(Base64.getEncoder().encode(encrypted));
		System.out.println("BASE64エンコード: " + base64EncodedText);


		// 復号化処理
		// BASE64でデコード
		String base64DecodedText = new String(Base64.getDecoder().decode(base64EncodedText));
		System.out.println("3DES: " + base64DecodedText);

		byte[] decrypted = crypter.decrypt(Base64.getDecoder().decode(base64EncodedText), secretKeySpec, ivSpec);
		System.out.println("srcText復号化: " + new String(decrypted));

	}


	/**
	 * 3DESによる暗号化を行う
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] encrypt(String value, SecretKeySpec secretKeySpec, IvParameterSpec ivSpec) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance(PADDING);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

			// 暗号化
			byte[] encrypted = cipher.doFinal(value.getBytes());
			return encrypted;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
  }

	/**
	 * 3DESによる復号化を行う
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] value, SecretKeySpec secretKeySpec, IvParameterSpec ivSpec) throws Exception {

		try {
			Cipher cipher = Cipher.getInstance(PADDING);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

			byte[] decrypted = cipher.doFinal(value);

			return decrypted;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}

