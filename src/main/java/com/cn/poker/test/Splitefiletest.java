package com.cn.poker.test;

import java.nio.ByteBuffer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class Splitefiletest {

    public static void splitFile(String filePath, int fileCount) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        FileChannel inputChannel = fis.getChannel();
        final long fileSize = inputChannel.size();
        //平均值
        long average = fileSize / fileCount;
        //缓存块大小，自行调整
        long bufferSize = 200;
        // 申请一个缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.valueOf(bufferSize + ""));
        //子文件开始位置
        long startPosition = 0;
        //子文件结束位置
        long endPosition = average < bufferSize ? 0 : average - bufferSize;
        for (int i = 0; i < fileCount; i++) {
            if (i + 1 != fileCount) {
                // 读取数据
                int read = inputChannel.read(byteBuffer, endPosition);
                readW:
                while (read != -1) {
                    byteBuffer.flip();//切换读模式
                    byte[] array = byteBuffer.array();
                    for (int j = 0; j < array.length; j++) {
                        byte b = array[j];
                        //判断\n\r
                        if (b == 10 || b == 13) {
                            endPosition += j;
                            break readW;
                        }
                    }
                    endPosition += bufferSize;
                    byteBuffer.clear(); //重置缓存块指针
                    read = inputChannel.read(byteBuffer, endPosition);
                }
            }else{
                //最后一个文件直接指向文件末尾
                endPosition = fileSize;
            }

            FileOutputStream fos = new FileOutputStream(filePath + (i + 1));
            FileChannel outputChannel = fos.getChannel();
            //通道传输文件数据
            inputChannel.transferTo(startPosition, endPosition - startPosition, outputChannel);
            outputChannel.close();
            fos.close();
            startPosition = endPosition + 1;
            endPosition += average;
        }
        inputChannel.close();
        fis.close();

    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        long startTime = System.currentTimeMillis();
        splitFile("C:/Users/Administrator/Desktop/日志-备份/error.log.2020-07-18-下午",14);
        long endTime = System.currentTimeMillis();
        System.out.println("耗费时间： " + (endTime - startTime) + " ms");
        scanner.nextLine();
    }



}

