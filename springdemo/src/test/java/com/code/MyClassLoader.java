package com.code;

import cn.hutool.core.io.IoUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author ccy
 * @description
 * @time 2020-08-19 14:12
 */
public class MyClassLoader extends ClassLoader {
    private static String classname = "java.lang.math";

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                if(name.startsWith("xxx")){
                    //满足自定义的条件 直接去加载类 不走双亲委派机制 打破双亲委派机制
                    c = this.findClass(name);
                }else {
                    //不满足自定义的条件,继续走双亲委派机制  例如 java.lang.* 下面的类 沙箱安全机制无法自定义
                    c = super.loadClass(name);
                }
            }

            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        System.out.println(classLoader);

        String uri = classLoader.getResource("").getPath() + "/java/lang/" + "Math.class";
//        String uri = classLoader.getResource("").getPath() + "/com/code/" + "Test.class";
        File file = new File(uri);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = IoUtil.readBytes(fileInputStream);

//            ByteOutputStream out = new ByteOutputStream();
//            out.write(fileInputStream);

            return defineClass(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }


//    private void myDefineClass(byte[] b, int off, int len) {
//
//        String source = defineClassSourceLocation(defaultDomain);
//        Class<?> c = defineClass1(classname, b, off, len, defaultDomain, source);
//        postDefineClass(c, defaultDomain);
//        return c;
//    }
//
//
//    // The "default" domain. Set as the default ProtectionDomain on newly
//    // created classes.
//    private final ProtectionDomain defaultDomain =
//            new ProtectionDomain(new CodeSource(null, (Certificate[]) null),
//                    null, this, null);
//
//    private String defineClassSourceLocation(ProtectionDomain pd)
//    {
//        CodeSource cs = pd.getCodeSource();
//        String source = null;
//        if (cs != null && cs.getLocation() != null) {
//            source = cs.getLocation().toString();
//        }
//        return source;
//    }
//
//    private void postDefineClass(Class<?> c, ProtectionDomain pd)
//    {
//        if (pd.getCodeSource() != null) {
//            Certificate certs[] = pd.getCodeSource().getCertificates();
//            if (certs != null)
//                setSigners(c, certs);
//        }
//    }

}
