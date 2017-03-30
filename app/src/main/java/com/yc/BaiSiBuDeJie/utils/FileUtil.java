package com.yc.baisibudejie.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yangchun on 2015/5/28.
 * 文件操作工具类
 */
public class FileUtil {

    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

    //{文件头信息, 文件后缀信息}
    public static final HashMap<String, String> mFileTypes = new HashMap();
    //{后缀名，MIME类型}
    public static final HashMap<String, String> mOpenFileTypes = new HashMap();
    //视频文件的后缀
    public static final ArrayList<String> mVideoSuffix = new ArrayList<>();
    //音频文件的后缀
    public static final ArrayList<String> mAudioSuffix = new ArrayList<>();
    //图片文件的后缀
    public static final ArrayList<String> mImgSuffix = new ArrayList<>();
    static {
        //vedio
        mVideoSuffix.add("aiff");
        mVideoSuffix.add("avi");
        mVideoSuffix.add("mov");
        mVideoSuffix.add("mpeg");
        mVideoSuffix.add("mpg");
        mVideoSuffix.add("qt");
        mVideoSuffix.add("ram");
        mVideoSuffix.add("rm");
        mVideoSuffix.add("wmv");
        mVideoSuffix.add("saf");
        mVideoSuffix.add("dat");
        mVideoSuffix.add("asx");
        mVideoSuffix.add("wvx");
        mVideoSuffix.add("saf");
        mVideoSuffix.add("mpe");
        mVideoSuffix.add("mpa");
        mVideoSuffix.add("mp4");

        //audio
        mAudioSuffix.add("mp3");
        mAudioSuffix.add("rm");
        mAudioSuffix.add("wmv");
        mAudioSuffix.add("acm");
        mAudioSuffix.add("aif");
        mAudioSuffix.add("aifc");
        mAudioSuffix.add("aiff");
        mAudioSuffix.add("asf");
        mAudioSuffix.add("asp");
        mAudioSuffix.add("amr");

        //image
        mImgSuffix.add("jpg");
        mImgSuffix.add("png");
        mImgSuffix.add("jpeg");
        mImgSuffix.add("bmp");
        mImgSuffix.add("dwg");
        mImgSuffix.add("psd");

        // images
        mFileTypes.put("FFD8FFE1", "jpg");
        mFileTypes.put("89504E47", "png");
        mFileTypes.put("47494638", "gif");
        mFileTypes.put("424D36BB", "bmp");
        mFileTypes.put("41433130", "dwg");   // CAD
        mFileTypes.put("38425053", "psd");   // Adobe Photoshop
        // text
        mFileTypes.put("25504446", "pdf");
        mFileTypes.put("504B0304", "docx/xlsx/pptx");
        mFileTypes.put("D0CF11E0", "doc/xls/ppt");
        mFileTypes.put("2F2F2F2F", "txt");
        mFileTypes.put("7B5C7274", "rtf"); 	// 日记本
        // 存储
        mFileTypes.put("52617221", "rar");
        mFileTypes.put("504B0304", "zip");
        mFileTypes.put("377ABCAF", "7z");

        // 网页
        mFileTypes.put("3C3F786D", "xml");
        mFileTypes.put("68746D6C", "html");

        // 多媒体（视频/音频）
        mFileTypes.put("57415645", "wav");  // Wave
        mFileTypes.put("41564920", "avi");  // AVI
        mFileTypes.put("2E524D46", "rm");   // Real Media
        mFileTypes.put("2E7261FD", "ram");  // Real Audio
        mFileTypes.put("000001BA", "mpg");  // MPEG
        mFileTypes.put("000001B3", "mpg");  // MPEG
        mFileTypes.put("6D6F6F76", "mov");  // Quicktime
        mFileTypes.put("3026B275", "asf");  // Windows Media
        mFileTypes.put("4D546864", "mid");  // MIDI
        mFileTypes.put("49443303", "mp3");  // mp3

        // 其它
        mFileTypes.put("44656C6", "eml"); 	// 邮件
        mFileTypes.put("5374616", "mdb"); 	// MS Access
        mFileTypes.put("2521505", "ps/eps"); // Postscript(eps.or.ps)

        // images
        mOpenFileTypes.put("gif", "image/gif");
        mOpenFileTypes.put("bmp", "image/bmp");
        mOpenFileTypes.put("jpeg", "image/jpeg");
        mOpenFileTypes.put("jpg", "image/jpeg");
        mOpenFileTypes.put("png", "image/png");
        // text
        mOpenFileTypes.put("htm", "text/html");
        mOpenFileTypes.put("html", "text/html");
        mOpenFileTypes.put("h", "text/plain");
        mOpenFileTypes.put("c", "text/plain");
        mOpenFileTypes.put("conf", "text/plain");
        mOpenFileTypes.put("cpp", "text/plain");
        mOpenFileTypes.put("prop", "text/plain");
        mOpenFileTypes.put("rc", "text/plain");
        mOpenFileTypes.put("log", "text/plain");
        mOpenFileTypes.put("java", "text/plain");
        mOpenFileTypes.put("sh", "text/plain");
        mOpenFileTypes.put("txt", "text/plain");
        mOpenFileTypes.put("xml", "text/plain");
        mOpenFileTypes.put("class", "application/octet-stream");
        mOpenFileTypes.put("pdf", "application/pdf");
        mOpenFileTypes.put("chm", "application/x-chm");
        mOpenFileTypes.put("doc", "application/msword");
        mOpenFileTypes.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mOpenFileTypes.put("xls", "application/vnd.ms-excel");
        mOpenFileTypes.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        mOpenFileTypes.put("ppt", "application/vnd.ms-powerpoint");
        mOpenFileTypes.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        mOpenFileTypes.put("wps", "application/vnd.ms-works");
        // 存储
        mOpenFileTypes.put("gtar", "application/x-gtar");
        mOpenFileTypes.put("gz", "application/x-gzip");
        mOpenFileTypes.put("tar", "application/x-tar");
        mOpenFileTypes.put("tgz", "application/x-compressed");
        mOpenFileTypes.put("zip", "application/x-zip-compressed");
        mOpenFileTypes.put("rar", "application/x-rar-compressed");
        mOpenFileTypes.put("z", "application/x-compress");
        // 多媒体（视频/音频）
        mOpenFileTypes.put("3gp", "video/3gpp");
        mOpenFileTypes.put("asf", "video/x-ms-asf");
        mOpenFileTypes.put("avi", "video/x-msvideo");
        mOpenFileTypes.put("m3u", "audio/x-mpegurl");
        mOpenFileTypes.put("m4a", "audio/mp4a-latm");
        mOpenFileTypes.put("m4b", "audio/mp4a-latm");
        mOpenFileTypes.put("m4p", "audio/mp4a-latm");
        mOpenFileTypes.put("m4u", "video/vnd.mpegurl");
        mOpenFileTypes.put("m4v", "video/x-m4v");
        mOpenFileTypes.put("mov", "video/quicktime");
        mOpenFileTypes.put("mp2", "audio/x-mpeg");
        mOpenFileTypes.put("mp3", "audio/x-mpeg");
        mOpenFileTypes.put("mp4", "video/mp4");
        mOpenFileTypes.put("mpe", "video/mpeg");
        mOpenFileTypes.put("mpeg", "video/mpeg");
        mOpenFileTypes.put("mpg", "video/mpeg");
        mOpenFileTypes.put("mpg4", "video/mp4");
        mOpenFileTypes.put("mpga", "audio/mpeg");
        mOpenFileTypes.put("ogg", "audio/ogg");
        mOpenFileTypes.put("wav", "audio/x-wav");
        mOpenFileTypes.put("wma", "audio/x-ms-wma");
        mOpenFileTypes.put("wmv", "audio/x-ms-wmv");
        mOpenFileTypes.put("rmvb", "audio/x-pn-realaudio");
        // 其它
        mOpenFileTypes.put("apk", "application/vnd.android.package-archive");
        mOpenFileTypes.put("bin", "application/octet-stream");
        mOpenFileTypes.put("exe", "application/octet-stream");
        mOpenFileTypes.put("jar", "application/java-archive");
        mOpenFileTypes.put("js", "application/x-javascript");
        mOpenFileTypes.put("mpc", "application/vnd.mpohun.certificate");
        mOpenFileTypes.put("msg", "application/vnd.ms-outlook");
        mOpenFileTypes.put("pps", "application/vnd.ms-powerpoint");
        mOpenFileTypes.put("rtf", "application/rtf");
        mOpenFileTypes.put(".", "*/*");
    }

