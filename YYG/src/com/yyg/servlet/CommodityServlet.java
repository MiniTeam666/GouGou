package com.yyg.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;

import com.yyg.AppConstant;
import com.yyg.ServiceManager;
import com.yyg.service.CommodityService;
import com.yyg.utils.TextUtils;

@WebServlet("/manager/addCommodity")
public class CommodityServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		LogManager.getLogger().entry();
		
		CommodityService service = (CommodityService)ServiceManager.getInstance().getService(ServiceManager.Commodity_Service);
		
		String describes = "";
		String name = "";
		String coverUrl = "";
		int price = 0,categoryID = 0;
		
		try{
			DiskFileItemFactory factory = new DiskFileItemFactory();
	
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);
	
			ServletFileUpload upload = new ServletFileUpload(factory);
	
			List<FileItem> items = upload.parseRequest(req);
			
			LogManager.getLogger().warn("item.size()" + items.size());
			
			Iterator<FileItem> it = items.iterator();
			while(it.hasNext()){
				FileItem item = it.next();
				if(item.isFormField()){
					String key = item.getFieldName();
					String value = item.getString();
					if("name".equals(key)){
						name = value;
					}else if("describes".equals(key)){
						describes = value;
					}else if("price".equals(key)){
						price = TextUtils.isEmpty(value) ? -1 : Integer.valueOf(value);
					}else if("categoryID".equals(key)){
						categoryID = TextUtils.isEmpty(value) ? -1 : Integer.valueOf(value);
					}
					LogManager.getLogger().info("key : " + key + " - > value : " + value);
				}else{
					String fieldName = item.getFieldName();
					String fileName = item.getName();
					String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
					String contentType = item.getContentType();
					long size = item.getSize();
					coverUrl = handleCoverUpload(item.getInputStream(),servletContext.getRealPath(""),suffix);
					LogManager.getLogger().info("fieldName:" + fieldName + ",fileName: " + fileName + ",size : " + size + ",type:" + contentType + ",url " + coverUrl);
				}
			}
			
			if(TextUtils.isEmpty(name) || price <= 0 || categoryID <= 0 || TextUtils.isEmpty(coverUrl)){
				onRespAddCommodityFail(resp,1);
				return ;
			}
			
			boolean ret = service.addCommodity(name, describes, coverUrl, price, categoryID);
			if(ret){
				onRespAddCommoditySuccess(resp);
			}else{
				onRespAddCommodityFail(resp,2);
			}
		
		}catch(Exception e){
			e.printStackTrace();
			onRespAddCommodityFail(resp, 3);
		}

	}
	
	public String handleCoverUpload(InputStream input,String servletContextPath,String suffix) throws Exception {
		BufferedInputStream in = new BufferedInputStream(input);
		byte[] buff = new byte[1024 * 5]; //5k
		
		String savePath = servletContextPath + AppConstant.COMMODITY_COVER_UPLOAD_DIR + System.currentTimeMillis() + suffix;
		File tmp = new File(savePath + ".tmp");
		File parent = tmp.getParentFile();
		if(!parent.exists()){
			parent.mkdirs();
		}
		
		if(tmp.exists()){
			tmp.delete();
		}
		tmp.createNewFile();
		
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tmp));
		int read = 0;
		while((read = in.read(buff)) > 0){
			out.write(buff,0,read);
			out.flush();
		}
		
		in.close();
		out.close();
		
		File outFile = new File(savePath);
		Thumbnails.of(tmp).outputQuality(0.7f).size(200, 200).keepAspectRatio(false).toFile(outFile);
		
		tmp.delete();
		return AppConstant.COMMODITY_COVER_URL_SUFFIX + outFile.getName();
	}
	
	public void onRespAddCommoditySuccess(HttpServletResponse resp){
		try{
			resp.getWriter().write("<h1>Successful ! reas");
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void onRespAddCommodityFail(HttpServletResponse resp,int reason){
		try{
			resp.getWriter().write("<h1>Fail ! reason : " + reason + "</h1>" );
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
