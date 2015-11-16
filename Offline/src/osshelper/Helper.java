package osshelper;

import utility.QuestionItemSet;
import android.util.Log;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.model.AuthenticationType;
import com.alibaba.sdk.android.oss.model.ClientConfiguration;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.alibaba.sdk.android.oss.model.TokenGenerator;
import com.alibaba.sdk.android.oss.storage.OSSBucket;
import com.alibaba.sdk.android.oss.storage.OSSData;
import com.alibaba.sdk.android.oss.util.OSSToolKit;

public class Helper {
	static String accessKey = "DpiakUbQrQMecsBk";
    static String secretKey = "7nDDuqqZQJo16uGuB1YopyRNnabydq";
    static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    static String bucketName = "brain-health-app";
    public OSSService ossService;
    OSSBucket bucket;
    QuestionItemSet questionItemSet;
	
	public void Init() {
		
		ossService.setGlobalDefaultHostId("oss-cn-beijing.aliyuncs.com");
		long epoch = System.currentTimeMillis() / 1000;
		ossService.setCustomStandardTimeWithEpochSec(epoch);
		
		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectTimeout(15 * 1000);
		conf.setSocketTimeout(15 * 1000);
		conf.setMaxConnections(50);
		ossService.setClientConfiguration(conf);
		
		ossService.setAuthenticationType(AuthenticationType.ORIGIN_AKSK);
		ossService.setGlobalDefaultTokenGenerator(new TokenGenerator() {
	        @Override
	        public String generateToken(String httpMethod, String md5, String type, String date, String ossHeaders,
	                String resource) {
	            String content = httpMethod + "\n" + md5 + "\n" + type + "\n" + date
	                    + "\n" + ossHeaders + resource;

	            return OSSToolKit.generateToken(accessKey, secretKey, content);
	        }
	    });
		
		bucket = ossService.getOssBucket(bucketName);
    }
	
	public void PutObject(String key, byte[] data) throws OSSException
	{
		OSSData ossData = ossService.getOssData(bucket, key);
		ossData.setData(data, "raw"); // 指定需要上传的数据和它的类型
		ossData.enableUploadCheckMd5sum(); // 开启上传MD5校验
		ossData.upload();
	}
	
	public void DeleteObject(String key) throws OSSException
	{
		OSSData ossData = ossService.getOssData(bucket, key); // 构造OSSData实例
		ossData.delete();
	}
	
	public String GetObject(String key) throws OSSException
	{
		OSSData ossData = ossService.getOssData(bucket, key);
		Log.d("test", key);
		byte[] data = ossData.get();
	    
        return new String(data);   
	}

	public byte[] GetObjectData(String key) throws OSSException
	{
		OSSData ossData = ossService.getOssData(bucket, key);
		Log.d("test", key);
		byte[] data = ossData.get();

		return data;
	}

	public Boolean CheckID(String testerID, String participanterID) throws OSSException {
		if (testerID.length() == 0 || participanterID.length() == 0)
			return false;
		String maps = GetObject("00_Config/IDmap.txt");
		String[] IDmaps = maps.split("\n");
		for (int i = 0; i < IDmaps.length; ++i)
		{
			String[] IDs = IDmaps[i].split("\t");
			if (testerID.equals(IDs[0]) && participanterID.equals(IDs[1]))
				return true;
		}
		return false;
	}
}
