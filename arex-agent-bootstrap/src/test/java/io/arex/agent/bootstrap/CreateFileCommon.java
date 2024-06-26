package io.arex.agent.bootstrap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateFileCommon {
    public static final String path;
    public static File jarInJarFile;

    static {
        try {
            path = Files.createTempDirectory("arex").toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File file1 = null, file2 = null, file3 = null, file4 = null, zipFile = null, zipExtensionFile = null;

    static {
        try {
            file1 = createFile(path, "/test1.class");
            file2 = createFile(path, "/test2.txt");
            file3 = createFile(path , "/io/arex/inst/ArexTest.class");
            file4 = createFile(path, "/META-INF/services/io.arex.agent.bootstrap.util.ServiceLoaderTest$SpiTestInterface", "io.arex.agent.bootstrap.util.ServiceLoaderTest$TestStringSerializable");
            List<File> files = Arrays.asList(file1, file2, file3, file4);
            zipFile = createFile(path, "/arex-agent-test.jar");
            zipExtensionFile = createFile(path + "/extensions/", "/arex-extension-test.jar");
            createZip(zipFile, files);
            createZip(zipExtensionFile, files);
            file1.deleteOnExit();
            file2.deleteOnExit();
            file3.deleteOnExit();

            // jar in jar file
            String outputJarPath = Files.createTempDirectory("arex").toAbsolutePath().toString();
            File outputJarFile = CreateFileCommon.createFile(outputJarPath ,"/output.jar");

            try (FileOutputStream fos = new FileOutputStream(outputJarFile);
                 JarOutputStream jos = new JarOutputStream(fos)) {
                File internalJarFile = CreateFileCommon.createFile(outputJarPath ,"/internal-test.jar");
                File internalThirdPartyJarFile = CreateFileCommon.createFile(outputJarPath ,"/third-party/jackson/jackson-test.jar");
                File internalBootstrapJarFile = CreateFileCommon.createFile(outputJarPath ,"/bootstrap/bootstrap-test.jar");
                addFileToJar(internalBootstrapJarFile, jos, outputJarPath);
                addFileToJar(internalJarFile, jos, outputJarPath);
                addFileToJar(internalThirdPartyJarFile, jos, outputJarPath);
                internalJarFile.delete();
                internalBootstrapJarFile.delete();
                jarInJarFile = outputJarFile;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            assert file1 != null;
            file1.deleteOnExit();
            assert file2 != null;
            file2.deleteOnExit();
            assert file3 != null;
            file3.deleteOnExit();
            assert zipFile != null;
            zipFile.deleteOnExit();
            assert zipExtensionFile != null;
            zipExtensionFile.deleteOnExit();
        }
    }

    public static void clear() {
        assert file1 != null;
        file1.deleteOnExit();
        assert file2 != null;
        file2.deleteOnExit();
        assert file3 != null;
        file3.deleteOnExit();
        assert file4 != null;
        file4.deleteOnExit();
        assert zipFile != null;
        zipFile.deleteOnExit();
        assert zipExtensionFile != null;
        zipExtensionFile.deleteOnExit();
        assert jarInJarFile != null;
        jarInJarFile.deleteOnExit();;
    }

    public static File getZipFile() {
        return zipFile;
    }

    public static File getZipExtensionFile() {
        return zipExtensionFile;
    }

    public static File getJarInJarFile() {
        return jarInJarFile;
    }

    private static void addFileToJar(File file, JarOutputStream jos, String outputJarPath) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            JarEntry jarEntry = new JarEntry(file.getAbsolutePath().substring(outputJarPath.length() + 1));
            jos.putNextEntry(jarEntry);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                jos.write(buffer, 0, bytesRead);
            }
        }
    }

    public static File createFile(String name) {
        return createFile(path, name);
    }

    public static File createFile(String path, String name) {
        try {
            File file = new File(path + name);
            if (!file.getParentFile().exists()) {
                boolean mkdirs = file.getParentFile().mkdirs();
            }
            boolean newFile = file.createNewFile();
            return file;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static File createFile(String path, String name, String value) {
        try {
            File file = createFile(path, name);
            assert file != null;
            Files.write(file.toPath(), value.getBytes(StandardCharsets.UTF_8));
            return file;
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static void createZip(File zipFile, List<File> files) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile.toPath()))) {

            for (File file : files) {
                if (!file.exists()) {
                    throw new FileNotFoundException("File Not Found For To Do Zip File! Please Provide A File..");
                }
                FileInputStream fis = new FileInputStream(file);
                ZipEntry zipEntry;
                String absolutePath = file.getAbsolutePath();
                int index = -1;
                if (absolutePath.contains("arex") || absolutePath.contains("extensions")) {
                    index = absolutePath.indexOf("io");
                }
                if (absolutePath.contains("META-INF")) {
                    index = absolutePath.indexOf("META-INF");
                }
                if (index >= 0) {
                    String substring = absolutePath.substring(index);
                    String replace = substring.replace("\\", "/");
                    zipEntry = new ZipEntry(replace);
                } else {
                    zipEntry = new ZipEntry(file.getName());
                }
                zos.putNextEntry(zipEntry);

                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }
        }
    }


}
