package org.lepigslayer.cSDay;

import org.lepigslayer.cSDay.Wand.CodeWrapper;
import org.lepigslayer.cSDay.Wand.Wand;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class FileCompiler {
    private CSDay plugin;
    private CodeWrapper wrapper;

    public FileCompiler(CSDay plugin) {
        this.plugin = plugin;
    }

    public void recompile(String fileName) {
        File folder = plugin.getDataFolder();
        boolean result = compile(fileName);
        if (result) {

            ClassLoader loader = new URLClassLoader(new java.net.URL[]{safeUrlConvert(folder)},getClass().getClassLoader());
            Class<?> loadedClass = safeLoadClass(loader,fileName);

            Object obj = safeNewClass(loadedClass);
            wrapper = new CodeWrapper(obj);
            plugin.getLogger().info("Successfully recompiled");
        }else{
            plugin.getLogger().warning("Failed to recompile");
        }

        plugin.getLogger().info("Done");
    }

    private boolean compile(String fileName){
        File folder = plugin.getDataFolder();

        String fullpath = Wand.class.getName();

        if (!folder.exists())
            folder.mkdir();

        File javaFile = new File(plugin.getDataFolder(), fileName + ".java");

        if (!javaFile.exists()) {
            safeCreateNewFile(javaFile);
            return false;
        }

        String dependencies = "D:/SpigotPlugins/TestServer/plugins/csDay-1.0.jar;" +
                "C:/Users/Owner/.m2/repository/org/bukkit/bukkit/1.21.4-R0.1-SNAPSHOT/bukkit-1.21.4-R0.1-SNAPSHOT.jar;" +
                "C:/Users/Owner/.m2/repository/org/spigotmc/spigot/1.21.4-R0.1-SNAPSHOT/spigot-1.21.4-R0.1-SNAPSHOT.jar";

        String outputDirectory = "D:/SpigotPlugins/TestServer/plugins/CSDay";

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        try{
            Iterable<String> options = Arrays.asList(
                    "-d",outputDirectory,
                    "-classpath", dependencies
            );

            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(javaFile);
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, compilationUnits);

            boolean success = task.call();
            return success;
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally {
            try{
                fileManager.close();
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    public CodeWrapper getWrapper(){
        return wrapper;
    }

    private boolean safeCreateNewFile(File file) {
        try {
            return file.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    private URL safeUrlConvert(File folder) {
        try {
            return folder.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T safeNewClass(Class<?> clazz, Object... params) {
        Class<?>[] types = new Class<?>[params.length];

        for (int i = 0; i < params.length; i++) {
            types[i] = params[i].getClass();
        }

        try {
            return (T) clazz.getDeclaredConstructor(types).newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Method safeGetMethod(Class<?> clazz, String method){
        try{
            return clazz.getDeclaredMethod(method);
        }catch(NoSuchMethodException e){
            throw new RuntimeException(e);
        }
    }

    private Class<?> safeLoadClass(ClassLoader loader, String name){
        try{
            return loader.loadClass(name);
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
