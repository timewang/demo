package com.webhybird.framework.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * Created by wangzhongfu on 2015/10/14.
 */
public class ThymeleafBaseController {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    protected HttpServletResponse response;
    protected HttpServletRequest request;
    protected HttpSession session;
    protected final String SUCCESS = "success";
    /**
     * 获取HttpServletRequest对象
     *
     * @return
     */
    protected HttpServletRequest getRequest() {

        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * 获取HttpServletResponse对象
     * @author wangwg
     * @return
     */
    @ModelAttribute
    public void getRequestAndResponse(HttpServletRequest request, HttpServletResponse response, Model model){
        this.response = response;
        this.request = request;
        this.session = request.getSession();
        // http://test.lakala.com
        this.response.setHeader("Access-Control-Allow-Origin","*");
        this.response.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        this.response.addHeader("Access-Control-Allow-Credentials","true");
       /* String base = request.getContextPath();
        String fullPath = request.getScheme() + "://" + request.getServerName()
                + base;
        model.addAttribute("base", base);
        model.addAttribute("fullPath", fullPath);*/
    }

    /**
     * 工程当前真实的物理地址，weblogic war发布模式时不能获取路径
     */
    public String getRealPath() {
        String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getSession().getServletContext().getRealPath("/");
        return path;
    }

    /**
     * 获得ContextPath
     */
    public String getContextPath() {
        String path = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getSession().getServletContext().getContextPath();
        return path;
    }

/*
	*//**
     * 将上传文件的公共方法
     * 注意，由于显示上传百分比的原因，文件有可能被分为多块上传，如果返回值为null说明文件没有上传完成
     * @param dstPath
     * 				上传的目标路径
     * @param timeflag
     * 				时间戳标识，是否在文件后面追加时间戳
     * @return File
     *              file分块全部 上传完成    null分块传输未结束
     *//*
	public File uploadFile(Uploader uploader, String dstPath, boolean timeflag) throws Exception {
		String realName = uploader.getUpload().getOriginalFilename();
		String dstAllPath ="G://apache-tomcat-6.0.36-windows-x86/apache-tomcat-6.0.36/gdtemp" + "/" + dstPath + "/"
				+ FormatUtils.format(System.currentTimeMillis(), "yyyyMMdd") + "/";
		System.out.println("mmmmmmmmmmmmmmmmmmm"+dstAllPath);
		File dstFilePath = new File(dstAllPath);
		if (dstFilePath.exists() == false) {
			dstFilePath.mkdirs();
		}
		File dstFile = new File(dstAllPath + uploader.getName());
		// 文件已存在（上传了同名的文件）
		if (uploader.getChunk() == 0 && dstFile.exists()) {
			dstFile.delete();
			dstFile = new File(dstAllPath + uploader.getName());
		}
		copyFile(uploader.getUpload().getInputStream(), dstFile);
//		logger.info("上传文件:" + realName + "文件类型:" + uploader.getUploadFile().getContentType() + " "
//				+ (uploader.getChunk() + 1) + " " + uploader.getChunk());
		if (uploader.getChunk() == uploader.getChunks() - 1) {
			File file = null;
			if (timeflag)
				file = new File(dstAllPath + realName + "."
						+ FormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS"));
			else
				file = new File(dstAllPath + realName);
			if (file.exists())
				file.delete();
			dstFile.renameTo(file);
			if (dstFile.exists())
				dstFile.delete();
			return file;
		}
		return null;
	}*/

    /**
     * 将分块上传的文件整合到一个文件中
     *
     */
    private void copyFile(InputStream src, File dst) {
        int BUFFER_SIZE = 1024;
        InputStream in = null;
        OutputStream out = null;
        try {
            if (dst.exists()) {
                out = new BufferedOutputStream(new FileOutputStream(dst, true), BUFFER_SIZE);
            } else {
                out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
            }
            in = new BufferedInputStream(src, BUFFER_SIZE);

            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
