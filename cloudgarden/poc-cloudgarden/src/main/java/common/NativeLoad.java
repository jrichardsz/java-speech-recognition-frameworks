package common;

import java.io.*;

public class NativeLoad{

	public static void loadJSAPI() throws Exception{

		String libname="cgjsapi170.dll";

		try{
			InputStream input=NativeLoad.class.getResourceAsStream("/com/cgjsapi/lib/" + libname);
			File temp=File.createTempFile("cgjsapi170-",".dll");
			temp.deleteOnExit();
			OutputStream out=new FileOutputStream(temp);
			byte[] buffer=new byte[1024];
			int read;
			while((read=input.read(buffer)) != -1)
				out.write(buffer,0,read);
			input.close();
			out.close();

			//System.loadLibrary(temp.getAbsolutePath());
		}
		catch(Exception e){
			throw new Exception("Failed to load library:" + libname,e);
		}
	}

}
