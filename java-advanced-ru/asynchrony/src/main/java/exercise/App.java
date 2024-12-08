package exercise;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

class App {

    // BEGIN
    public static CompletableFuture<String> unionFiles(String file1, String file2, String result) {

        CompletableFuture<String> string1 = CompletableFuture.supplyAsync(() -> {
            try {
                Path path = Paths.get(file1).toAbsolutePath().normalize();
                String file = Files.readString(path);
                System.out.println("First file read = " + file);
                return file;
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });

        CompletableFuture<String> string2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                Path path = Paths.get(file2).toAbsolutePath().normalize();
                String file = Files.readString(path);
                System.out.println("Second file read= " + file);
                return file;
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });

        CompletableFuture<String> unionString = string1.thenCombine(string2, (f1, f2) -> {
            return  write(f1, f2,   result);
        }).exceptionally(ex -> {
            System.out.println("Oops! We have an exception - " + ex.getMessage());
            return null;
        });
        return unionString;
    }
    // END

    public static void main(String[] args) throws Exception {
        // BEGIN
        CompletableFuture<String> result = App.unionFiles("src/main/resources/file1.txt",
                "src/main/resources/file2.txt",
                "src/main/resources/dest.txt");
        System.out.println("Result= " + result.join());
        // END
    }

    public static String withoutFuture(String file1, String file2, String file_result) {
        try {
            Path path = Paths.get(file1).toAbsolutePath().normalize();
            String f1 = Files.readString(path);
            System.out.println("First file read = " + f1);

            path = Paths.get(file2).toAbsolutePath().normalize();
            String f2 = Files.readString(path);
            System.out.println("Second file read= " + f2);

            try {
                if (Files.notExists(Paths.get(file_result).toAbsolutePath().normalize())) {
                    System.out.println("Result file create");
                    Files.createFile(Paths.get(file_result).toAbsolutePath().normalize());
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(file_result));
                System.out.println("Start metod write(f1)");
                writer.write(f1);
                System.out.println("Start metod write(f2)");
                writer.write(f2);
                System.out.println("Start metod write.close");
                writer.close();
                String file = Files.readString(Paths.get(file_result).toAbsolutePath().normalize());
                System.out.println("Writing to result file completed = "  + file);
            } catch (IOException e) {
                System.out.println("Result file not found");
                throw new RuntimeException(e);
            }

            String result = f1 + f2;
            System.out.println("Sum of files=" + result);

            return result;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static CompletableFuture<String> disconnectedFuture(String file1, String file2, String file_result) throws ExecutionException, InterruptedException {

        CompletableFuture<String> string1 = CompletableFuture.supplyAsync(() -> {
            try {
                Path path = Paths.get(file1).toAbsolutePath().normalize();
                String file = Files.readString(path);
                System.out.println("First file read = " + file);
                return file;
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });
        String f1 = string1.get();

        CompletableFuture<String> string2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                Path path = Paths.get(file2).toAbsolutePath().normalize();
                String file = Files.readString(path);
                System.out.println("Second file read= " + file);
                return file;
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        });
        String f2 = string2.get();

        CompletableFuture<String> unionString = CompletableFuture.supplyAsync(() -> {
            return  write(f1, f2, file_result);
        });
        return unionString;
    }

    public static String write(String f1, String f2, String file_result){
        try {
            System.out.println("Start metod write");
            if (Files.notExists(Paths.get(file_result).toAbsolutePath().normalize())) {
                System.out.println("Result file create");
                Files.createFile(Paths.get(file_result).toAbsolutePath().normalize());
            }
            System.out.println("Start metod BufferedWriter");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_result));
            System.out.println("Start metod write(f1)");
            writer.write(f1);
            System.out.println("Start metod write(f2)");
            writer.write(f2);
            System.out.println("Start metod write.close");
            writer.close();
            String file = Files.readString(Paths.get(file_result).toAbsolutePath().normalize());
            System.out.println("Writing to result file completed = "  + file);
        } catch (IOException e) {
            System.out.println("Result file not found");
            throw new RuntimeException(e);
        }
        String result = f1 + f2;
        System.out.println("Sum of files=" + result);
        return result;
    }
}

