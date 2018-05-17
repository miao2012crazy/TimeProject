package com.memory.timeapp.commonUtil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.memory.timeapp.applaction.CustomApplaction;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UIUtils {

    private Toast toast;

    /**
     * @return 返回上下文环境
     */
    public static Context getContext() {
        return CustomApplaction.getContext();
    }

    public static Handler getHandler() {
        return CustomApplaction.getHandler();
    }

    public static Thread getMainThread() {
        return CustomApplaction.getMainThread();
    }

    public static int getMainThreadId() {
        return CustomApplaction.getMainThreadId();
    }

    /**
     * @return 获取res文件夹
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * @param colorId 根据id获取色值
     * @return
     */
    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    private static final String marshmallowMacAddress = "02:00:00:00:00:00";
    private static final String fileAddressMac = "/sys/class/net/wlan0/address";

    /**
     * @param drawableId 根据id
     * @return 获取图片
     */
    public static Drawable getDrawable(int drawableId) {
        return getResources().getDrawable(drawableId);
    }

    /**
     * @param layoutId
     * @return
     */
    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    //获取string中的字符串
    public static String getString(int stringId) {
        return getResources().getString(stringId);
    }

    //适配中,dip ---->px
    public static int dip2px(int dip) {
        //ic_hair_boy.获取手机的像素密度,px和dp的比例关系		ic_hair_boy:0.75  ic_hair_boy:ic_hair_boy ic_hair_boy:ic_hair_boy.5
        float density = getResources().getDisplayMetrics().density;
        Log.e("像素密度", density + "");
        return (int) (density * dip + 0.5f);
    }

    //px--->dip方法
    public static int px2dip(int px) {
        //ic_hair_boy.获取手机的像素密度,px和dp的比例关系		ic_hair_boy.5		150px	100dp+0.5f = 100
        float density = getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    //保证代码运行在主线程中(开启子线程,到主线程中处理UI)
    public static void runInMainThread(Runnable runnable) {
        //ic_hair_boy.如果此runInMainThread中的runnable就是在主线程中
        if (getMainThreadId() == android.os.Process.myTid()) {
            //在WLBApplication中获取的id和你当前应用程序的id一致,说明是主线程,是主线程则直接运行runnable中过的run方法即可
            runnable.run();
        } else {
            //不是主线程,指定runnable中的run方法在主线程中运行
            getHandler().post(runnable);

            //ic_hair_boy.创建Message对象,将runnable封装在message对象的obj中(handler发送过程),子线程
            //2.在handlerMessage方法中获取message中的obj对象,然后获取runnable,再去调用run方法(handler接受处理消息过程),主线程
        }
    }

    /**
     * @param mTabTextColorResId 传递一个包含了选择器的文件,返回选择器的对象
     * @return
     */
    public static ColorStateList getColorStateList(int mTabTextColorResId) {
        return getResources().getColorStateList(mTabTextColorResId);
    }

    public static String[] getStringArray(int arrayId) {
        return getResources().getStringArray(arrayId);
    }

    /**
     * 验证输入的身份证号是否合法
     */
    public static boolean isLegalId(String id) {
        if (id.toUpperCase().matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 手机号号段校验，
     * 第1位：1；
     * 第2位：{3、4、5、6、7、8}任意数字；
     * 第3—11位：0—9任意数字
     *
     * @param value
     * @return
     */
    public static boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }

    /**
     * @param msg 吐司 (短) 显示toast 提示信息
     */
    public static void showToast(String msg) {
        Toast.makeText(UIUtils.getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */

    public static int lastButtonId = -1;
    public static long lastClickTime = 0;

    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }

    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName()
                    , PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] signatures = md.digest(cert);
            StringBuffer sha1 = new StringBuffer();
            int i = 0;
            for (byte key : signatures) {
                String appendString = Integer.toHexString(0xFF & key).toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    sha1.append("0");
                sha1.append(appendString);
                if (signatures.length - 1 == i)
                    break;
                sha1.append(":");
                i++;
            }
            return sha1.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取屏幕宽
     */
    public static int getWindowWidth() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 获取屏幕宽
     */
    public static int getWindowHeight() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }


    /**
     * 获取mac
     *
     * @param context
     * @return
     */
    public static String getAdresseMAC(Context context) {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);


            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial.toUpperCase().replace(':', '-').toString();
    }

    private static String getAdressMacByInterface() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (nif.getName().equalsIgnoreCase("wlan0")) {
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }

        } catch (Exception e) {
            Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
        }
        return null;
    }

    /**
     * 获取内置SD卡路径
     *
     * @return
     */
    public static String getInnerSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取版本code
     *
     * @return
     * @throws Exception
     */
    public static double getVersionCode() {
        // 获取packagemanager的实例
        PackageManager packageManager = getContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("版本信息", "获取版本信息异常");
            e.printStackTrace();
        }
        double version = packInfo.versionCode;
        return version;
    }

    //安装apk
    public static void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        getContext().startActivity(intent);
    }

    /**
     * 从APK中读取签名
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static List<String> getSignaturesFromApk(File file) throws IOException {
        List<String> signatures = new ArrayList<String>();
        JarFile jarFile = new JarFile(file);
        try {
            JarEntry je = jarFile.getJarEntry("AndroidManifest.xml");
            byte[] readBuffer = new byte[8192];
            Certificate[] certs = loadCertificates(jarFile, je, readBuffer);
            if (certs != null) {
                for (Certificate c : certs) {
                    String sig = toCharsString(c.getEncoded());
                    signatures.add(sig);
                }
            }
        } catch (Exception ex) {
        }
        return signatures;
    }

    /**
     * 加载签名
     *
     * @param jarFile
     * @param je
     * @param readBuffer
     * @return
     */
    private static Certificate[] loadCertificates(JarFile jarFile, JarEntry je, byte[] readBuffer) {
        try {
            InputStream is = jarFile.getInputStream(je);
            while (is.read(readBuffer, 0, readBuffer.length) != -1) {
            }
            is.close();
            return je != null ? je.getCertificates() : null;
        } catch (IOException e) {
        }
        return null;
    }


    /**
     * 将签名转成转成可见字符串
     *
     * @param sigBytes
     * @return
     */
    private static String toCharsString(byte[] sigBytes) {
        byte[] sig = sigBytes;
        final int N = sig.length;
        final int N2 = N * 2;
        char[] text = new char[N2];
        for (int j = 0; j < N; j++) {
            byte v = sig[j];
            int d = (v >> 4) & 0xf;
            text[j * 2] = (char) (d >= 10 ? ('a' + d - 10) : ('0' + d));
            d = v & 0xf;
            text[j * 2 + 1] = (char) (d >= 10 ? ('a' + d - 10) : ('0' + d));
        }
        return new String(text);
    }

    //判断文件是否存在
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 转换为圆形图片头像专用
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public static Bitmap getOvalBitmap(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 将Bitmap转换成文件
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Bitmap bm, String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return path + fileName;
    }

    /**
     * getTime方法返回的就是10位的时间戳
     *
     * @return
     */
    public static long getTime() {
        long time = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        return time;
    }

    public static String getTimeForSurplus(String stamp) {
        long l = Long.parseLong(stamp);
        long time = getTime();
        long l2 = l - time;
        //小时
        long l1 = l2 / (60 * 60);
        long l3 = l2 % (60 * 60);
        //分钟
        long l4 = l3 / 60;
        //秒
        long l5 = l3 % 60;
        String hh = String.valueOf(l1);
        if (hh.length()==1){
            hh="0"+hh;
        }
        String mm = String.valueOf(l4);
        if (mm.length()==1){
            mm="0"+mm;
        }
        String ss = String.valueOf(l5);
        if (ss.length()==1){
            ss="0"+ss;
        }
        return hh+":"+mm+":"+ss;
    }

    public static long getDistanceTime(long time1, long time2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        String flag;
        if (time1 < time2) {
            diff = time2 - time1;
            flag = "前";
        } else {
            diff = time1 - time2;
            flag = "后";
        }
        day = diff / (24 * 60 * 60 * 1000);
        return day;
    }
}
