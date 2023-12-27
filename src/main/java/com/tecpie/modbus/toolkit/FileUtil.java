package com.tecpie.modbus.toolkit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class FileUtil {

    private FileUtil() {
    }

    /***************************************************************
     * 文件比较
     */
    /**
     * @param file1       文件1
     * @param file2       文件2
     * @param charsetName 文件编码，可以为空
     * @return 是否相同
     * @throws IOException
     * @方法名：contentEquals
     * @方法描述【方法功能描述】 判断两个文件是否相同
     * @修改描述【修改描述】
     */
    public static boolean contentEquals(File file1, File file2, String charsetName) throws IOException {
        boolean check = false;
        if (StringUtils.isBlank(charsetName)) {
            check = FileUtils.contentEquals(file1, file2);
        } else {
            check = FileUtils.contentEqualsIgnoreEOL(file1, file2, charsetName);
        }
        return check;
    }

    /**
     * @param file 文件
     * @param date 比较日期
     * @return 文件创建日期早则为false，晚为true
     * @throws IOException
     * @方法名：isFileNewer
     * @方法描述【方法功能描述】判断文件的创建日期与日期比较，文件创建日期早则为false，晚为true
     * @修改描述【修改描述】
     */
    public static boolean isFileNewer(File file, Date date) {
        return FileUtils.isFileNewer(file, date);
    }

    /***************************************************************
     * 文件查询
     */
    /**
     * @param directory  目录
     * @param recursive  是否递归
     * @param fileFilter 文件过滤器
     * @param dirFilter  目录过滤器
     * @return 文件列表
     * @方法名：listFiles
     * @方法描述【方法功能描述】 查询目录下文件
     * @修改描述【修改描述】
     */
    public static List<File> listFiles(File directory, boolean recursive, IOFileFilter fileFilter,
                                       IOFileFilter dirFilter) {
        if (recursive) {
            return (List<File>) FileUtils.listFiles(directory, fileFilter, dirFilter);
        } else {
            return (List<File>) FileUtils.listFilesAndDirs(directory, fileFilter, dirFilter);
        }
    }

    /**
     * @param file     文件
     * @param encoding 编码
     * @return 文件字符串
     * @throws IOException
     * @方法名：readFileToString
     * @方法描述【方法功能描述】读取文件为字符串
     * @修改描述【修改描述】
     */
    public static String readFileToString(File file, String encoding) throws IOException {
        return FileUtils.readFileToString(file, encoding);
    }

    /**
     * @param file     文件
     * @param encoding 编码
     * @return 多行字符串
     * @throws IOException
     * @方法名：readLines
     * @方法描述【方法功能描述】读取文件为多行字符串
     * @修改描述【修改描述】
     */
    public static List<String> readLines(File file, String encoding) throws IOException {
        return FileUtils.readLines(file, encoding);
    }

    /**
     * @param file 文件/目录
     * @return 大小
     * @throws IOException
     * @方法名：sizeOf
     * @方法描述【方法功能描述】查询文件大小
     * @修改描述【修改描述】
     */
    public static Long sizeOf(File file) {
        if (file.isDirectory()) {
            return FileUtils.sizeOf(file);
        } else {
            return FileUtils.sizeOfDirectory(file);
        }

    }

    /***************************************************************
     * 文件新增
     */
    /**
     * @param file     文件
     * @param data     字符串
     * @param encoding 编码
     * @param append   是否追加,true则追加，false则覆盖
     * @throws IOException
     * @方法名：writeStringToFile
     * @方法描述【方法功能描述】 将一个字符串写入一个文件创建文件，如果不存在。
     * @修改描述【修改描述】
     */
    public static void writeStringToFile(File file, String data, String encoding, boolean append) throws IOException {
        FileUtils.writeStringToFile(file, data, encoding, append);
    }

    /**
     * @param file       文件
     * @param encoding   编码
     * @param lines      多行文本
     * @param lineEnding 要使用的行分隔符null是系统默认值
     * @param append     是否追加,true则追加，false则覆盖
     * @throws IOException
     * @方法名：writeLines
     * @方法描述【方法功能描述】将多行文本写入文件
     * @修改描述【修改描述】
     */
    public static void writeLines(File file, String encoding, List<String> lines, String lineEnding, boolean append)
            throws IOException {
        if (StringUtils.isBlank(lineEnding)) {
            FileUtils.writeLines(file, encoding, lines, append);
        } else {
            FileUtils.writeLines(file, encoding, lines, lineEnding, append);
        }

    }

    /**
     * @param file    目录/文件
     * @param isFlile 是否是文件
     * @return 文件目录是否创建成功
     * @throws IOException
     * @方法名：mkdir
     * @方法描述【方法功能描述】创建目录或创建文件的父目录
     * @修改描述【修改描述】
     */
    public static boolean mkdir(File file, boolean isFlile) throws IOException {
        if (isFlile) {
            FileUtils.forceMkdirParent(file);
        } else {
            FileUtils.forceMkdir(file);
        }
        return true;
    }

    /***************************************************************
     * 文件修改
     */
    /**
     * @param srcDir           起始目录/起始文件
     * @param destDir          目的目录/目的文件
     * @param preserveFileDate 文件是否保存原日期，false保存最新日期，true保存元日期
     * @param filter           文件过滤
     * @throws IOException
     * @方法名：copy
     * @方法描述【方法功能描述】将目录/文件复制到目录/文件；可过滤；可设置保存最新日期
     * @修改描述【修改描述】
     */
    public static void copy(File srcDir, File destDir, boolean preserveFileDate, FileFilter filter) throws IOException {
        // 从起始目录到目的目录
        if (srcDir.isDirectory() && destDir.isDirectory()) {
            FileUtils.copyDirectory(srcDir, destDir, filter, preserveFileDate);
        }
        // 从起始文件到目的目录
        else if (!srcDir.isDirectory() && destDir.isDirectory()) {
            FileUtils.copyFileToDirectory(srcDir, destDir, preserveFileDate);
        }
        // 从起始文件到目的文件
        else if (!srcDir.isDirectory() && !destDir.isDirectory()) {
            FileUtils.copyFile(srcDir, destDir, preserveFileDate);
        }

    }

    /**
     * @param src           目录/文件
     * @param dest          目录/文件
     * @param createDestDir 是否制定创建时间
     * @throws IOException
     * @方法名：move
     * @方法描述【方法功能描述】将目录/文件移动到目录/文件；可过滤；可设置保存最新日期
     * @修改描述【修改描述】
     */
    public static void move(File src, File dest, boolean createDestDir) throws IOException {
        // 从起始目录到目的目录
        if (src.isDirectory() && dest.isDirectory()) {
            FileUtils.moveDirectoryToDirectory(src, dest, createDestDir);
        }
        // 从起始文件到目的目录
        else if (!src.isDirectory() && dest.isDirectory()) {
            FileUtils.moveFileToDirectory(src, dest, createDestDir);
        }
        // 从起始文件到目的文件
        else if (!src.isDirectory() && !dest.isDirectory()) {
            FileUtils.moveFile(src, dest);
        }

    }

    /***************************************************************
     * 文件删除
     */
    /**
     * @param path 文件或目录路径
     * @throws IOException
     * @方法名：deleteFile
     * @方法描述【方法功能描述】删除目录或文件
     * @修改描述【修改描述】
     */
    public static void deleteFile(String path) throws IOException {
        if (path == null || "".equals(path)) {
            return;
        }
        File ftemp = null;
        try {
            ftemp = new File(path);
            FileUtils.forceDelete(ftemp);
        } catch (IOException e) {
            FileUtils.forceDeleteOnExit(ftemp);
        }
    }

    /**
     * @param file 文件或目录对象
     * @throws IOException
     * @方法名：deleteFile
     * @方法描述【方法功能描述】删除目录或文件
     * @修改描述【修改描述】
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2018年8月28日 上午11:05:03
     * @修改人：cc
     * @修改时间：2018年8月28日 上午11:05:03
     */
    public static void deleteFile(File file) throws IOException {
        if (file == null || !file.exists()) {
            return;
        }
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            FileUtils.forceDeleteOnExit(file);
        }
    }

    /**
     * @param paths 文件或目录路径
     * @throws IOException
     * @方法名：deleteFilesViaPath
     * @方法描述【方法功能描述】 批量删除文件或目录
     * @修改描述【修改描述】
     */
    public static void deleteFilesViaPath(List<String> paths) throws IOException {
        if (CollectionUtils.isEmpty(paths))
            return;
        for (String path : paths) {
            FileUtil.deleteFile(path);
        }
    }

    /**
     * @param files 文件或目录对象集合
     * @throws IOException
     * @方法名：deleteFiles
     * @方法描述【方法功能描述】批量删除文件或目录
     * @修改描述【修改描述】
     */
    public static void deleteFiles(List<File> files) throws IOException {
        if (CollectionUtils.isEmpty(files))
            return;
        for (File file : files) {
            FileUtil.deleteFile(file);
        }
    }

}