    /**
     * 判断SD卡是否存在
     * @return
     */
    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 判断文件或目录是否存在
     * @param path
     * @return
     */
    public static boolean isFileExist(String path) {
        return new File(path).exists();
    }

    /**
     * 创建目录
     * @param dir
     */
    public static File createDirectory(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 打开指定文件，如果路径为空的，则会先创建父目录
     * @param dir
     * @return
     */
    public static File createFile(String dir) {
        File file = new File(dir);
        createDirectory(file.getParentFile().getPath());
        return file;
    }

    /**
     * 删除指定路径的文件或目录，且删除自身目录
     * @param path
     */
    public static void deletePath(String path) {
        deletePath(path, true);
    }

    /**
     * 删除指定路径的文件或目录
     * @param path
     * @param deleteSelf
     */
    public static void deletePath(String path, boolean deleteSelf) {
        if(ValidatesUtil.isEmpty(path)) {
            return;
        }

        File file = new File(path);
        // 不存在
        if(!file.exists()) {
            return;
        }
        // 是文件
        if(file.isFile()) {
            file.delete();
        }
        // 是目录
        if(file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if(childFiles == null || childFiles.length == 0) {
                return;
            }
            for(File childFile : childFiles) {
                deletePath(childFile.getPath());
            }
            if(deleteSelf) {
                file.delete();
            }
        }
    }

    /**
     * 取得不含后缀的文件名
     * @param file
     * @return
     */
    public static String getNameNoSuffix(File file) {
        if(file == null) {
            return null;
        }
        String name = file.getName();
        int index = name.lastIndexOf(".");
        if(index > 0) {
            return name.substring(0, index);
        }
        return name;
    }

    /**
     * 将sourcePath拷贝到destPath
     * @param sourcePath
     * @param destPath
     * @return
     */
    public static boolean copyFile(String sourcePath, String destPath) {
        File sourceFile = new File(sourcePath);
        File destFile = createFile(destPath);
        // 原文件不存在
        if(!sourceFile.exists()) {
            return false;
        }
        // 复制
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            byte[] buffer = new byte[1024 * 32];    // 32K 缓存
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(destFile);
            int count;
            while ((count = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 拷贝Asset目录中内容到destPath，新文件名为文件url的hashCode值
     * @param context
     * @param assetPath
     * @param destPath
     */
    public static void copyAssetDir(Context context, String assetPath, String destPath) {
        AssetManager assetManager = context.getAssets();
        String assets[];
        try {
            assets = assetManager.list(assetPath);
            if (assets.length != 0) {
                createDirectory(destPath);
                for(String asset : assets) {
                    copyAssetFile(context, assetPath, asset, destPath, urlToFileName(asset));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 通过url获取file name
     * @param url
     * @return
     */
    private static String urlToFileName(String url) {
        return String.valueOf(url.hashCode());
    }

    /**
     * 拷贝Asset文件到目标位置
     * @param context
     * @param assetPath
     * @param assetName
     * @param destPath
     * @param destName
     */
    public static void copyAssetFile(Context context, String assetPath, String assetName, String destPath, String destName) {
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        String assetFilePath = assetPath + "/" + assetName;
        createDirectory(destPath);
        String newFilePath = destPath + "/" + destName;
        try {
            in = assetManager.open(assetFilePath);
            out = new FileOutputStream(newFilePath);

            byte[] buffer = new byte[1024];
            int count;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            deletePath(newFilePath);
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
                if(out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取assets文件夹下的文件
     * @param fileName
     * @param context
     * @return
     */
    public static String getFromAssets(String fileName, Context context){
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String result = "";
            while((line = bufReader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串写入到指定文件中
     * @param saveFile  写入文件
     * @param contentTxt 写入内容
     * @param flag 是否在文件最后追加
     */
    public static void writeToFile(File saveFile, String contentTxt, boolean flag) {

        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile,flag), "UTF-8"));
            bufferedWriter.write(contentTxt);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 从指定文件中读取内容
     * @param fromFile 目标文件
     * @return
     */
    public static String readFromFile(File fromFile) {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fromFile), "UTF-8"));
            String line;
            while((line= bufferedReader.readLine()) != null && line.length() > 0) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    /**
     * 打开指定文件
     * @param filePath
     * @return
     *
     * Intent.FLAG_ACTIVITY_CLEAR_TOP ：
     *      如果在当前Task中，有要启动的Activity，那么把该Acitivity之前的所有Activity都关掉，
     *      并把此Activity置前以避免创建Activity的实例
     * Intent.FLAG_ACTIVITY_NEW_TASK ：
     *      系统会检查当前所有已创建的Task中是否有该要启动的Activity的Task，若有，则在该Task上
     *      创建Activity，若没有则新建具有该Activity属性的Task，并在该新建的Task上创建Activity。
     */
    public static Intent openFile(String filePath){

        File file = new File(filePath);
        if(!file.exists()) {
            return null;
        }
		/* 取得扩展名 */
        String extName = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
        if (ValidatesUtil.isEmpty(extName)) {
            extName = ".";
        }
		/* 依扩展名的类型决定MimeType */
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(filePath));
        intent.setDataAndType(uri, mOpenFileTypes.get(extName));
        return intent;
    }

    public static byte[] getBytes(String filePath) {
        if(TextUtils.isEmpty(filePath)){
            return null;
        }
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    //得到文件夹的大小
//    public static long getFileSize(File file){
//        long  size =  0 ;
//        File flist[] = file.listFiles();
//        for  ( int  i =  0 ; i < flist.length; i++) {
//            if  (flist[i].isDirectory()) {
//                size = size + getFileSize(flist[i]);
//            } else {
//                size = size + flist[i].length();
//            }
//        }
//        return  size;
//    }

    //得到某个文件的大小
    public static String getFileLength(File file){
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        long size = file.length();

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    //判断文件是否为图片
    public static boolean isImg(String name){
        boolean flag = false;
        for(int i=0;i<mImgSuffix.size();i++){
            if(mImgSuffix.get(i).equalsIgnoreCase(name)){
                flag = true;
                return flag;
            }
        }
        return flag;
    }
    //判断文件是否为视频
    public static boolean isVideo(String name){
        boolean flag = false;
        for(int i=0;i<mVideoSuffix.size();i++){
            if(mVideoSuffix.get(i).equalsIgnoreCase(name)){
                flag = true;
                return flag;
            }
        }
        return flag;
    }
    //判断文件是否为音频
    public static boolean isAudio(String name){
        boolean flag = false;
        for(int i=0;i<mAudioSuffix.size();i++){
            if(mAudioSuffix.get(i).equalsIgnoreCase(name)){
                flag = true;
                return flag;
            }
        }
        return flag;
    }

    public static  void saveBitmap(Bitmap bitmap, String bitName) {
        File file = new File(bitName);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 90, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath
     *            文件路径
     * @param sizeType
     *            获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogTools.e("获取文件大小", "获取失败!");
        }
        return FormetFileSize(blockSize, sizeType);
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 获取指定文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        FileInputStream fis = null;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                LogTools.e("获取文件大小", "文件不存在!");
            }
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df
                        .format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    /**
     * 读取流文件
     *  @param  instream
     *  @return  byte[]
     *  @throws Exception
     */
    public static String readInputStream(InputStream instream){
        BufferedReader in = null;
        StringBuffer buffer = null;
        try {
            in = new BufferedReader(new InputStreamReader(instream, "utf-8"));
            buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
        }catch (Exception e){
            LogTools.e(e.getMessage());
        }finally {
            try {
                if(in != null){
                    in.close();
                }
            }catch (Exception ex){
                LogTools.e(ex.getMessage());
            }
        }
        return  buffer.toString();
    }

    /**
     * 1\可先创建文件的路径
     * @param filePath
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 2\然后在创建文件名就不会在报该错误
     * @param filePath
     * @param fileName
     * @return
     */
    public static File getFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
