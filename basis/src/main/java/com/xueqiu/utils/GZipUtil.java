package com.xueqiu.utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * @Author:ggq
 * @Date:2018/12/6
 * @Description:
 */
public class GZipUtil {
    public static File pack(File[] sources, File target){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(target);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        TarArchiveOutputStream os = new TarArchiveOutputStream(out);
        for (File file : sources) {
            try {
                os.putArchiveEntry(new TarArchiveEntry(file));
                IOUtils.copy(new FileInputStream(file), os);
                os.closeArchiveEntry();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(os != null) {
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return target;
    }

    /**
     *
     * @Title: compress
     * @Description: 将文件用gzip压缩
     * @param  source 需要压缩的文件
     * @return File    返回压缩后的文件
     * @throws
     */
    public static File compress(File source) {
        System.out.println(source.getName());
        File target = new File(source.getParent() + "/" + source.getName().substring(0,source.getName().lastIndexOf(".")) + ".tgz");
        FileInputStream in = null;
        GZIPOutputStream out = null;
        try {
            in = new FileInputStream(source);
            out = new GZIPOutputStream(new FileOutputStream(target));
            byte[] array = new byte[1024];
            int number = -1;
            while((number = in.read(array, 0, array.length)) != -1) {
                out.write(array, 0, number);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return target;
    }

    public static void main(String[] args) {
        File[] sources = new File[] {
                new File("E:\\fileload\\test\\58_consume_20181123.cvs")
                ,new File("E:\\fileload\\test\\readme.txt")};
        File target = new File("E:\\fileload\\test\\58_consume_20181123.tar");
        compress(pack(sources, target));
    }
}
