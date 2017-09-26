package cn.hopever.platform.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.*;


/**
 * Created by Donghui Huo on 2016/10/31.
 */
public class FileUtil {
    public static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String readFile(File file) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = reader.readLine()) != null)
                builder.append(line);
        } catch (IOException e) {
            logger.warn("file open problem:" + e);
        } finally {
            closeQuietly(reader);
        }
        return builder.toString();
    }

    public static void writeFile(InputStream inputStream, String filePath) throws IOException {
        OutputStream out = null;
        try {
            int lastIndex = filePath.lastIndexOf("/");
            if (lastIndex == -1) {
                lastIndex = filePath.lastIndexOf("\\");
            }
            String directory = filePath.substring(0, lastIndex);
            createDirectory(directory, null);
            out = new FileOutputStream(new File(filePath));
            int length = 0;
            byte[] b = new byte[2048];
            while ((length = inputStream.read(b)) != -1) {
                out.write(b, 0, length);
            }
        } catch (IOException e) {
            logger.warn("file open problem:" + e);
        } finally {
            out.close();
            inputStream.close();
        }
    }

    private static void closeQuietly(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException ignored) {
                logger.warn("file close problem:" + ignored);
            }
        }
    }

    public static void zip(File file, ZipOutputStream zos, String rootPath) throws Exception {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileTmp : files) {
                zip(fileTmp, zos, ((rootPath != null && rootPath.length() > 0) ? rootPath + "\\" : "") + file.getName());
            }
        } else {
            String entryName = null;
            if (!"".equals(rootPath)) {
                entryName = rootPath + "\\" + file.getName();
            } else {
                entryName = file.getName();
            }
            ZipEntry entry = new ZipEntry(entryName);
            entry.setSize(file.length());
            zos.putNextEntry(entry);
            InputStream is = new FileInputStream(file);
            int length = 0;
            byte[] b = new byte[2048];
            while ((length = is.read(b)) != -1) {
                zos.write(b, 0, length);
            }
            is.close();
        }

    }

    public static List<String> unZip(File file, String rootPath) throws Exception {
        List<String> list = new ArrayList<>();
        ZipFile zipFile = null;
        try {
            Charset CP866 = Charset.forName("CP866");
            zipFile = new ZipFile(file, CP866);
            createDirectory(rootPath, null);//创建输出目录
            Enumeration<?> enums = zipFile.entries();
            while (enums.hasMoreElements()) {

                ZipEntry entry = (ZipEntry) enums.nextElement();
                if (entry.isDirectory()) {//是目录
                    createDirectory(rootPath, entry.getName());//创建空目录
                } else {//是文件
                    File tmpFile = new File(rootPath + "/" + entry.getName());
                    createDirectory(tmpFile.getParent() + "/", null);//创建输出目录
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = zipFile.getInputStream(entry);
                        out = new FileOutputStream(tmpFile);
                        int length = 0;
                        byte[] b = new byte[2048];
                        while ((length = in.read(b)) != -1) {
                            out.write(b, 0, length);
                        }

                    } catch (IOException ex) {
                        throw ex;
                    } finally {
                        if (in != null)
                            in.close();
                        if (out != null)
                            out.close();
                        list.add(entry.getName());
                    }
                }
            }

        } catch (IOException e) {
            throw new IOException("解压缩文件出现异常", e);
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException ex) {
                throw new IOException("关闭zipFile出现异常", ex);
            }
        }
        return list;
    }

    public static LinkedHashMap<String, Long> unZip(InputStream inputStream, String rootPath) throws Exception {
        LinkedHashMap<String, Long> map = new LinkedHashMap<>();
        ZipInputStream zipInputStream = null;
        try {
            Charset CP866 = Charset.forName("CP866");
            zipInputStream = new ZipInputStream(inputStream, CP866);
            createDirectory(rootPath, null);//创建输出目录
            while (true) {
                ZipEntry entry = (ZipEntry) zipInputStream.getNextEntry();
                if (entry == null) {
                    break;
                }
                if (entry.isDirectory()) {//是目录
                    createDirectory(rootPath, entry.getName());//创建空目录
                } else {//是文件
                    File tmpFile = new File(rootPath + "/" + entry.getName());
                    createDirectory(tmpFile.getParent() + "/", null);//创建输出目录
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(tmpFile);
                        long total = 0;
                        int length = 0;
                        byte[] b = new byte[2048];
                        while ((length = zipInputStream.read(b)) != -1) {
                            out.write(b, 0, length);
                            total += length;
                        }
                        map.put(entry.getName(), total);
                    } catch (IOException ex) {
                        throw ex;
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new IOException("解压缩文件出现异常", e);
        } finally {
            try {
                if (zipInputStream != null) {
                    zipInputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                throw new IOException("关闭zipFile出现异常", ex);
            }
        }
        return map;
    }

    public static void gzip(File file, GZIPOutputStream gos, String rootPath) throws Exception {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileTmp : files)
                gzip(fileTmp, gos, rootPath + "\\" + file.getName());
        } else {
            InputStream is = new FileInputStream(file);
            int length = 0;
            byte b[] = new byte[2048];
            while ((length = is.read(b)) != -1)
                gos.write(b, 0, length);
            is.close();
        }
    }

    public static List<String> unGzip(File file, String rootPath) throws Exception {
        List<String> list = new ArrayList<>();
        try {
            //建立gzip压缩文件输入流
            FileInputStream fin = new FileInputStream(file);
            //建立gzip解压工作流
            GZIPInputStream gzin = new GZIPInputStream(fin);
            //建立解压文件输出流
            createDirectory(rootPath, null);//创建空目录
            FileOutputStream fout = new FileOutputStream(rootPath);
            int num;
            byte[] buf = new byte[1024];
            while ((num = gzin.read(buf, 0, buf.length)) != -1) {
                fout.write(buf, 0, num);
            }
            gzin.close();
            fout.close();
            fin.close();
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        return list;
    }

    public static void createDirectory(String outputDir, String subDir) {
        File file = new File(outputDir);
        if (!(subDir == null || subDir.trim().equals(""))) {//子目录不为空
            file = new File(outputDir + "/" + subDir);
        }
        if (!file.exists()) {
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            file.mkdirs();
        }
    }

    public static void main(String[] args) throws Exception {
//        File file = new File("D:\\var\\log\\platform");
//        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("D:\\var\\log\\platform.zip"));
//        FileUtil.zip(file, zipOutputStream, "");
//        zipOutputStream.close();
//        File file = new File("D:\\var\\log\\platform.zip");
//        FileUtil.unZip(new FileInputStream(file), "D:\\var\\log\\platform1");
//        FileUtil.unZip(file, "D:\\var\\log\\platform1");
//        FileUtil.unZip(new FileInputStream(file), "D:\\var\\log\\platform1");
//        File file = new File("D:\\var\\log\\platform");
//        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new FileOutputStream("D:\\var\\log\\platform.gz"));
//        FileUtil.gzip(file, gzipOutputStream, "");
        File file = new File("D:\\var\\log\\platform.zip");
        FileUtil.writeFile(new FileInputStream(file), "D:\\var\\log1\\platform1.zip");
    }

    public static String getFileGeneralType(String filePath) {
        filePath = filePath.toLowerCase();
        if (filePath.endsWith(".css")) {
            return "stylesheet";
        } else if (filePath.endsWith(".js")) {
            return "script";
        } else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg") || filePath.endsWith(".png") || filePath.endsWith(".gif") || filePath.endsWith(".svg")) {
            return "image";
        } else if (filePath.endsWith(".flv") || filePath.endsWith(".avi") || filePath.endsWith(".rmvb") || filePath.endsWith(".rm") || filePath.endsWith(".mpeg") || filePath.endsWith(".asf") || filePath.endsWith(".wmv")) {
            return "video";
        } else if (filePath.endsWith(".wav") || filePath.endsWith(".mp3") || filePath.endsWith(".wma")) {
            return "audio";
        } else if (filePath.endsWith(".ttf") || filePath.endsWith(".eot") || filePath.endsWith(".woff") || filePath.endsWith(".woff2")) {
            return "font";
        } else if (filePath.endsWith(".css")) {
            return "stylesheet";
        } else if (filePath.endsWith(".js")) {
            return "script";
        } else {
            return "document";
        }
    }

    public static String getExtensionName(String filePath) {
        int dot = filePath.lastIndexOf('.');
        if ((dot > -1) && (dot < (filePath.length() - 1))) {
            return filePath.substring(dot + 1).toLowerCase();
        }
        return null;
    }

    public static String getFileNameNoExtend(String filePath) {
        int dot = filePath.lastIndexOf('.');
        int separate = filePath.lastIndexOf("/");
        if (dot < 0) {
            dot = filePath.length() - 1;
        }
        return filePath.substring(separate + 1, dot);
    }

    public static String getFileName(String filePath) {
        int separate = filePath.lastIndexOf("/");
        if (separate < 0) {
            separate = 0;
        }
        return filePath.substring(separate + 1);
    }

}
