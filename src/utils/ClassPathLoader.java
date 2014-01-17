package utils;


import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

public class ClassPathLoader {
    private static ClassPathLoader instance = new ClassPathLoader();
    private Set<URL> urls = new HashSet<URL>();

    public static ClassPathLoader getInstance() {
        return instance;
    }

    public void addJar(File jarFile) throws MalformedURLException {
        urls.add(jarFile.toURI().toURL());
    }

    public void addJarFromDirectory(File dir) throws MalformedURLException {
        if (!dir.exists()) {
            System.out.println("Module directory doesn't exist : " + dir.getName());
            return;
        }
        final File[] dirs = dir.listFiles();
        for (int i = 0; i < dirs.length; i++) {
            File f = dirs[i];
            if (f.getName().endsWith(".jar")) {
                addJar(f);
            }
        }
    }

    public void load() {
        try {
            final Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
            addURL.setAccessible(true);// you're telling the JVM to override the default visibility
            final ClassLoader cl = ClassLoader.getSystemClassLoader();

            for (URL url : urls) {
                System.out.println("Loading module " + url);
                addURL.invoke(cl, new Object[] { url });
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
