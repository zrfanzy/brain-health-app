package osshelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

public class Helper{
	static String accessKeyId = "DpiakUbQrQMecsBk";
    static String accessKeySecret = "7nDDuqqZQJo16uGuB1YopyRNnabydq";
    static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    static String bucketName = "brain-health-app";
    OSSClient client;
	
	public void Init() {
        client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
    }
	
	public void PutObject(String key, String filePath) throws FileNotFoundException
	{
	    File file = new File(filePath);
	    InputStream content = new FileInputStream(file);
	    ObjectMetadata meta = new ObjectMetadata();
	    meta.setContentLength(file.length());
	    PutObjectResult result = client.putObject(bucketName, key, content, meta);

	    System.out.println(result.getETag());
	}
	
	public void DeleteObject(String key)
	{
	    client.deleteObject(bucketName, key);
	}
	
	public String GetObject(String key) throws IOException
	{
		OSSObject object = client.getObject(bucketName, key);
	    //ObjectMetadata meta = object.getObjectMetadata();
	    InputStream objectContent = object.getObjectContent();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(objectContent));   
        StringBuilder sb = new StringBuilder();   
        String line = null;   
        
        try
        {
            while ((line = reader.readLine()) != null)  
                sb.append(line + "\n");   
        }
        catch (IOException e)  
        {
            e.printStackTrace(); 
        }
        
        objectContent.close();
        return sb.toString();   
	}
	
	public Boolean CheckID(String testerID, String paticipanterID) throws IOException {
		String maps = GetObject("00_Config/IDmap.txt");
		String[] IDmaps = maps.split("\n");
		for (int i = 0; i < IDmaps.length; ++i)
		{
			int f1 = IDmaps[i].indexOf(testerID);
			int f2 = IDmaps[i].indexOf(paticipanterID);
			
			if (f1 > -1 && f2 > -1 && f1 < f2)
				return true;
		}
		return false;
	}
}
